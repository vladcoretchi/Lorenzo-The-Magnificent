package it.polimi.ingsw.LM34.Controller.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.DiceDependentContexts.MarketAreaContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.MarketBan;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.PerRoundLeaderReward;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TurnContext extends AbstractGameContext {
    /**
     Constructor called only at the game setup
     */


    public TurnContext() {
        contextType = ContextType.TURN_CONTEXT;
    }


    /**
     * @param player about to begin his turn
     *Reactivate all observers that are of the player that is going to play
     *NOTE: OncePerRound observers are excluded
     */
    public void initContext(Player player) {

        /*To make the player skip his turn*/
        setChanged();
        notifyObservers(this); //for SkipTurn observer


        /*ArrayList<AbstractEffect> observers = player.getObservers();
        for (AbstractEffect observer : observers)
            if (!observer.isOncePerRound())
                observer.subscribeObserverToContext(contexts);
            notifyObservers(player); //for PerRoundLeaderReward*/
            System.out.println("Now is player: "+player.getPawnColor()+" turn");
            interactWithPlayer(player);
            //TODO: start timeout

    }

    @Override
    public void interactWithPlayer(Player player) {
        //LeaderDiscardContext leaderContext = (LeaderDiscardContext) gameManager.getContextByType(ContextType.LEADER_DISCARD_CONTEXT);

        //ArrayList<TerritoryCard> territoryCards = (ArrayList) Configurator.getTerritoryCards();


        //TODO: let the player visit where he pleases to go
       /* for(Integer i=7; i<20; i++) {
            territoryCards.get(i).getPermanentBonus().getResources().getResourceByType(ResourceType.SERVANTS);
            ResourcesBonus bonus = territoryCards.get(i).getPermanentBonus();
            ResourceIncomeContext incomeContext = (ResourceIncomeContext) gameManager.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
            incomeContext.addObserver(bonus);
            //TODO: each bonus has to be also be stored in a list assigned to a player in order to reactivate it
            player.registerObserver(bonus);
        }*/

        /*System.out.println("benvenuto in turn context");
        //gameManager.getContextByType(ContextType.MARKET_AREA_CONTEXT).interactWithPlayer(player);
        //System.out.println("siamo tornati in turn context");
        //gameManager.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT).interactWithPlayer(player);
        //System.out.println("siamo ritornati in turn context");
        ResourcesPerItemBonus observer = new ResourcesPerItemBonus(player, new Resources(), 0);
        //observer.getContextToBeSubscribedTo().forEach(context -> context.addObserver(observer));
        this.addObserver(observer);
        setChanged(); notifyObservers();
        System.out.println("il turn context continua la sua esecuzione");
        setChanged(); notifyObservers();
        System.out.println("il turn context sta per terminare");
        endContext();*/

        //switchto che vuole //sulla scelta dell'utente per farlo entrare nel contesto


        setChanged();
        notifyObservers();

        List<String> _contexts = new ArrayList<>();
        _contexts.add("Market Area");
        _contexts.add("Production Area");
        _contexts.add("Discard Leader Cards");

        System.out.println(this.gameManager.getActivePlayerNetworkController().contextSelection(_contexts));
    }

    /**
     * Deactivates all bonus observers of the player that has played
     */
    public void endContext() {
        //TODO: unsubscribe player observer at the end of the turn
        /*contexts.forEach((c) -> c.deleteObservers());*/
        //gameManager.nextTurn();

    }


    public static void main (String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player("cicoio", PawnColor.RED, new PersonalBoard());
        Configurator.loadConfigs();
        //GameManager gameManager = new GameManager();
        /*gameManager.setupGameContexts();
        TurnContext turnContext = (TurnContext) gameManager.getContextByType(ContextType.TURN_CONTEXT);
        turnContext.setGameManager(gameManager);
        turnContext.trial(player);*/

    }

    //TODO: a testing method
    private void trial(Player player) {
        PerRoundLeaderReward perRoundLeaderReward = new PerRoundLeaderReward();
        perRoundLeaderReward.applyEffect(this, player);
        MarketBan marketBan = new MarketBan();
        marketBan.applyEffect(this, player);
        //this.addObserver(marketBan);
        MarketAreaContext marketAreaContext = (MarketAreaContext) gameManager.getContextByType(ContextType.MARKET_AREA_CONTEXT);
        marketAreaContext.interactWithPlayer(player);

    }

}
