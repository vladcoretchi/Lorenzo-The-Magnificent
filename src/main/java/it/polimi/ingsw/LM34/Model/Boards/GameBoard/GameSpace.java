package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 13/05/2017.
 */
public abstract class GameSpace {
    protected ArrayList<ActionSlot> actionSlots;

    public void sweep() {
            for (ActionSlot as : actionSlots)
                as.sweep();
        }

    public void addSlot(ActionSlot as) {
        actionSlots.add(as);
    }

    public ArrayList<ActionSlot> getActionSlots() {
        return this.actionSlots;
    }


    public ArrayList<ActionSlot> getAvailableSlots() {
        ArrayList<ActionSlot> availableSlots = new ArrayList<ActionSlot>();
        for (ActionSlot as : actionSlots)
            if(as.isEmpty())
                availableSlots.add(as);

        return availableSlots;
    }

}
