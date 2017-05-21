package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameContexts.CurchReportContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ObserverEffect;
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
    private  DevelopmentCardDeck<TerritoryCard> territoryCardDeck = new DevelopmentCardDeck<TerritoryCard>();
    private DevelopmentCardDeck<CharacterCard> characterCardDeck = new DevelopmentCardDeck<CharacterCard>();
    private DevelopmentCardDeck<VentureCard> ventureCardDeck = new DevelopmentCardDeck<VentureCard>();
    private DevelopmentCardDeck<BuildingCard> buildingCardDeck = new DevelopmentCardDeck<BuildingCard>();
    private ArrayList<LeaderCard> leaderCardsDeck;
    private ArrayList<ExcommunicationCard> excommunicationCards;

    /*GAME CONTEXTS*/
    protected ArrayList<AbstractGameContext> contexts;
    private PhaseContext phaseContext;
    private AbstractGameContext currentContext;

    public GameManager() {

        period = 1; //TODO: refactor
        round = 1;
        phase = 1;
        //Load all the configurations from json
        Configurator.loadConfigs();

        //TODO: sync the loading so that none of the methods below is called before configs.json file has been parsed
        prepareGameSpaces();
        prepareDecks();
        //TODO: initialize players
        setupPlayersResources();
        Collections.shuffle(players); //randomly set the initial play order

        setupGameContexts();
        startGame();

    }
        public void startGame()  {
            //TODO
            phaseContext.initContext(players.get(phase)); //first player start first round of the game
    }

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

    public void endGame() {
        //TODO
            AbstractGameContext endGameContext = Utilities.getContextByType(contexts, ContextType.END_GAME_CONTEXT);
            endGameContext.initContext();
    }

    //method called only at the start of the game
    public void prepareDecks() {

            territoryCardDeck = (DevelopmentCardDeck<TerritoryCard>) Configurator.prepareDevelopmentDeck();
            buildingCardDeck = (DevelopmentCardDeck<BuildingCard>) Configurator.prepareDevelopmentDeck();
            characterCardDeck = (DevelopmentCardDeck<CharacterCard>) Configurator.prepareDevelopmentDeck();
            ventureCardDeck = (DevelopmentCardDeck<VentureCard>) Configurator.prepareDevelopmentDeck();

            Configurator.prepareLeaderAndExcommunicationDecks(leaderCardsDeck, excommunicationCards);
            Configurator.orderExcommunicatioCardByPeriod(excommunicationCards);
    }

    public void nextRound() { //round = half period

        round++;

        Utilities.setNewTurnOrder(councilPalace, players);
        rollDices();
        sweepActionSlots();  //sweeps all action and tower slots from pawns and cards
        replaceCards();

        for (Player player : players) {
            player.unSubscribeObservers();
            ArrayList<ObserverEffect> playerObservers = player.getObservers();
            for(ObserverEffect observerEffect : playerObservers)
                observerEffect.resetApplyFlag();
        }

        if(round %2 == 0)
            nextPeriod();
    }

    public void nextPeriod() {
        period++;

        //Do curch report after 2nd round of each period
        if(period %2 == 0) {

            CurchReportContext curchContext = (CurchReportContext) Utilities.getContextByType(contexts, ContextType.CURCH_REPORT_CONTEXT);
            curchContext.initContext();
        }


        //enter the endGame context in which final points are calculated
        if(period > Configurator.TOTAL_PERIODS)
            endGame();
        round = 1;
        //TODO
    }


    public void nextPhase() {
        //resetMillisTimer //unique per player
        if (++phase == players.size()) {
            nextRound();
            phase = 1;
        }
        else phase++;

        /**
         *@param player Now is the turn of the next player to place his family member
         */
        phaseContext.initContext(players.get(phase));

    }

    public void replaceCards() {

        //TODO: refactor this
            Utilities.placeNewRoundCards(towers, territoryCardDeck);
            Utilities.placeNewRoundCards(towers, buildingCardDeck);
            Utilities.placeNewRoundCards(towers, characterCardDeck);
            Utilities.placeNewRoundCards(towers, ventureCardDeck);

    }

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

            phaseContext = new PhaseContext(contexts);
            contexts.add(phaseContext);

    }
}






