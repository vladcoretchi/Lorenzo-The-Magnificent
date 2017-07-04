package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.io.Serializable;
import java.util.List;

public class Market extends GameSpace implements Serializable {
    private static final long serialVersionUID = 5521854884094710325L;

    /**
     * costructor called only at the beginning of the game
     *this class has been implemented so that action slots are only set at the beginning of the game by the controller
     *after the {@link it.polimi.ingsw.LM34.Utils.Configurator} has load the configuration that have been chosen
    */
    public Market(List<ActionSlot> actionSlots) {
        super(actionSlots);
    }

    public void insertFamilyMember(Integer slotIndex, FamilyMember fm) throws OccupiedSlotException{
        this.actionSlots.get(slotIndex).insertFamilyMember(fm);
    }

    public Integer getSize() { return this.actionSlots.size(); }
}
			
