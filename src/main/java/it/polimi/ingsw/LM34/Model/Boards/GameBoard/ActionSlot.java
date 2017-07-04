package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ActionSlot implements Serializable {
    private static final long serialVersionUID = 4982569359763654675L;
    private List<FamilyMember> familyMembers; //the pawns inside the action slot
    private ResourcesBonus resources; //the bonus the slot provides
    private Integer diceValue;
    private Boolean singlePawnSlot;

    public ActionSlot(boolean singlePawnSlot, Integer diceValue, ResourcesBonus resources) {
        this.diceValue = diceValue;
        this.resources = resources;
        this.singlePawnSlot = singlePawnSlot;
        this.familyMembers = new ArrayList<>();
    }

    /**
     * A player places the pawn in the slot that provides the reward he/she pleases, otherwise it throws an exception
     */
    public void insertFamilyMember(FamilyMember fm) throws OccupiedSlotException {
        if(this.isSinglePawnSlot() && !this.familyMembers.isEmpty())
            throw new OccupiedSlotException();
        else
            this.familyMembers.add(fm);
    }

    public void insertFamilyMemberIgnoringSlotLimit(FamilyMember fm) {
        this.familyMembers.add(fm);
    }

    public boolean isEmpty() {
        return this.familyMembers.isEmpty();
    }

    public ResourcesBonus getResourcesReward() {
        return this.resources;
    }

    public void sweep() {
        this.familyMembers.clear();
    }

    public Integer getDiceValue() { return this.diceValue; }

    public Boolean isSinglePawnSlot() { return this.singlePawnSlot; }

    public List<FamilyMember> getFamilyMembers() {
        return this.familyMembers;
    }
}
