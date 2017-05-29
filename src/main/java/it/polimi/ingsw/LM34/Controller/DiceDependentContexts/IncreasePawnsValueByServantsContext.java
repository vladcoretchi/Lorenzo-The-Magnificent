package it.polimi.ingsw.LM34.Controller.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class IncreasePawnsValueByServantsContext extends AbstractGameContext {
    private Integer servantsConsumed;

    public IncreasePawnsValueByServantsContext() {
        servantsConsumed = 0;
    }

    public void interactWithPlayer(Observable o, Player player) {
        servantsConsumed = 0; //resets every time the servantsConsumed value

        //TODO: let the player choose the servants to sacrifice... servantsConsumed = ...
        setChanged();
        notifyObservers();  //Notify the excommunication observer that halves servants value

        ((DiceDependentContextsInterface)o).increaseTempValue(servantsConsumed);

    }

    @Override
    public ContextType getType() {
        return ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
    }

    /**
     * Called by the excommunication effect that halves servants values
     */
    public void halvesServantsValue() {
        servantsConsumed /= 2;
    }
}
