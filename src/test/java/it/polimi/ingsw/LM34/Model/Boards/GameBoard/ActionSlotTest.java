package it.polimi.ingsw.LM34.Model.Boards.GameBoard;


import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionSlotTest {

    /**
     * this test will check if a family member effectively cannot be inserted into an occupied slot
     * @throws OccupiedSlotException
     */
    @Test(expected = OccupiedSlotException.class)
    public void insertFamilyMemberWithException() throws OccupiedSlotException {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        familyMember.setValue(3);
        actionSlot.insertFamilyMember(familyMember);
        actionSlot.insertFamilyMember(familyMember);

    }

    /**
     * this test will check if a family member will effectively inserted into an actionSlot
     */
    @Test
    public void insertFamilyMember() {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.BLUE, DiceColor.ORANGE);

        try {
            actionSlot.insertFamilyMember(familyMember);
            assertTrue(actionSlot.getFamilyMembers().get(0) != null);
        }
        catch (OccupiedSlotException ex) {
            fail("insertFamilyMember, when received a null familyMember, should not throw exception");
        }

    }

    /**
     * this test will check if an action slot is effectively empty
     * @throws OccupiedSlotException
     */
    @Test
    public void isEmpty() throws OccupiedSlotException {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);

        assertTrue(actionSlot.isEmpty());

        actionSlot.insertFamilyMember(familyMember);

        assertFalse(actionSlot.isEmpty());
    }

    /**
     * this test will check if an action slot will properly swept
     * @throws OccupiedSlotException
     */
    @Test
    public void sweep() throws OccupiedSlotException {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        actionSlot.insertFamilyMember(familyMember);

        assertFalse(actionSlot.isEmpty());

        actionSlot.sweep();

        assertTrue(actionSlot.isEmpty());
    }

    /**
     * this test will check if an action slot is effectively a singePawnSlot
     * @throws OccupiedSlotException
     */
    @Test
    public void isSinglePawnSlot() throws OccupiedSlotException {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
        ActionSlot actionSlot1 = new ActionSlot(false,3,resourcesBonus);

        assertTrue(actionSlot.isSinglePawnSlot());
        assertFalse(actionSlot1.isSinglePawnSlot());

    }

}
