package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import java.util.List;
import java.util.stream.Collectors;

public class WorkingArea extends GameSpace {

    public WorkingArea(List<ActionSlot> actionSlots) {
        super(actionSlots);
    }

    public ActionSlot getSingleSlot() {
        return this.actionSlots.stream().filter(as -> !as.isSinglePawnSlot()).collect(Collectors.toList()).get(0);
    }

    public List<ActionSlot> getAdvancedSlots() {
        return this.actionSlots.stream().filter(ActionSlot::isSinglePawnSlot).collect(Collectors.toList());
    }
}

