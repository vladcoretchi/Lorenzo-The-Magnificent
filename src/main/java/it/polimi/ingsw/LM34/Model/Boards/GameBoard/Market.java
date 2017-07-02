package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.io.Serializable;
import java.util.List;

//TODO: apply Singleton design pattern
public class Market extends GameSpace implements Serializable {

    public Market(List<ActionSlot> actionSlots) {
        super(actionSlots);
    }

    public void insertFamilyMember(Integer slotIndex, FamilyMember fm) throws OccupiedSlotException{
        this.actionSlots.get(slotIndex).insertFamilyMember(fm);
    }

    public Integer getSize() { return this.actionSlots.size(); }
}
			
