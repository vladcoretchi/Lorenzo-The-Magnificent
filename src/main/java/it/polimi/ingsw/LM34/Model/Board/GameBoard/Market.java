package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import it.polimi.ingsw.LM34.Exception.Model.NoSuchAvailableSlotException;
import it.polimi.ingsw.LM34.Exception.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: apply Singleton design pattern
public class Market {
    private ArrayList<ActionSlot> marketSlots;
/*private HashMap*/  //TODO: think of a possible use of HashMap here...

    public ArrayList<ActionSlot> getActionSlots() {
        return this.marketSlots;
    }



    //costructor called only at the beginning of the game
    //this is a space where configuration are loaded from file, so there must not be variables with hardcoded values...
    //param numberOfSlots is loaded from the configuration file
    //this class has been implemented so that action slots are only set at the beginning of the game by the controller
    //after the configurator has load the configuration that have been chosen
    public Market(ArrayList<ActionSlot> marketSlots) {
        this.marketSlots= marketSlots;
    }

    //a player places the pawn in the slot that provides the reward he/she pleases, otherwise it throws an exception
    public void insertFamilyMember (Resources resources, FamilyMember fm) throws OccupiedSlotException, NoSuchAvailableSlotException {
        boolean alreadyFoundSlot= false;
        for (ActionSlot as : marketSlots)
            if (as.getResourcesReward()==resources && !alreadyFoundSlot) {
                as.insertFamilyMember(fm);
                alreadyFoundSlot= true;
            }
            else throw new NoSuchAvailableSlotException();
    }


    public Market() {
        marketSlots = new ArrayList<ActionSlot>();
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
    public ArrayList<Resources> getMarketSlotsBonuses() {
        ArrayList<Resources> rewardFromSlot= new ArrayList<Resources>();

        for (ActionSlot as : marketSlots)
            rewardFromSlot.add(as.getResourcesReward());
        return rewardFromSlot;
    }

    public void addSlot(ActionSlot as) {
        marketSlots.add(as);
    }

    public Integer getSize() {
        return marketSlots.size();
    }
}
			
