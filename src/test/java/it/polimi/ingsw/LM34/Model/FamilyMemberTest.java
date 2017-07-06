package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import org.junit.Test;

import static org.junit.Assert.*;

public class FamilyMemberTest {
    /**
     * this test will check if a FamilyMember will be properly copied
     */
    @Test
    public void copy() {
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        familyMember.setValue(3);
        FamilyMember familyMemberThatWillReceiveCopy = familyMember.copy();

        assertEquals(familyMember.getDiceColorAssociated(), familyMemberThatWillReceiveCopy.getDiceColorAssociated());
        assertEquals(familyMember.getFamilyMemberColor(), familyMemberThatWillReceiveCopy.getFamilyMemberColor());
        assertEquals(familyMember.getValue(), familyMemberThatWillReceiveCopy.getValue());
        assertEquals(familyMember.isUsed(), familyMemberThatWillReceiveCopy.isUsed());
    }

    /**
     * this test will check if a pawn will be properly placed
     * @throws Exception
     */
    @Test
    public void placePawn() throws Exception {
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);

        assertFalse(familyMember.isUsed());

        familyMember.placePawn();

        assertTrue(familyMember.isUsed());
    }

    /**
     * this test will check if a pawn's slot will be effectively freed when a pawn will be removed
     * @throws Exception
     */
    @Test
    public void freePawn() throws Exception {
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        familyMember.placePawn();

        familyMember.freePawn();

        assertFalse(familyMember.isUsed());
    }

    /**
     * this test will check if a pawn's slot will be effectively used when it's will contains a pawn
     * @throws Exception
     */
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