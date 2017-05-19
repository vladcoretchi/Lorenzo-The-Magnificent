package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TurnContext extends AbstractGameContext {
    ArrayList<AbstractGameContext> contexts;



    public void initContext(Player player) {
    //TODO:in this context reactivate all observers of the player that is playing
        ArrayList<Observer> observers = player.getObservers();

        for (AbstractGameContext context : contexts)
            for (Observer observer : observers)
                //if(context.getType() == observer.getObservableType())
                    context.addObserver(observer);
    }

    @Override
    public ContextType getType() {
        return null;
    }

    @Override
    public void endContext() {

    }


    public void endContext(ArrayList<AbstractGameContext> contexts, Player player) {
        //TODO:in this context reactivate all observers of the player that has
        ArrayList<Observer> observers = player.getObservers();
        for (AbstractGameContext context : contexts)
                context.deleteObservers();

    }
    //the only observer associated with this context is the penalty of the excommunication card that make you skip
    //your first turn

}
