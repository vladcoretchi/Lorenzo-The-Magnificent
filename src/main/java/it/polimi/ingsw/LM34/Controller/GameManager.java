package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.SpecialContexts.CurchReportContext;
import it.polimi.ingsw.LM34.Controller.SpecialContexts.EndGameContext;
import it.polimi.ingsw.LM34.Controller.SpecialContexts.TurnContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.RemotePlayer;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class GameManager {

    /*TURNS*/
    private Integer period; //3 in a game
    private Integer round; //3*2 in a game
    private Integer phase; //equal to #players
    private Integer turn;

    private ArrayList<Dice> dices;
    private ArrayList<Player> players = new ArrayList<>();

    /*GAMEBOARD COMPONENTS*/
    private Market market;
    private CouncilPalace councilPalace;
    private ArrayList<Tower> towers;
    private WorkingArea harvestArea;
    private WorkingArea productionArea;
    private Map<Integer, Integer> faithPath = new HashMap<Integer, Integer>();

    HashMap<Player, Integer> victoryPointsByPlayer = new HashMap<Player, Integer>();

    /*DECKS*/
    private DevelopmentCardDeck<TerritoryCard> territoryCardDeck = new DevelopmentCardDeck<TerritoryCard>();
    private DevelopmentCardDeck<CharacterCard> characterCardDeck = new DevelopmentCardDeck<CharacterCard>();
    private DevelopmentCardDeck<VentureCard> ventureCardDeck = new DevelopmentCardDeck<VentureCard>();
    private DevelopmentCardDeck<BuildingCard> buildingCardDeck = new DevelopmentCardDeck<BuildingCard>();
    private ArrayList<LeaderCard> leaderCardsDeck;
    private ArrayList<ExcommunicationCard> excommunicationCards;

    /*GAME CONTEXTS*/
    protected ArrayList<AbstractGameContext> contexts;
    private TurnContext turnContext;
    private CurchReportContext curchContext;
    private AbstractGameContext currentContext;

    /*CONSTRUCTOR*/
    public GameManager() {

        period = 1; //when cards of the new period are stored on towers
        round = 1; //when all players have placed all their pawns
        phase = 1; //when all players have placed 1 of their pawn
        turn = 1; //when the current player places his pawn
        //Load all the configurations from json
        Configurator.loadConfigs();

        //TODO: sync the loading so that none of the methods below is called before configs.json file has been parsed
        prepareGameSpaces();
        prepareDecks();
        drwaExcommunicationCards();
        //TODO: initialize players
        setupPlayersResources();
        Collections.shuffle(players); //randomly set the initial play order

        setupGameContexts();
        startGame();
    }


    private void drwaExcommunicationCards() {
        ArrayList<ExcommunicationCard> exCards = Utilities.getExcommunictionCards(excommunicationCards);
        for (ExcommunicationCard card : exCards)
            curchContext.addExcommunicationCard(card);
    }

    public void startGame() {
        //TODO
        turnContext.initContext(players.get(phase)); //first player start first round of the game
    }

    /**
     * Sets up the game spaces before game begins
     */
    public void prepareGameSpaces() {

        market = Configurator.getMarket();
        councilPalace = Configurator.getPalace();
        towers = Configurator.getTowers();
        harvestArea = Configurator.getHarvestArea();
        productionArea = Configurator.getProductionArea();
        //TODO: prepare faithPath, etc
    }

    //TODO: chain together remotePlayer (client) and the player
    public void addPlayer(RemotePlayer remotePlayer, Player player) {
        players.add(player);
    }

    /**
     * Enter the EndGameContext during which final points are calculated and ranking is showed
     */
    public void endGame() {
        EndGameContext endGameContext = (EndGameContext) Utilities.getContextByType(contexts, ContextType.END_GAME_CONTEXT);
        endGameContext.interactWithPlayer(players);
    }

    /**
     * Prepare decks at the start of the game
     */
    public void prepareDecks() {

        territoryCardDeck = (DevelopmentCardDeck<TerritoryCard>) Configurator.prepareDevelopmentDeck();
        buildingCardDeck = (DevelopmentCardDeck<BuildingCard>) Configurator.prepareDevelopmentDeck();
        characterCardDeck = (DevelopmentCardDeck<CharacterCard>) Configurator.prepareDevelopmentDeck();
        ventureCardDeck = (DevelopmentCardDeck<VentureCard>) Configurator.prepareDevelopmentDeck();
        Configurator.prepareLeaderAndExcommunicationDecks(leaderCardsDeck, excommunicationCards);
        Configurator.orderExcommunicatioCardByPeriod(excommunicationCards);
    }

    public void nextRound() { //round = half period
        ArrayList<AbstractEffect> playerObservers = new ArrayList<>();

        round++;

        Utilities.setNewTurnOrder(councilPalace, players);
        rollDices();
        sweepActionSlots();  //sweeps all action and tower slots from pawns and cards
        replaceCards();      //Four development cards per type are moved from the decks into the towerslots

        /**
         * At the beginning of the round re apply all the once per round observers of the players
         */
        for (Player player : players) {
            playerObservers = player.getObservers();
            for (AbstractEffect observer : playerObservers)
                if (observer.isOncePerRound())
                    observer.subscribeObserverToContext(contexts);

        }
        if (round % 2 == 0) {
            /**
             * Now it is Curch Report time
             */
            CurchReportContext curchContext = (CurchReportContext) Utilities.getContextByType(contexts, ContextType.CURCH_REPORT_CONTEXT);

            //param players are passed to the context so that CurchReportContext handle the interact with them
            curchContext.interactWithPlayer(players);

            nextPeriod();
        }

    }

    public void nextTurn() {

        turnContext.initContext(players.get(phase));
        if (++phase >= players.size()) { //all players have placed 1 pawn
            nextPhase();
            turn = 1;
        }

    }

    public void nextPeriod() {
        period++;


        if(period > Configurator.TOTAL_PERIODS)
            //enter the endGame context in which final points are calculated
            endGame();
        else
            round = 1;

        nextTurn();
        //TODO

    }


    public void nextPhase() {
        /**
         * If all players have placed all of their pawns go to the next round
         */
        if(phase >= (players.size() * players.get(1).getFamilyMembers().size())) //TODO: refactor
            nextRound();
        else
            phase++;

        /**
         *@param player Now is the turn of the next player to place his family member
         */


    }

    /**
     * New cards are placed in the towers at the beginning of the new round
     */
    public void replaceCards() {

            Utilities.placeNewRoundCards(towers, territoryCardDeck);
            Utilities.placeNewRoundCards(towers, buildingCardDeck);
            Utilities.placeNewRoundCards(towers, characterCardDeck);
            Utilities.placeNewRoundCards(towers, ventureCardDeck);
    }

    /**
     * Free all the action slots from the pawns and card stored during the previous round
     */
    public void sweepActionSlots() {

        for (Tower tower : towers)
            tower.sweep();

        market.sweep();
        councilPalace.sweep();
        productionArea.sweep();
        harvestArea.sweep();
    }

    public void rollDices() {
        for (Dice dice : dices) dice.rollDice();
    }

    /**
     * provide the players the initial amount of resources
     */
    public void setupPlayersResources() {
        Integer incrementalCoins = Configurator.BASE_COINS;
        for (Player player : players) {
            player.addResources(new Resources(incrementalCoins++, 2, 2, 3));
        }
    }

    /**
     * Instantiate all the game contexts at the start of the game
     */
    public void setupGameContexts() {

        contexts = new ArrayList<>();
        for (ContextType context : ContextType.values())
            contexts.add(ContextFactory.getContext(context));

        contexts.forEach((c) -> c.setContexts(contexts));

        /*This is the main context that registers the observers of the current player */
        //turnContext = (TurnContext) Utilities.getContextByType(contexts,ContextType.TURN_CONTEXT);
        //turnContext.setGameManager(this); //The TurnContext need a callback to the GameManager
        //curchContext = (CurchReportContext) Utilities.getContextByType(contexts,ContextType.CURCH_REPORT_CONTEXT);
    }
}






