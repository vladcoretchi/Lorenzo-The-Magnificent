package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import it.polimi.ingsw.LM34.Exception.Model.ActionSlotInconsistencyException;
import it.polimi.ingsw.LM34.Exception.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: apply Singleton design pattern
public class HarvestArea {
    private ActionSlot singleSlot;
    private ArrayList<ActionSlot> advancedSlots = new ArrayList<ActionSlot>();
    private Integer normalAreaDiceValue;
    private Integer advancedAreaDiceValue;

/*TODO: evalute where to place the penalty of -3 on the dice value in case of advancedSlot id est choice if it must be handled here or in the controller*/


    public HarvestArea(ArrayList<ActionSlot> slots, Integer diceValue, Integer advancedAreaDiceValue) throws ActionSlotInconsistencyException {
        if (slots.isEmpty()) {
            this.singleSlot = slots.get(0);
            slots.remove(0); //the first action slot is the single one
        } else
            throw new ActionSlotInconsistencyException();

        this.advancedSlots= slots; //all the remaining slot in the arraylist configured are in the AdvancedSlot category
        this.normalAreaDiceValue= diceValue;
        this.advancedAreaDiceValue= advancedAreaDiceValue;
    }

    public void insertFamilyMemberInSingleSlot(FamilyMember fm) throws OccupiedSlotException {
        singleSlot.insertFamilyMember(fm);
    }


    public void insertFamilyMemberInAdvancedSlot(FamilyMember fm) throws OccupiedSlotException {
        boolean alreadyFoundSlot= false;
        for (ActionSlot as : advancedSlots)
            if (as.isEmpty() && !alreadyFoundSlot) {
                as.insertFamilyMember(fm);
                alreadyFoundSlot = true;
            }

    }

    //TODO: the values required to place a pawn in the slots is 1 or can be configured during setup?
    public Integer getNormalAreaDiceValue() {
        return normalAreaDiceValue;
    }

    public Integer getAdvancedAreaDiceValue() {
        return advancedAreaDiceValue;
    }


}

