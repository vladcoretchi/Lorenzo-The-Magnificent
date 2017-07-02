package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class GameSpaceTest {

    @Test
    public void sweep() throws Exception {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourceBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourceBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        actionSlot.insertFamilyMember(familyMember);
        List<ActionSlot> actionSlots = new ArrayList<>();
        actionSlots.add(actionSlot);
        GameSpace gameSpace = new TestGameSpace(actionSlots);

        assertEquals(1, gameSpace.getActionSlots().get(0).getFamilyMembers().size());

        gameSpace.sweep();

        assertEquals(0, gameSpace.getActionSlots().get(0).getFamilyMembers().size());
    }


    private class TestGameSpace extends GameSpace {
        TestGameSpace(List<ActionSlot> slots) {
            super(slots);
        }
    }

}