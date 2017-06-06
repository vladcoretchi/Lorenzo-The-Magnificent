package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Validator;

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

    public ActionSlot actionSlotSelection(DiceDependentContextsInterface callerContext, Player player) {
        ActionSlot selectedSlot = null;
        Integer selected = this.gameManager.getActivePlayerNetworkController().actionSlotSelection(callerContext.getActionSlots());
        //TODO
        try {
            Validator.checkValidity(selected.toString(),callerContext.getActionSlots());
            selectedSlot = callerContext.getActionSlots().get(selected);
            if (selectedSlot == null)
                throw new IncorrectInputException();
            //TODO: do this step in FamilyMemberSelectionContext
            // gameManager.getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT).interactWithPlayer(player);
        }
        /*If input mismatch expected informations... the player is able to try again*/
        catch(IncorrectInputException ide){
            actionSlotSelection(callerContext, player);
        }
        return selectedSlot;
    }




}

