package it.polimi.ingsw.LM34.Model.Boards.GameBoard;


import static org.junit.Assert.*;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

public class ActionSlotTest {

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

    @Test
    public void insertFamilyMember() {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = null;

        try {
            actionSlot.insertFamilyMember(familyMember);
            assertTrue(actionSlot.getFamilyMembers().get(0) == null);
        }
        catch (OccupiedSlotException ex) {
            fail("insertFamilyMember, when received a null familyMember, should not throw exception");
        }

    }

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
