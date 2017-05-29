package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by GiulioComi on 17/05/2017.
 */
public abstract class AbstractGameContext extends Observable  {
    protected ArrayList<AbstractGameContext> contextsToSubscribeTo;


    public AbstractGameContext() {
        contextsToSubscribeTo = new ArrayList<>();
    }


    /**
     * @param player that is playing his turn and changes context as he pleases
     */
    public abstract void interactWithPlayer(Player player);

    /**
     This list is asked by contexts in order to now to which context subscribe the effects once they are activated
     */
    public ArrayList<AbstractGameContext> getContextsToSubscribeTo() {
        return this.contextsToSubscribeTo;
    }

    public abstract ContextType getType();


}
