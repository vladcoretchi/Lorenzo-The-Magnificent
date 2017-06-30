package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CouncilPalace implements Serializable {
    ActionSlot palaceSlot;
    private List<FamilyMember> occupyingPawns; //FamilyMembers in the palace
    //The CouncilPalace is a special case among the board classes...
    // In fact, it does not need ActionSlots from a design point of view
    //which makes its implementation more straightforward

    public CouncilPalace(ActionSlot palaceSlot) {
        occupyingPawns = new ArrayList<FamilyMember>();
        this.palaceSlot = palaceSlot;
    }

    public void sweepPalace() {
        occupyingPawns.clear();
    }

    //return the order of the players in the next turn
    public List<FamilyMember> getOccupyingPawns() {
        return this.occupyingPawns;
    }

    public ActionSlot getActionSlot() {
        return palaceSlot;
    }

    public void addPawn(FamilyMember fm) {
        occupyingPawns.add(fm);
    }
}

			