package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ActionSlotContext extends AbstractGameContext {

    //TODO: double the needs of servants to increase family members values

    //TODO: Ludovico Ariosto changes a boolean here so that the player can add his pawn despite of action slot limits
    //handle a particular excommunication card


    @Override
    public void initContext() {
        setChanged();
        notifyObservers(ContextStatus.ENTERED);
    }

    @Override
    public ContextType getType() {
        return ContextType.ACTION_SLOT_CONTEXT;
    }

    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }

}

