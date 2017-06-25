package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;

import java.io.Serializable;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: evaluate to apply a factory pattern

public class ActionSlot implements Serializable {
    protected FamilyMember familyMember; //the pawn inside the action slot
    protected ResourcesBonus resources; //the bonus the slot provides
    //TODO: evaluate if diceValue should be considered at this level or in market, towers, etc.
    protected Integer diceValue;
    protected boolean singlePawnSlot;

    public ActionSlot() {
        resources = new ResourcesBonus(new Resources(), 0);
    }
    //from the configuration file the game controller loads the rewards in each action slot
    //the slots instantiated are then passed to market, council and working areas in groups
    //set methods are not meant to be provided because the action slot bonus does not change during the game
    public ActionSlot(boolean singlePawnSlot,Integer diceValue, ResourcesBonus resources) {
        this.diceValue = diceValue;
        this.resources = resources;
        this.singlePawnSlot = singlePawnSlot;
    }

    public void insertFamilyMember(FamilyMember fm) throws OccupiedSlotException {
        if(familyMember == null)
            this.familyMember = fm;
        else
            throw new OccupiedSlotException();
    }

    public boolean isEmpty() {
        return (familyMember == null) ? true : false;
    }

    //inform the player about the bonus that the slot provides to him
    public ResourcesBonus getResourcesReward() {
        return this.resources;
    }


    //free the slot from the pawn at the end of a turn
    public void sweep() {
        this.familyMember = null;
    }

    public Integer getDiceValue() { return this.diceValue; }

    public boolean isSinglePawnSlot() { return this.singlePawnSlot; }

    public FamilyMember getFamilyMember() {
        return this.familyMember;
    }
}
