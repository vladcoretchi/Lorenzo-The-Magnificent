package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class PhaseContext extends AbstractGameContext {
    ArrayList<AbstractGameContext> contexts;
    private Boolean skipTurn;

    //Constructor called only at the game setup
    public PhaseContext(ArrayList<AbstractGameContext> contexts) {
        this.contexts = contexts;
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

        if (!skipTurn) {
            ArrayList<AbstractEffect> observers = player.getObservers();
            for (AbstractEffect observer : observers)
                if (!observer.isOncePerRound())
                    observer.subscribeObserverToContext(contexts);

            notifyObservers(player); //for PerRoundLeaderReward
        }

        skipTurn = false;
    }
    
    public void endContext(Player player) {
        //TODO:in this context deactivate all observers of the player that has finished his turn
        player.unSubscribeObservers();
        //TODO: tell the game manager to go to the next phase
    }

    @Override
    public void initContext() {/*not used*/}

    @Override
    public ContextType getType() {
        return ContextType.PHASE_CONTEXT;
    }

    @Override
    public void endContext() {
        //TODO
    }

    public Boolean getSkipTurn() {
        return skipTurn;
    }

    public void setSkipTurn(Boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    //the only observer associated with this context is the penalty of the excommunication card that make you skip
    //your first turn


}
