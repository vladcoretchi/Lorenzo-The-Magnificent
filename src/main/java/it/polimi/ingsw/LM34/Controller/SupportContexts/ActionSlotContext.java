package it.polimi.ingsw.LM34.Controller.SupportContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ActionSlotContext extends AbstractGameContext {
    //TODO: Ludovico Ariosto changes a boolean here so that the player can add his pawn despite of action slot limits
    //handle a particular excommunication card

    public ActionSlotContext() {
        contextType = ContextType.ACTION_SLOT_CONTEXT;
    }

    @Override
    public void interactWithPlayer(Player player) {
        //TODO: implement what player can do here and modify the model in this controller class
        //handle "federico da montefeltro"
        setChanged();
        notifyObservers();
        //actionSlot.setSingleSlot(false);
        //TODO: player choices pawn to place and the slot
        //if(actionSlot.getSingleSlot()) ...

    }




}

