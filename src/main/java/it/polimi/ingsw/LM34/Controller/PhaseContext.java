package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
import it.polimi.ingsw.LM34.Model.Effects.ObserverEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class PhaseContext {
    ArrayList<AbstractGameContext> contexts;


    public PhaseContext(ArrayList<AbstractGameContext> contexts) {
        this.contexts = contexts;
    }

    /**
     * @param player about to begin his turn
     *Reactivate all observers of the player that is going to play
     */
    public void initContext(Player player) throws NoSuchContextException {

        ArrayList<ObserverEffect> observers = player.getObservers();
            for (ObserverEffect observer : observers)
                observer.registerObserverToContext(contexts);
    }

    
    public void endContext(ArrayList<AbstractGameContext> contexts, Player player) {
        //TODO:in this context deactivate all observers of the player that has finished his turn
        for (AbstractGameContext context : contexts)
                context.deleteObservers();

    }
    //the only observer associated with this context is the penalty of the excommunication card that make you skip
    //your first turn

}
