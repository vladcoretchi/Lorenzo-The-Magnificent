package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.CouncilPalaceContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.HarvestAreaContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.MarketAreaContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.ProductionAreaContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.ChurchReportContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.TurnContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.EndGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.Server.ServerNetworkController;
import it.polimi.ingsw.LM34.Utils.Configurator;

import java.util.*;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class GameManager {
    private GameRoom gameRoom;

    /*TURNS*/
    private Integer period; //3 in a game
    private Integer round; //3*2 in a game
    private Integer phase; //equal to #players
    private Integer turn;

    private List<Dice> dices;
    private List<Player> players;

    /*GAMEBOARD COMPONENTS*/
    private Market market;
    private List<Tower> towers;
    private CouncilPalace councilPalace;
    private WorkingArea harvestArea;
    private WorkingArea productionArea;

    /*DECKS*/
    private DevelopmentCardDeck<TerritoryCard> territoryCardDeck;
    private DevelopmentCardDeck<CharacterCard> characterCardDeck;
    private DevelopmentCardDeck<VentureCard> ventureCardDeck;
    private DevelopmentCardDeck<BuildingCard> buildingCardDeck;
    private List<LeaderCard> leaderCardsDeck;
    private List<ExcommunicationCard> excommunicationCards;

    /*GAME CONTEXTS*/
    protected Map<ContextType, AbstractGameContext> contexts;

    private TurnContext turnContext = new TurnContext();
    private CouncilPalaceContext palaceContext;

    /*CONSTRUCTOR*/
    public GameManager(GameRoom gameRoom, List<String> players) {
        this.gameRoom = gameRoom;

        this.players = new ArrayList<Player>();

        //TODO: verify lengths
        for (int i = 0; i < players.size(); i++)
            this.players.add(new Player(players.get(i), PawnColor.values()[i], new PersonalBoard()));

        this.period = 1; //when cards of the new period are stored on towers
        this.round = 1; //when all players have placed all their pawns
        this.phase = 1; //when all players have placed 1 of their pawn
        this.turn = 0; //when the current player places his pawn

        setupGameContexts();

        //TODO: sync the loading so that none of the methods below is called before configs.json file has been parsed
        setUpGameSpaces();
        setUpDecks();
        replaceCards();
        drawExcommunicationCards();

        //TODO: initialize players
        Collections.shuffle(players); //randomly set the initial play order
        setupPlayersResources();
    }

    public ServerNetworkController getActivePlayerNetworkController() {
        return this.gameRoom.getPlayerNetworkController(players.get(this.turn).getPlayerName());
    }

    public ServerNetworkController getPlayerNetworkController(Player player) {
        return this.gameRoom.getPlayerNetworkController(player.getPlayerName());
    }

    public void drawExcommunicationCards() {
        /*List<ExcommunicationCard> exCards = getExcommunicationCards(excommunicationCards);
        ChurchReportContext churchContext = (ChurchReportContext) getContextByType(CHURCH_REPORT_CONTEXT);
        exCards.forEach(churchContext::addExcommunicationCard);*/
    }

    public void startGame() {
        players.forEach(player -> this.getPlayerNetworkController(player).updatePlayersData(this.players));
        ((TurnContext) getContextByType(TURN_CONTEXT)).initContext(); //first player start first round of the game
    }

    /**
     * Sets up the game spaces before game begins
     */
    public void setUpGameSpaces() {
        this.market = Configurator.getMarket();
        this.towers = Configurator.getTowers();
        this.councilPalace = Configurator.getPalace();
        this.harvestArea = Configurator.getHarvestArea();
        this.productionArea = Configurator.getProductionArea();
    }

    /**
     * Enter the EndGameContext during which final points are calculated and ranking is showed
     */
    public void endGame() {
        EndGameContext endGameContext = (EndGameContext) getContextByType(END_GAME_CONTEXT);
        endGameContext.interactWithPlayer();
    }

    /**
     * Prepare decks at the start of the game
     */
    public void setUpDecks() {
        this.territoryCardDeck = Configurator.getTerritoryCards();
        this.buildingCardDeck = Configurator.getBuildingCards();
        this.characterCardDeck = Configurator.getCharactersCards();
        this.ventureCardDeck = Configurator.getVentureCards();
        this.leaderCardsDeck = Configurator.getLeaderCards(this.players.size());
        this.excommunicationCards = Configurator.getExcommunicationTiles();
    }

    public void nextRound() { //round = half period
        List<AbstractEffect> playerObservers = new ArrayList<>();

        round++;

        setNewTurnOrder();
        rollDices();
        sweepActionSlots();  //sweeps all action and tower slots from pawns and cards
        replaceCards();      //Four development cards per type are moved from the decks into the towerslots

        /* At the beginning of the round re apply all the once per round observers of the player */
        /*for (Player player : players) {
            playerObservers = player.getObservers();
            for (AbstractEffect observer : playerObservers)
                if (observer.isOncePerRound())
                    observer.subscribeObserverToContext(contexts);
        }*/

        if (round % 2 == 0) {
            /* Now it is Curch Report time */
            ChurchReportContext churchContext = (ChurchReportContext) getContextByType(CHURCH_REPORT_CONTEXT);

            /* ChurchReportContext interact with a player at a time, based on turn order */
            players.forEach(churchContext::interactWithPlayer);

            nextPeriod();
        }

    }

    public void nextTurn() {
        if (turn >= players.size() - 1) { //all players have placed 1 pawn
            nextPhase();
            turn = 0;
        } else {
            turn++;
            ((TurnContext) getContextByType(TURN_CONTEXT)).initContext();
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
        /*If all players have placed all of their pawns go to the next round*/
        if(phase >= (players.size() * players.get(1).getFamilyMembers().size())) //TODO: refactor
            nextRound();
        else
            phase++;
    }

    /* New cards are placed in the towers at the beginning of the new round */
    public void replaceCards() {
        changeCards(towers, territoryCardDeck);
        changeCards(towers, buildingCardDeck);
        changeCards(towers, characterCardDeck);
        changeCards(towers, ventureCardDeck);
    }

    /**
     * Free all the action slots from the pawns and card stored during the previous round
     */
    public void sweepActionSlots() {
        this.towers.forEach(Tower::sweep);
        //((CouncilPalaceContext) getContextByType(ContextType.COUNCIL_PALACE_CONTEXT)).getCouncilPalace().sweep();
        this.market.sweep();
        this.productionArea.sweep();
        this.harvestArea.sweep();
    }

    public void rollDices() {
        dices.forEach(Dice::rollDice);
    }

    /**
     * provide the players the initial amount of resources
     */
    public void setupPlayersResources() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).addResources(new Resources(
                Configurator.BASE_COINS + i * Configurator.COINS_INCREMENT_PLAYER_ORDER,
                Configurator.BASE_WOODS + i * Configurator.WOODS_INCREMENT_PLAYER_ORDER,
                Configurator.BASE_STONES + i * Configurator.STONES_INCREMENT_PLAYER_ORDER,
                Configurator.BASE_SERVANTS + i * Configurator.SERVANTS_INCREMENT_PLAYER_ORDER
            ));
        }
    }

    /**
     * Instantiate all the game contexts at the start of the game
     */
    public void setupGameContexts() {
        contexts = new EnumMap<>(ContextType.class);
        for (ContextType context : ContextType.values())
            try {
                contexts.put(context, ContextFactory.getContext(context));
            } catch(NoSuchContextException e) {
                LOGGER.log(Level.SEVERE, "Cannot set one of the contexts");
            }

        ((UseCouncilPrivilegeContext) contexts.get(USE_COUNCIL_PRIVILEGE_CONTEXT)).setRewards(Configurator.getCouncilPrivilegeRewards());
        contexts.forEach((type, context) -> context.setGameManager(this));
    }

    public AbstractGameContext getContextByType(ContextType contextType) {
        return this.contexts.getOrDefault(contextType, null);
    }

    public AbstractGameContext getContextByType(PlayerSelectableContexts contextType) {
        return contexts.getOrDefault(contextType, null);
    }

    //TODO: refactor
    public List<Player>  setNewTurnOrder() {
        List<Player> oldPlayersOrder = players;
        List<Player> newPlayersOrder = new ArrayList<>();
        List<FamilyMember> membersInOrder = this.councilPalace.getActionSlot().getFamilyMembers();

        /*First remove all multiple pawns associated to the same player*/
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
        newPlayersOrder.addAll(oldPlayersOrder);

        return newPlayersOrder;
    }

    /**
     *
     * @param towers from which choose the right tower by development card type
     * @param developmentDeck from which to extract and place in the tower the cards for the new round
     */
    public void changeCards(List<Tower> towers, DevelopmentCardDeck<?> developmentDeck) {
        Tower tower = null;
        Iterator iterator = developmentDeck.iterator();

        //select the right tower...
        for (Tower t : towers)
            if (t.getDevelopmentTypeStored() == developmentDeck.getCardColor())
                tower = t;

        //...and now place every card in the deck until the tower's slots are full
        if(tower != null)
            tower.sweep();
        while (iterator.hasNext() && tower.getCardsStored().size() < Configurator.CARD_PER_ROUND)
            tower.addCard((AbstractDevelopmentCard) iterator.next());
    }

    /**
     *
     * @param cards excommunication deck from which to extract one card by period
     * @return the 3 card choosed
     */
    public static List<ExcommunicationCard> getExcommunicationCards(List<ExcommunicationCard> cards) {
        //TODO: consider doing this in the configurator
        List<ExcommunicationCard> selectedCards = new ArrayList<>();
        //TODO: wrong!!
        Integer period = 1;
        for(ExcommunicationCard card : cards)
            if(card.getPeriod() == period) {
                selectedCards.add(card);
                period++;
            }
        return selectedCards; //return the 3 cards, one by period
    }

    //TODO
    //if timeout while user selects the card -> an arbitrary card is selected automatically
    public void bonusTileSelectionPhase() {
        List<BonusTile> bonusTiles;
        bonusTiles = Configurator.getBonusTiles();
        for (Player p : players) {
            /*Integer selected = getPlayerNetworkController(p).bonusTileSelection(bonusTiles);
            p.getPersonalBoard().setPersonalBonusTile(bonusTiles.get(selected));
            bonusTiles.remove(selected);*/
    }
    }

    /**
     * To each player presents 4 leader at each step, from which he chooses one
     */
    //TODO: implement the steps defined in the rules to manage how leaders selection works
    //if timeout while user selects the card -> an arbitrary card is selected automatically
    public void leaderSelectionPhase() {
        //the leadercards are only 4*#players
        leaderCardsDeck = leaderCardsDeck.subList(0, Configurator.MAX_LEADER_PER_PLAYER * players.size());

        for (Integer i = 0; i < Configurator.MAX_LEADER_PER_PLAYER; i++) {
            for (Player p : players) {
                //Integer selected = getPlayerNetworkController(p).leaderSelection(leaderCardsDeck.subList());
                //leaderCardsDeck.remove(selected);
            }
        }
    }

    public Integer getPeriod() {
        return period;
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.turn);
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public List<Tower> getTowers() {
        return this.towers;
    }

    public Market getMarket() {
        return this.market;
    }

    public WorkingArea getProductionArea() {
        return this.productionArea;
    }

    public WorkingArea getHarvestArea() {
        return this.harvestArea;
    }

    public CouncilPalace getCouncilPalace() {
        return this.councilPalace;
    }

    public List<ExcommunicationCard> getExcommunicationCards() {
        return this.excommunicationCards;
    }
}







