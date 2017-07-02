package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GameSpace implements Serializable {
    List<ActionSlot> actionSlots;

    GameSpace(List<ActionSlot> slots) {
        this.actionSlots = slots;
    }

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
