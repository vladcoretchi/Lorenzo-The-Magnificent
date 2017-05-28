package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TurnContext extends AbstractGameContext {

    /**
     Constructor called only at the game setup
     */
    public TurnContext(ArrayList<AbstractGameContext> contexts) {
        this.contexts = contexts;
    }

    public TurnContext() {

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


        ArrayList<AbstractEffect> observers = player.getObservers();
        for (AbstractEffect observer : observers)
            if (!observer.isOncePerRound())
                observer.subscribeObserverToContext(contexts);

            notifyObservers(player); //for PerRoundLeaderReward

            interactWithPlayer(player);
            //TODO: start timeout

    }

    @Override
    public void interactWithPlayer(Player player) {
        LeaderDiscardContext leaderContext = new LeaderDiscardContext();
        contexts.add(leaderContext);


        ArrayList<TerritoryCard>  territoryCards = (ArrayList) Configurator.getTerritoryCards();


        //TODO: let the player visit where he pleases to go
        for(Integer i=0; i<22; i++) {
            ResourcesBonus bonus = territoryCards.get(i).getPermanentBonus();
            leaderContext.addObserver(bonus);

            /*AbstractEffect observer = new PerRoundLeaderReward();
            //observer.applyEffect(contexts);

            //observer.subscribeObserverToContext(contexts);
            leaderContext.addObserver((Observer) observer);*/
        }
        leaderContext.interactWithPlayer(player);


        System.out.println("benvenuto in turn context");
        MarketAreaContext marketAreaContext = new MarketAreaContext();
        marketAreaContext.interactWithPlayer(player);
        System.out.println("siamo tornati in turn context");
        UseCouncilPrivilegeContext privilegeContext = new UseCouncilPrivilegeContext();
        privilegeContext.interactWithPlayer(player, 3);
        System.out.println("siamo ritornati in turn context");


        //switchto che vuole //sulla scelta dell'utente per farlo entrare nel contesto
    }

    public void endContext(Player player) {
        /*In this context deactivate all observers of the player that has finished his turn*/
        player.unSubscribeObservers();
    }
    
    @Override
    public ContextType getType() {
        return ContextType.TURN_CONTEXT;
    }
    
}
