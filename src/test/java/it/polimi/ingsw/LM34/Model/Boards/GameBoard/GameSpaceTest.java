package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameSpaceTest {

    @Test
    public void sweep() throws Exception {
        GameSpace gameSpace = new TestGameSpace();
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourceBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourceBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        actionSlot.insertFamilyMember(familyMember);
        gameSpace.addSlot(actionSlot);

        assertNotNull(gameSpace.getActionSlots().get(0).getFamilyMember());

        gameSpace.sweep();

        assertNull(gameSpace.getActionSlots().get(0).getFamilyMember());
    }

    @Test
    public void addSlot() throws Exception {
        GameSpace gameSpace = new TestGameSpace();
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourceBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourceBonus);
        gameSpace.addSlot(new ActionSlot(true, 3, resourceBonus));

        assertNotNull(gameSpace.getActionSlots());

    }

    private class TestGameSpace extends GameSpace {

    }

}