package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ActionSlotContext extends AbstractGameContext {
    //TODO: Ludovico Ariosto changes a boolean here so that the player can add his pawn despite of action slot limits
    //handle a particular excommunication card


    @Override
    public void interactWithPlayer(Player player) {
        //TODO: implement what player can do here and modify the model in this controller class
        //handle "federico da montefeltro"
        setChanged();
        notifyObservers();




        //turnContext.interactWithPlayer();
    }


    @Override
    public ContextType getType() {
        return ContextType.ACTION_SLOT_CONTEXT;
    }





}

