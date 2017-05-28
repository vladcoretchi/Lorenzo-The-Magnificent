package it.polimi.ingsw.LM34.Controller.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.SupportContexts.LeaderDiscardContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TurnContext extends AbstractGameContext {
    GameManager gameManager;
    /**
     Constructor called only at the game setup
     */
    public TurnContext(ArrayList<AbstractGameContext> contexts, GameManager gameManager) {
        this.gameManager = gameManager;
        this.contexts = contexts;
    }

    public TurnContext() {

    }

    @Override
    public ContextType getType() {
        return null;
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
        LeaderDiscardContext leaderContext = (LeaderDiscardContext) getContextByType(ContextType.LEADER_DISCARD_CONTEXT);

        ArrayList<TerritoryCard> territoryCards = (ArrayList) Configurator.getTerritoryCards();


        /*//TODO: let the player visit where he pleases to go
        for(Integer i=7; i<20; i++) {
            System.out.println("SERVANTS AGGIUNTIVI DI CARTA NUM "+i+"= "+territoryCards.get(i).getPermanentBonus().getResources().getResourceByType(ResourceType.SERVANTS));
            ResourcesBonus bonus = territoryCards.get(i).getPermanentBonus();
            ResourceIncomeContext incomeContext = (ResourceIncomeContext) GameManager.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
            incomeContext.addObserver(bonus);
        }*/

        System.out.println("benvenuto in turn context");
        GameManager.getContextByType(ContextType.MARKET_AREA_CONTEXT).interactWithPlayer(player);
        System.out.println("siamo tornati in turn context");
        GameManager.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT).interactWithPlayer(player);
        System.out.println("siamo ritornati in turn context");
        gameManager.nextTurn();


        //switchto che vuole //sulla scelta dell'utente per farlo entrare nel contesto
    }

    public void endContext(Player player) {
            //In this context deactivate all observers of the player that has finished his turn*/
            player.unSubscribeObservers();
            gameManager.nextTurn();
        }


    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    //@Override
   /* public ContextType getType() {
       return ContextType.TURN_CONTEXT;
    }*/
    
}
