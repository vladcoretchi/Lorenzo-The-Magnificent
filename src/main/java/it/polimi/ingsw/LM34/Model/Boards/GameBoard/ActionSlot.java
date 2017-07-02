package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ActionSlot implements Serializable {
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

    public void insertFamilyMember(FamilyMember fm) throws OccupiedSlotException {
        if(this.familyMembers.size() == 0)
            this.familyMembers.add(fm);
        else
            throw new OccupiedSlotException();
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
