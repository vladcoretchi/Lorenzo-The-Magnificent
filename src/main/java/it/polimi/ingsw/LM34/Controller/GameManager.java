package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.DiceDependentContexts.CouncilPalaceContext;
import it.polimi.ingsw.LM34.Controller.DiceDependentContexts.HarvestAreaContext;
import it.polimi.ingsw.LM34.Controller.DiceDependentContexts.MarketAreaContext;
import it.polimi.ingsw.LM34.Controller.DiceDependentContexts.ProductionAreaContext;
import it.polimi.ingsw.LM34.Controller.SpecialContexts.CurchReportContext;
import it.polimi.ingsw.LM34.Controller.SpecialContexts.EndGameContext;
import it.polimi.ingsw.LM34.Controller.SpecialContexts.TurnContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.*;

import static it.polimi.ingsw.LM34.Enums.Model.PawnColor.BLUE;
import static it.polimi.ingsw.LM34.Enums.Model.PawnColor.RED;

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
    private ArrayList<Player> players;

    /*GAMEBOARD COMPONENTS*/
    private Market market;
    private ArrayList<Tower> towers;
    private WorkingArea harvestArea;
    private WorkingArea productionArea;
    private Map<Integer, Integer> faithPath = new HashMap<Integer, Integer>();

    HashMap<Player, Integer> victoryPointsByPlayer = new HashMap<Player, Integer>();

    /*DECKS*/
    //TODO: migrate from arraylist to deck generics <T>
    public ArrayList<TerritoryCard> territoryCardDeck = new ArrayList<>();
    private DevelopmentCardDeck<CharacterCard> characterCardDeck = new DevelopmentCardDeck<CharacterCard>();
    private DevelopmentCardDeck<VentureCard> ventureCardDeck = new DevelopmentCardDeck<VentureCard>();
    private DevelopmentCardDeck<BuildingCard> buildingCardDeck = new DevelopmentCardDeck<BuildingCard>();
    private ArrayList<LeaderCard> leaderCardsDeck;
    private ArrayList<ExcommunicationCard> excommunicationCards;

    /*GAME CONTEXTS*/
    protected static ArrayList<AbstractGameContext> contexts;
    private TurnContext turnContext = new TurnContext();
    private CurchReportContext curchContext;
    private AbstractGameContext currentContext;
    private CouncilPalaceContext palaceContext;
    private MarketAreaContext marketContext;
    private ProductionAreaContext productionContext;
    private HarvestAreaContext harvestContext;

    /*CONSTRUCTOR*/
    public GameManager() {


        players = new ArrayList<Player>();
        period = 1; //when cards of the new period are stored on towers
        round = 1; //when all players have placed all their pawns
        phase = 1; //when all players have placed 1 of their pawn
        turn = 0; //when the current player places his pawn
        //Load all the configurations from json
        Configurator.loadConfigs();

        //TODO: sync the loading so that none of the methods below is called before configs.json file has been parsed
        //prepareGameSpaces();
       // prepareDecks();
       // drwaExcommunicationCards();

        //TODO: initialize players
        setupPlayersResources();
        Collections.shuffle(players); //randomly set the initial play order

        setupGameContexts();

    }


    private void drwaExcommunicationCards() {
        ArrayList<ExcommunicationCard> exCards = getExcommunictionCards(excommunicationCards);
        for (ExcommunicationCard card : exCards)
            curchContext.addExcommunicationCard(card);
    }

    public void startGame() {
        //TODO
        turnContext.initContext(players.get(turn)); //first player start first round of the game
    }

    /**
     * Sets up the game spaces before game begins
     */
    public void prepareGameSpaces() {

        market = Configurator.getMarket();
        towers = Configurator.getTowers();
        harvestArea = Configurator.getHarvestArea();
        productionArea = Configurator.getProductionArea();
        //TODO: prepare faithPath, etc
    }

    //TODO: chain together remotePlayer (client) and the player
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Enter the EndGameContext during which final points are calculated and ranking is showed
     */
    public void endGame() {
        EndGameContext endGameContext = (EndGameContext) getContextByType(ContextType.END_GAME_CONTEXT);
        endGameContext.interactWithPlayer(players);
    }

    /**
     * Prepare decks at the start of the game
     */
    public void prepareDecks() {

        //territoryCardDeck = (DevelopmentCardDeck<TerritoryCard>) Configurator.prepareDevelopmentDeck();
        buildingCardDeck = (DevelopmentCardDeck<BuildingCard>) Configurator.prepareDevelopmentDeck();
        characterCardDeck = (DevelopmentCardDeck<CharacterCard>) Configurator.prepareDevelopmentDeck();
        ventureCardDeck = (DevelopmentCardDeck<VentureCard>) Configurator.prepareDevelopmentDeck();
        Configurator.prepareLeaderAndExcommunicationDecks(leaderCardsDeck, excommunicationCards);
        Configurator.orderExcommunicatioCardByPeriod(excommunicationCards);
    }

    public void nextRound() { //round = half period
        ArrayList<AbstractEffect> playerObservers = new ArrayList<>();

        round++;

        setNewTurnOrder();
        rollDices();
        sweepActionSlots();  //sweeps all action and tower slots from pawns and cards
        replaceCards();      //Four development cards per type are moved from the decks into the towerslots

        /**
         * At the beginning of the round re apply all the once per round observers of the players
         */
        /*for (Player player : players) {
            playerObservers = player.getObservers();
            for (AbstractEffect observer : playerObservers)
                if (observer.isOncePerRound())
                    observer.subscribeObserverToContext(contexts);

        }*/
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
        if (turn >= players.size()-1) { //all players have placed 1 pawn
            System.out.println("Turni finiti");
            nextPhase();
            turn = 0;
        } else {
            turn++;
            turnContext.initContext(players.get(turn));
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

        //placeNewRoundCards(towers, territoryCardDeck);
        placeNewRoundCards(towers, buildingCardDeck);
        placeNewRoundCards(towers, characterCardDeck);
        placeNewRoundCards(towers, ventureCardDeck);
    }

    /**
     * Free all the action slots from the pawns and card stored during the previous round
     */
    public void sweepActionSlots() {

        for (Tower tower : towers)
            tower.sweep();


        //TODO: transfer areas variable from gamemanager to related contexts...
        marketContext.sweep();
        palaceContext.sweep();
        productionContext.sweep();
        harvestContext.sweep();
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
            try {
            contexts.add(ContextFactory.getContext(context));
            } catch (NoSuchContextException e) {
                e.printStackTrace();
            }


        TurnContext turnContext = (TurnContext) getContextByType(ContextType.TURN_CONTEXT);
        palaceContext = (CouncilPalaceContext) getContextByType(ContextType.COUNCIL_PALACE_CONTEXT);
        marketContext = (MarketAreaContext) getContextByType(ContextType.MARKET_AREA_CONTEXT);
        productionContext = (ProductionAreaContext) getContextByType(ContextType.PRODUCTION_AREA_CONTEXT);
        harvestContext = (HarvestAreaContext) getContextByType(ContextType.HARVEST_AREA_CONTEXT);
    }


    public static AbstractGameContext getContextByType(ContextType contextType) {
        //TODO: refactor this loop
        for (AbstractGameContext context : contexts)
            if (context.getType() == contextType)
                return context;

        return null;
    }

    //TODO: refactor
    public ArrayList<Player>  setNewTurnOrder() {

        ArrayList<Player> oldPlayersOrder = players;
        ArrayList<Player> newPlayersOrder = new ArrayList<>();
        ArrayList<FamilyMember> membersInOrder = palaceContext.getCouncilPalace().getOccupyingPawns();

        /*First remove all multipe pawns associated to the same player*/
        /*These inner loops do not add temporal complexity because pawns' count is negligible*/
        for(FamilyMember fm1 : membersInOrder)
            for(FamilyMember fm2 : membersInOrder)
                if(fm1.getFamilyMemberColor() == fm2.getFamilyMemberColor())
                    membersInOrder.remove(fm2); //keep just the first pawn for every player

        /*now that there is one pawn per players order the player based on pawns' positions*/
        for(FamilyMember fm : membersInOrder) {
            PawnColor color = fm.getFamilyMemberColor();
            for (Player player : oldPlayersOrder)
                if (player.getPawnColor() == color) {
                    newPlayersOrder.add(player);
                    oldPlayersOrder.remove(player);
                }
        }
        /**
         *@param remainingPlayers that did not placed their familyMembers in councilPalace
         */
        newPlayersOrder.addAll(oldPlayersOrder);

        /**
         * @return this is the new players order for the next round
         */
        return newPlayersOrder;
    }

    /**
     *
     * @param towers from which choose the right tower by development card type
     * @param developmentDeck from which to extract and place in the tower the cards for the new round
     */
    public void placeNewRoundCards(ArrayList<Tower> towers, DevelopmentCardDeck<?> developmentDeck) {

        Tower tower = new Tower();
        Iterator iterator  = developmentDeck.iterator();
        AbstractDevelopmentCard card;

        //select the right tower...
        for (Tower t : towers)
            if (t.getDevelopmentTypeStored() == developmentDeck.getCardColor())
                tower = t;

        //...and now place every card in the deck until the tower's slots are full
        Integer cardStored = 0;
        while (iterator.hasNext() && cardStored< Configurator.CARD_PER_ROUND) {
            card = (AbstractDevelopmentCard) iterator.next();
            tower.addCard(card);
        }

    }
    /**
     * @param player on which to control the amount of resources he has available
     * @param resourcesRequired to activate the exchange bonus
     * @see it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesExchangeBonus
     * @return if the effect is activable or not
     */

    /*Called by Game Manager only at the beginning of the game*/
    public void setContexts(ArrayList<AbstractGameContext> contexts) {
        this.contexts = contexts;
    }

    /**
     *
     * @param cards excommunication deck from which to extract one card by period
     * @return the 3 card choosed
     */
    public static ArrayList<ExcommunicationCard> getExcommunictionCards(ArrayList<ExcommunicationCard> cards) {
        ArrayList<ExcommunicationCard> exCardChoosed = new ArrayList<>();
        Integer period = 1;
        for(ExcommunicationCard card : cards)
            if(card.getPeriod() == period) {
                exCardChoosed.add(card);
                period++;
            }
        return exCardChoosed; //return the 3 cards, one by period
    }

    //TODO: this is just for testing purpose
    public ArrayList<AbstractGameContext> getContexts() {
        return contexts;
    }


    public TurnContext getTurnContext() {
        return this.turnContext;
    }
    //TODO: this is just for testing purpose
    public ArrayList<Player> getPlayers() {
        return players;
    }


    //TODO: remove this testing main
    public static void main (String [] args) {
        Configurator.loadConfigs();
        GameManager gameManager = new GameManager();

        gameManager.setupGameContexts();
        ArrayList<TerritoryCard> territoryCards = new ArrayList<>();


        Player player = new Player(BLUE, new PersonalBoard());
        Player player1 = new Player(RED, new PersonalBoard());
        gameManager.addPlayer(player);
        gameManager.addPlayer(player1);

        gameManager.getTurnContext().setGameManager(gameManager);
        gameManager.territoryCardDeck = territoryCards;
        gameManager.startGame();

    }


}






