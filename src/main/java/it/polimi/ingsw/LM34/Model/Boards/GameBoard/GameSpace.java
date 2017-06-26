package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiulioComi on 13/05/2017.
 */
public abstract class GameSpace implements Serializable {
    protected List<ActionSlot> actionSlots;

    public GameSpace() {
        actionSlots = new ArrayList<>();
    }

    public void sweep() {
            for (ActionSlot as : actionSlots)
                as.sweep();
        }

    public void addSlot(ActionSlot as) {
        actionSlots.add(as);
    }

    public List<ActionSlot> getActionSlots() {
        return this.actionSlots;
    }

    public List<ActionSlot> getAvailableSlots() {
        ArrayList<ActionSlot> availableSlots = new ArrayList<>();
        for (ActionSlot as : actionSlots)
            if(as.isEmpty())
                availableSlots.add(as);

        return availableSlots;
    }

}
