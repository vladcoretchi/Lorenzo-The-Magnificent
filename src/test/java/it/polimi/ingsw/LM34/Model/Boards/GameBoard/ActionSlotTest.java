package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import static org.junit.Assert.*;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ActionSlotTest {

    ActionSlot actionSlot = new ActionSlot();
    FamilyMember familyMember = new FamilyMember(PawnColor.RED,DiceColor.BLACK);
    //FamilyMember familyMember = null;

    @Test
    public void insertFamilyMember() {

        try {
            actionSlot.insertFamilyMember(familyMember);
            assertFalse(familyMember.equals(null));
        }
        catch (OccupiedSlotException ex) {
            assertFalse(familyMember.equals(null));
        }

    }

}
