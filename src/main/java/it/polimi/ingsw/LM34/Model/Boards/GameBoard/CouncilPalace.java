package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import java.io.Serializable;
import java.util.ArrayList;

public class CouncilPalace extends GameSpace implements Serializable {
    private ArrayList<FamilyMember> occupyingPawns; //FamilyMembers in the palace
    private ActionSlot actionSlot;
    //The CouncilPalace is a special case among the board classes...
    // In fact, it does not need ActionSlots from a design point of view
    //which makes its implementation more straightforward
    public CouncilPalace(ActionSlot palaceSlot) {
        occupyingPawns = new ArrayList<FamilyMember>();
        actionSlot = palaceSlot;
    }

    public void insertFamilyMember(FamilyMember fm) {
        occupyingPawns.add(fm);
    }

    public void sweepPalace() {
        occupyingPawns.clear();
    }

    //Returns the council privilege
    public ResourcesBonus getReward() { return this.actionSlot.getResourcesReward(); }

    //return the order of the players in the next turn
    public ArrayList<FamilyMember> getOccupyingPawns() {
        return this.occupyingPawns;
    }

}

			