package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by robertodorata on 6/24/17.
 */
public class TowerSlotTest {
    ResourcesBonus resourcesBonus = new ResourcesBonus(5);
    private TowerSlot towerSlot = new TowerSlot(true, 5, resourcesBonus);
    @Test
    public void isEmpty() {
        assertTrue(towerSlot.isEmpty());
    }
}
