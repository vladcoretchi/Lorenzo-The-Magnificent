package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GameSpace implements Serializable {
    private static final long serialVersionUID = -3770094102046167420L;
    List<ActionSlot> actionSlots;

    GameSpace(List<ActionSlot> slots) {
        this.actionSlots = slots;
    }

    /**
     * Free each action slot from the {@link it.polimi.ingsw.LM34.Model.FamilyMember} placed inside them
     */
    public void sweep() {
        this.actionSlots.forEach(ActionSlot::sweep);
    }

    /**
     * @return all the slots of this gameSpace
     */
    public List<ActionSlot> getActionSlots() {
        return this.actionSlots;
    }

    /**
     * @return the available slots of this gameSpace
     */
    public List<ActionSlot> getAvailableSlots() {
        List<ActionSlot> availableSlots = new ArrayList<>();
        for (ActionSlot as : actionSlots)
            if(as.isEmpty())
                availableSlots.add(as);

        return availableSlots;
    }
}
