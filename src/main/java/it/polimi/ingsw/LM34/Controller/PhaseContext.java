package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class PhaseContext extends AbstractGameContext {
    ArrayList<AbstractGameContext> contexts;


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

        ArrayList<AbstractEffect> observers = player.getObservers();
            for (AbstractEffect observer : observers)
                if(!observer.isOncePerRound())
                    observer.subscribeObserverToContext(contexts);
    }

    
    public void endContext(Player player) {
        //TODO:in this context deactivate all observers of the player that has finished his turn
        player.unSubscribeObservers();
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

    //the only observer associated with this context is the penalty of the excommunication card that make you skip
    //your first turn


}
