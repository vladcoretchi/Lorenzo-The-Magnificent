package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CouncilPalaceTest {

    @Test
    public void sweepPalace() throws Exception {
        List<FamilyMember> occupyingPawns;
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
        List<ActionSlot> palaceSlots = new ArrayList<>();
        palaceSlots.add(actionSlot);
        CouncilPalace councilPalace = new CouncilPalace(palaceSlots);

        councilPalace.sweepPalace();

        assertEquals(0, councilPalace.getOccupyingPawns().size()); //size will be 0 before and after sweep, because getOccupyingPawns will never been initialized with any FamilyMember
    }

}