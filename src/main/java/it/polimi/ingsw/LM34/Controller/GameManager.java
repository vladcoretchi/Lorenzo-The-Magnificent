package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ObserverEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.RemotePlayer;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;
import it.polimi.ingsw.LM34.Utils.SetupLeaderAndExcommunicationDecks;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class GameManager {

    private Integer period; //3 in a game
    private Integer round; //3*2 in a game
    private Integer phase; //equal to #players
    private ArrayList<Dice> dices;
    private ArrayList<Player> players = new ArrayList<>();
    private Map<Integer, Integer> faithPath = new HashMap<Integer, Integer>();
    HashMap<Player, Integer> victoryPointsByPlayer = new HashMap<Player, Integer>();

    //TODO: apply factory patterns to DECKS
    //DECKS cointaining all the 96 development cards of the game, that at the beginning of each period will be partially loaded in the towers
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

    /*GAMEBOARD COMPONENTS*/
    private Market market;
    private CouncilPalace councilPalace;
    private ArrayList<Tower> towers;
    private WorkingArea harvestArea;
    private WorkingArea productionArea;

    public void startGame() {
        //check all the clients are connected, load configurations from file, setup all the game components with the configuration

        //Load all the configurations from json
        Configurator.loadConfigs();
        //TODO: sync the loading so that none of the methods below is called before configs.json file has been parsed
        Collections.shuffle(players); //randomly set the initial play order
        prepareGameSpaces();
        prepareDecks();

    }
    public void prepareGameSpaces() {

        market = Configurator.getMarket();
        councilPalace = Configurator.getPalace();
        towers = Configurator.getTowers();
        harvestArea = Configurator.getHarvestArea();
        productionArea = Configurator.getProductionArea();
    }

    //TODO: chain together remotePlayer (client) and the player
    public void addPlayer(RemotePlayer remotePlayer, Player player) {
        players.add(player);
    }

    public void endGame() {
        //TODO
        try {
            AbstractGameContext endGameContext = Utilities.getContextByType(contexts, ContextType.CURCH_REPORT_CONTEXT);
            endGameContext.initContext();
        } catch (NoSuchContextException e) {
            //TODO: handle this exception
        }
    }

    //method called only at the start of the game
    public void prepareDecks() {

            territoryCardDeck = (DevelopmentCardDeck<TerritoryCard>) Configurator.prepareDevelopmentDeck();
            buildingCardDeck = (DevelopmentCardDeck<BuildingCard>) Configurator.prepareDevelopmentDeck();
            characterCardDeck = (DevelopmentCardDeck<CharacterCard>) Configurator.prepareDevelopmentDeck();
            ventureCardDeck = (DevelopmentCardDeck<VentureCard>) Configurator.prepareDevelopmentDeck();

            SetupLeaderAndExcommunicationDecks.prepareLeaderAndExcommunicationDecks(leaderCardsDeck, excommunicationCards);
            SetupLeaderAndExcommunicationDecks.orderExcommunicatioCardByPeriod(excommunicationCards);
    }

    public void setNewTurnOrder() {
        ArrayList<FamilyMember> temp;
        ArrayList<Player> oldPlayersOrder = players;
        temp = councilPalace.getNextTurnOrder();
        players.clear();
        for (FamilyMember fm : temp) {
            PawnColor pawnColor = fm.getFamilyMemberColor();
            for (Player rm : oldPlayersOrder)
                if (rm.getPawnColor() == pawnColor)
                    players.add(rm);
        }

    }

    public void nextRound() { //round = half period

        round++;
        rollDices();

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

        round = 0;
        //TODO
    }


    public void nextPhase() {
        //resetMillisTimer //unique per player
        if (++phase == players.size()) {
            nextRound();
            phase = 0;
        }
        else phase++;

        try {
            phaseContext.initContext(players.get(phase));
        } catch (NoSuchContextException e) {
            e.printStackTrace();
        }


    }

    public void replaceCards() {
        sweepActionSlots();
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
        Integer incrementalCoins = 5;
        for (Player player : players) {
            player.addResources(new Resources(incrementalCoins++, 2, 2, 3));
        }
    }



    /*public void tryCardPolymorphism() {

        territoryCardDeck.add(new TerritoryCard("falegnameria", 3, 1, new ResourcesBonus(new Resources(1, 2, 3, 4), 1), new ResourcesBonus(new Resources(2, 3, 4, 5), 2)));
        AbstractDevelopmentCard t = territoryCardDeck.get(0);
        System.out.println(t.getColor());
        System.out.println(t.getPeriod());
        System.out.println(t.getName());
        System.out.println(t.getResourcesRequired());

        buildingCardDeck.add(new BuildingCard("giardino",2, 1, (new Resources(1, 2, 3, 4)),new ResourcesBonus(new Resources(2, 3, 4, 5), 3), new ResourcesBonus(new Resources(2, 3, 4, 5), 2)));
        AbstractDevelopmentCard b = buildingCardDeck.get(0);
        BuildingCard bb = (BuildingCard) b;
        System.out.println((bb.getDiceValueToProduct()));
        System.out.println(b.getColor());
        System.out.println(b.getPeriod());
        System.out.println(b.getName());
        System.out.println(b.getResourcesRequired());
        System.out.println(b.getInstantBonus());

    }

    public void tryObserverPattern() {
        Player player = new Player(PawnColor.BLUE, null);
        players.add(player);
        setupPlayersResources();
        Integer preVictoryPoints = player.getResources().getResourceByType(ResourceType.VICTORY_POINTS);
        System.out.println("victory points " + preVictoryPoints);

        activateObserverOnTurnChange(player);
        contexts.get(0).initContext(player);
        System.out.println(player.getResources().getResourceByType(ResourceType.VICTORY_POINTS));

    }*/


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


    //a testing porpuse main
    public static void main(String[] args) {
     //   Configurator.loadConfigs();

        GameManager game = new GameManager();
        //game.tryCardPolymorphism();

        game.setupGameContexts();
        //game.tryObserverPattern();
    }


}






