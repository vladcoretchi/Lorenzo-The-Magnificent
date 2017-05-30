package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.DiceDependentContextsInterface;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class IncreasePawnsValueByServantsContext extends AbstractGameContext {
    private Integer servantsConsumed;

    public IncreasePawnsValueByServantsContext() {
        contextType = ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
        servantsConsumed = 0;
    }

    @Override
    public void interactWithPlayer(Player player) {

    }

    public void interactWithPlayer(Observable o, Player player) {
        servantsConsumed = 0; //resets every time the servantsConsumed value

        //TODO: let the player choose the servants to sacrifice... servantsConsumed = ...
        setChanged();
        notifyObservers();  //Notify the excommunication observer that halves servants value

        ((DiceDependentContextsInterface)o).increaseTempValue(servantsConsumed);
    }



    /**
     * Called by the excommunication effect that halves servants values
     */
    public void halvesServantsValue() {
        servantsConsumed /= 2;
    }


}
