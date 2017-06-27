package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import java.util.List;

public class WorkingArea extends GameSpace {
    private ActionSlot singleSlot;
    private List<ActionSlot> advancedSlots;

    public WorkingArea(ActionSlot singleSlot, List<ActionSlot> advancedSlots) {
        this.singleSlot = singleSlot;
        this.advancedSlots = advancedSlots;
    }

    public ActionSlot getSingleSlot() {
        return singleSlot;
    }

    public List<ActionSlot> getAdvancedSlots() {
        return advancedSlots;
    }
}

