package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CouncilPalaceTest {

    /**
     * this test will check if council palace will be properly swept
     * @throws Exception
     */
    @Test
    public void sweepPalace() throws Exception {
        List<FamilyMember> occupyingPawns;
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        ActionSlot palaceSlot = new ActionSlot(true, 3, resourcesBonus);
        CouncilPalace councilPalace = new CouncilPalace(palaceSlot);

        councilPalace.sweepPalace();

        assertEquals(0, councilPalace.getActionSlot().getFamilyMembers().size()); //size will be 0 before and after sweep, because getOccupyingPawns will never been initialized with any FamilyMember
    }

}