package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import it.polimi.ingsw.LM34.Exception.Model.NoSuchAvailableSlotException;
import it.polimi.ingsw.LM34.Exception.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Bonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: apply Singleton design pattern
public class Market implements GameSpace{
    private ArrayList<ActionSlot> marketSlots;
    private final Integer diceValue; //minimum value to place FamilyMembers in the market space
/*private HashMap*/  //TODO: think of a possible use of HashMap here...

    public ArrayList<ActionSlot> getActionSlots() {
        return this.marketSlots;
    }



    //costructor called only at the beginning of the game
    //this is a space where configuration are loaded from file, so there must not be variables with hardcoded values...
    //param numberOfSlots is loaded from the configuration file
    //this class has been implemented so that action slots are only set at the beginning of the game by the controller
    //after the configurator has load the configuration that have been chosen
    public Market(ArrayList<ActionSlot> marketSlots, Integer diceValue) {
        this.marketSlots= marketSlots;

        this.diceValue= diceValue;
        //TODO:EVALUTE IF REMOVE THIS SETTER OR PROPAGATE IT ALSO TO OTHER BOARD CLASSES
        for (ActionSlot as : marketSlots)
            as.setDiceValueRequirement(diceValue);
    }

    //a player places the pawn in the slot that provides the reward he/she pleases, otherwise it throws an exception
    public void insertFamilyMember (Bonus bonus, FamilyMember fm) throws OccupiedSlotException, NoSuchAvailableSlotException {
        boolean alreadyFoundSlot= false;
        for (ActionSlot as : marketSlots)
            if (as.getBonusReward()==bonus && !alreadyFoundSlot) {
                as.insertFamilyMember(fm);
                alreadyFoundSlot= true;
            }
            else throw new NoSuchAvailableSlotException();
    }

    //perhaps this method will not be useful
    public ArrayList<ActionSlot> getAvailableSlots() {
        ArrayList<ActionSlot> availableSlots= new ArrayList<ActionSlot>();
        for (ActionSlot as : marketSlots)
            if(as.isEmpty())
                availableSlots.add(as);

        return availableSlots;
    }


    //show to the player all the different type of resource rewards each market slot provides
    public ArrayList<Bonus> getMarketSlotsBonuses() {
        ArrayList<Bonus> rewardFromSlot= new ArrayList<Bonus>();

        for (ActionSlot as : marketSlots)
            rewardFromSlot.add(as.getBonusReward());
        return rewardFromSlot;
    }

    //TODO: the values required to place a pawn in the slots is 1 or can be configured during setup?
    public Integer getDiceValueiceValue() {
        return diceValue;
    }

    public GameSpace getSpace() {
        return this;
    }
}
			
