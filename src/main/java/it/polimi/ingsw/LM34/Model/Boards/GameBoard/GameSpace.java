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

    public List<ActionSlot> getActionSlots() {
        return this.actionSlots;
    }
}
