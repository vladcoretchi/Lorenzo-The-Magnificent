package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import org.junit.Test;

import static org.junit.Assert.*;

public class FamilyMemberTest {

    @Test
    public void copy() throws Exception {
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        familyMember.setValue(3);
        FamilyMember familyMemberThatWillReceiveCopy = familyMember.copy();

        assertEquals(familyMember.getDiceColorAssociated(), familyMemberThatWillReceiveCopy.getDiceColorAssociated());
        assertEquals(familyMember.getFamilyMemberColor(), familyMemberThatWillReceiveCopy.getFamilyMemberColor());
        assertEquals(familyMember.getValue(), familyMemberThatWillReceiveCopy.getValue());
        assertEquals(familyMember.isUsed(), familyMemberThatWillReceiveCopy.isUsed());
    }

    @Test
    public void placePawn() throws Exception {
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);

        assertFalse(familyMember.isUsed());

        familyMember.placePawn();

        assertTrue(familyMember.isUsed());
    }

    @Test
    public void freePawn() throws Exception {
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        familyMember.placePawn();

        familyMember.freePawn();

        assertFalse(familyMember.isUsed());
    }

    @Test
    public void isUsed() throws Exception {
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);

        assertFalse(familyMember.isUsed());

        familyMember.placePawn();

        assertTrue(familyMember.isUsed());

        familyMember.freePawn();

        assertFalse(familyMember.isUsed());
    }

}