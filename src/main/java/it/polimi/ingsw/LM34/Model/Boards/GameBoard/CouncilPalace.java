package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CouncilPalace implements Serializable {
    ActionSlot palaceSlot;

    public CouncilPalace(ActionSlot palaceSlot) {
        this.palaceSlot = palaceSlot;
    }

    public void sweepPalace() {
        this.palaceSlot.sweep();
    }

    public ActionSlot getActionSlot() {
        return palaceSlot;
    }

    public void addFamilyMember(FamilyMember fm) {
        this.palaceSlot.insertFamilyMemberIgnoringSlotLimit(fm);
    }

    public List<PawnColor> getPlayersOrder() {
        List<PawnColor> returnList = new ArrayList<>();
        this.palaceSlot.getFamilyMembers().forEach((fm) -> {
            if(!returnList.contains(fm.getFamilyMemberColor()))
                returnList.add(PawnColor.valueOf(fm.getFamilyMemberColor().name()));
        });
        return  returnList;
    }
}

			