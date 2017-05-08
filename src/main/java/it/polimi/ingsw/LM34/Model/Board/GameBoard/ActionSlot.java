package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import it.polimi.ingsw.LM34.Exception.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;


/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: evaluate to apply a factory pattern
public class ActionSlot {
    private FamilyMember familyMember; //the pawn inside the action slot
    private final Resources resources; //the bonus the slot provides
    //TODO: evaluate if diceValue should be considered at this level or in market, towers, etc.
    private Integer diceValue;
    private Integer councilPrivilege;



    //from the configuration file the game controller loads the rewards in each action slot
    //the slots instantiated are then passed to market, council and harv/prod areas in groups

    //set methods are not meant to be provided because the action slot bonus does not change during the game
    //TODO: evaluate to let a player retrieve in which slot another player's pawn is
    public ActionSlot(Resources resources, Integer councilPrivilege) {
        this.resources= resources;
        this.councilPrivilege= councilPrivilege;
    }

    public void insertFamilyMember(FamilyMember fm) throws OccupiedSlotException {
        if(familyMember!=null)
            this.familyMember= fm;
        else
            throw new OccupiedSlotException();
    }

    public boolean isEmpty() {
        return (familyMember==null) ? true : false;
    }

    //inform the player about the bonus that the slot provides to him
    public Resources getResourcesReward() {
        return this.resources;
    }

    public Integer getCouncilPrivilege() {
        return this.councilPrivilege;
    }

    //free the slot from the pawn at the end of a turn
    public void sweep() {
        this.familyMember= null;
    }

    //TODO: for now it is only used by the Market constructor
    public void setDiceValueRequirement(Integer diceValue) {
        //this.diceValue= diceValue;
    }
}
