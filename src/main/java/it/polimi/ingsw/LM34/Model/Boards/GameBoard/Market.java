package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Exceptions.Model.NoSuchAvailableSlotException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: apply Singleton design pattern
public class Market extends GameSpace {
    private ArrayList<ActionSlot> marketSlots;
    private Integer diceValue;

    /*costructor called only at the beginning of the game
    this class has been implemented so that action slots are only set at the beginning of the game by the controller
    after the configurator has load the configuration that have been chosen
    */
    public Market(ArrayList<ActionSlot> actionSlots, Integer diceValue) {
        this.marketSlots= actionSlots;
        this.diceValue = diceValue;
    }

    //a player places the pawn in the slot that provides the reward he/she pleases, otherwise it throws an exception
    public void insertFamilyMember (ResourcesBonus resources, FamilyMember fm) throws OccupiedSlotException, NoSuchAvailableSlotException {
        boolean alreadyFoundSlot= false;
        for (ActionSlot as : actionSlots)
            if (as.getResourcesReward()==resources && !alreadyFoundSlot) {
                as.insertFamilyMember(fm);
                alreadyFoundSlot= true;
            }
            else throw new NoSuchAvailableSlotException();
    }

    public Integer getSize() { return this.actionSlots.size(); }

    public Integer getDiceValue() { return this.diceValue; }

}
			
