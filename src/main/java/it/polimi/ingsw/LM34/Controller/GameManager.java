package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.GameContexts.*;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Cards.*;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Enums.Controller.ContextEnum;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.RemotePlayer;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;
import it.polimi.ingsw.LM34.Utils.SetupDecks;

import java.util.*;

/**
 * Created by GiulioComi on 05/05/2017.
 */
public class GameManager {

    private Integer period; //3 in a game
    private Integer turn; //3*2 in a game
    private Integer phase; //equal to #players
    private ArrayList<Dice> dices;
    private ArrayList<Player> players = new ArrayList<>();
    private Map<Integer, Integer> faithPath = new HashMap<Integer, Integer>();
    HashMap<Player, Integer> victoryPointsByPlayer = new HashMap<Player, Integer>();

    //TODO: apply factory patterns to DECKS
    //DECKS cointaining all the 96 development cards of the game, that at the beginning of each period will be partially loaded in the towers
    private ArrayList<AbstractDevelopmentCard> territoryCardDeck = new ArrayList<>();
    private ArrayList<AbstractDevelopmentCard> characterCardDeck = new ArrayList<>();
    private ArrayList<AbstractDevelopmentCard> ventureCardDeck = new ArrayList<>();
    private ArrayList<AbstractDevelopmentCard> buildingCardDeck = new ArrayList<>();
    private ArrayList<LeaderCard> leaderCardsDeck;
    private ArrayList<ExcommunicationCard> excommunicationCards;

    private ArrayList<AbstractGameContext> contexts;
    private ArrayList<Observer> observersBonuses = new ArrayList<Observer>();
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
        market = Configurator.getMarket();

        //TODO: load development cards, leaders, excommunication tiles and others GameBoards & PersonalBoard spaces
        Collections.shuffle(players); //randomly set the initial play order
        shuffleDecksByPeriod();

    }

    //TODO: chain together remotePlayer (client) and the player
    public void addPlayer(RemotePlayer remotePlayer, Player player) {
        players.add(player);
    }

    public void endGame() {
        //TODO
        onEndCalculateVictoryPointsPerPlayer();
        onEndGameCalculatePointsByDevelopmentCardsOwned(victoryPointsByPlayer);
    }

    //method called only at the start of the game
    public void shuffleDecksByPeriod() {

            SetupDecks.prepareDevelopmentCard(territoryCardDeck);
            SetupDecks.prepareDevelopmentCard(buildingCardDeck);
            SetupDecks.prepareDevelopmentCard(characterCardDeck);
            SetupDecks.prepareDevelopmentCard(ventureCardDeck);

            SetupDecks.prepareOtherDecks(leaderCardsDeck, excommunicationCards);
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

    public void nextPeriod() { //round = mezzo periodo
        turn++;
        if (turn % 2 == 0)
            doCurchReport();
    }

    public void nextTurn() {
        //resetMillisTImer //unico per giocatori
        sweepActionSlots();
        replaceCards();
        setNewTurnOrder();
        rollDices();
        if (phase == players.size()) {
            nextPeriod();
            turn = 0;
        } else phase++;
    }

    public void buyCard(Player player, TowerSlot slot) throws InvalidCardType {
        player.getPersonalBoard().addCard(slot.getCardStored());
    }

    public void doCurchReport() {
        //TODO
    }

    public void replaceCards() {
        //TODO
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

    public HashMap<Player, Integer> onEndGameCalculatePointsByDevelopmentCardsOwned(HashMap<Player, Integer> victoryPointsByPlayer) {

        return victoryPointsByPlayer;
    }

    /**
     * @return the hashmap with a correlation between players and their points earned by venture cards
     */
    public HashMap<Player, Integer> onEndCalculateVictoryPointsPerPlayer() {
        HashMap<Player, Integer> victoryPointsToPlayers = new HashMap<Player, Integer>();
        Integer totalVictoryPointsByVentureCardReward = 0;
        ArrayList<AbstractDevelopmentCard> tempPlayerVentureCards = new ArrayList<AbstractDevelopmentCard>();
        //for each player we calculate the sum of the victory points rewards provided by his venture cards stored in the personal board
        try {
            for (Player p : players) {
                //TODO: check if the player has the excommunication card that disable this step
                /*if(p.getMalus== noCalculateEndPoints)
                    victoryPointsToPlayers(p, 0);*/
                // else
                tempPlayerVentureCards = p.getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.PURPLE);
                totalVictoryPointsByVentureCardReward = 0;
                for (AbstractDevelopmentCard dci : tempPlayerVentureCards) {
                    VentureCard dciVenture = (VentureCard) dci;
                    totalVictoryPointsByVentureCardReward += dciVenture.getEndingVictoryPointsReward();
                }
                victoryPointsToPlayers.put(p, totalVictoryPointsByVentureCardReward);
            }
        } catch (InvalidCardType ict) {
            ict.printStackTrace();
        } //TODO:  adjust this exception handle

        return victoryPointsToPlayers;
    }

    public void tryCardPolymorphism() {

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

    }

    /**
     *
     * @param player of the new turn
     */
    public void activateObserverOnTurnChange(Player player) {
        ArrayList<Observable> o;

        for (AbstractGameContext context : contexts)
            for(Observer observerOfThisContext : observersBonuses)
                context.addObserver(observerOfThisContext);

    }


    public void setupGameContexts() {

        contexts = new ArrayList<>();
        for (ContextEnum context : ContextEnum.values())
            contexts.add(ContextFactory.getContext(context));

        ResourcesBonus bonus = new ResourcesBonus(new Resources(0, 0, 5), 0);
        observersBonuses.add(bonus);
    }


    //a testing porpuse main
    public static void main(String[] args) {
     //   Configurator.loadConfigs();

        GameManager game = new GameManager();
        //game.tryCardPolymorphism();

        game.setupGameContexts();
        game.tryObserverPattern();
    }


}






