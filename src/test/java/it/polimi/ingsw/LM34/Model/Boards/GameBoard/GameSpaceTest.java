package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameSpaceTest {
    /**
     * this test will check if GameSpace's FamilyMember will properly sweep
     * @throws Exception
     */
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
        private static final long serialVersionUID = -1717858015402468021L;

        TestGameSpace(List<ActionSlot> slots) {
            super(slots);
        }
    }

}