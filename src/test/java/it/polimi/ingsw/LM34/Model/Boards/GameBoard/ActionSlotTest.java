package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ActionSlotTest {

    @Test(expected = OccupiedSlotException.class)
    public void insertFamilyMember() throws OccupiedSlotException {
        ActionSlot actionSlot = new ActionSlot();
        FamilyMember familyMember = new FamilyMember(PawnColor.RED,DiceColor.BLACK);
        FamilyMember familyMember2 = new FamilyMember(PawnColor.BLUE,DiceColor.ORANGE);

        actionSlot.insertFamilyMember(familyMember);
        assertFalse(actionSlot.isEmpty());

        actionSlot.insertFamilyMember(familyMember2);
    }
}
