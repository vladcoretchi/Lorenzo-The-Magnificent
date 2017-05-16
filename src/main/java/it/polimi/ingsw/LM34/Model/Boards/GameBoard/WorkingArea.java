package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: apply Singleton design pattern
public class WorkingArea extends GameSpace {
    private Integer diceValueToWork; //production and harvest

    public WorkingArea(ArrayList<ActionSlot> actionSlots) {
        this.actionSlots= actionSlots;
    }

}

