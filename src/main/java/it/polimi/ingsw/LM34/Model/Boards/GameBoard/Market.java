package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.io.Serializable;
import java.util.List;

//TODO: apply Singleton design pattern
public class Market extends GameSpace implements Serializable {

    /*costructor called only at the beginning of the game
    this class has been implemented so that action slots are only set at the beginning of the game by the controller
    after the configurator has load the configuration that have been chosen
    */
    public Market(List<ActionSlot> actionSlots) {
        this.actionSlots = actionSlots;
    }

    //a player places the pawn in the slot that provides the reward he/she pleases, otherwise it throws an exception
    public void insertFamilyMember (Integer slotIndex, FamilyMember fm) throws OccupiedSlotException{
        boolean alreadyFoundSlot= false;
            actionSlots.get(slotIndex).insertFamilyMember(fm);
            alreadyFoundSlot= true;
    }

    public Integer getSize() { return this.actionSlots.size(); }
}
			
