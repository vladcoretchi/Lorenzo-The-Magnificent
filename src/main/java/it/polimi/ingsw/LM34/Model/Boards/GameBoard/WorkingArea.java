package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: apply Singleton design pattern
public class WorkingArea extends GameSpace {
    private Integer diceValueToWork; //production and harvest
    private ActionSlot singleSlot;
    private ArrayList<ActionSlot> advancedSlots;

    public WorkingArea(ActionSlot singleSlot, ArrayList<ActionSlot> advancedSlots) {

        this.singleSlot = singleSlot;
        this.advancedSlots = advancedSlots;
    }

    public ActionSlot getSingleSlot() {
        return singleSlot;
    }

    public ArrayList<ActionSlot> getAdvancedSlots() {
        return advancedSlots;
    }
}

