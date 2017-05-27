package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.util.ArrayList;

//TODO: apply Singleton design pattern
public class CouncilPalace extends GameSpace {
    private ArrayList<FamilyMember> occupyingPawns; //FamilyMembers in the palace
    private final Integer diceValue; //minimum value to place FamilyMembers in the market space
    private ResourcesBonus reward;
    //The CouncilPalace is a special case among the board classes...
    // In fact, it does not need ActionSlots from a design point of view
    //which makes its implementation more straightforward
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

    public ResourcesBonus getReward() { return this.reward; }


    //TODO: remove from here and do this in the controller
    //return the order of the players in the next turn
    public ArrayList<FamilyMember> getOccupyingPawns() {
        return this.occupyingPawns;
    }


    public Integer getDiceValue() {
        return diceValue;
    }
}

			