package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import java.util.ArrayList;

import it.polimi.ingsw.LM34.Model.FamilyMember;

//TODO: apply Singleton design pattern
public class CouncilPalace implements GameSpace {
    private ArrayList<FamilyMember> occupyingPawns; //FamilyMembers in the palace
    private final Integer diceValue; //minimum value to place FamilyMembers in the market space


//The CouncilPalace is a special case among the board classes...
// In fact, it does not need ActionSlots from a design point of view
//which makes its implementation more straightforward

    //costructor called only at the beginning of the game
//this is a space where configuration are loaded from file, so there must not be variables with hardcoded values...
    public CouncilPalace(Integer diceValue) {
        this.diceValue= diceValue;
        occupyingPawns= new ArrayList<FamilyMember>();
    }

    public void insertFamilyMember(FamilyMember fm) {
        occupyingPawns.add(fm);
    }

    public void sweepPalace() {
        occupyingPawns.clear();
    }

    public boolean isOccupied() {
        return occupyingPawns.isEmpty();
    }

    //return the order of the players in the next turn
    public ArrayList<FamilyMember> getNextTurnOrder() {
        ArrayList<FamilyMember> tempOrder= new ArrayList<FamilyMember>();
        for (FamilyMember fm : occupyingPawns)
            if(!occupyingPawns.contains(fm))
                tempOrder.add(fm);
        return tempOrder;
    }

    @Override
    public GameSpace getSpace() {
        return this;
    }
}

			