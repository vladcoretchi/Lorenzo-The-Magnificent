package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by robertodorata on 6/24/17.
 */
public class TowerSlotTest {
    @Test
    public void isEmpty() {
        ResourcesBonus resourcesBonus = new ResourcesBonus(5);
        TowerSlot towerSlot = new TowerSlot(true, 5, resourcesBonus);
        assertTrue(towerSlot.isEmpty());
    }

    @Test
    public void isNotEmp() {
        ResourcesBonus resourcesBonus = new ResourcesBonus(5);
        TowerSlot towerSlot = new TowerSlot(true, 5, resourcesBonus);
        TerritoryCard card = new TerritoryCard("Woods", 2, 1, null, new ResourcesBonus(new Resources(2,3,4,5),9));
        towerSlot.setCardStored(card);
        assertFalse(towerSlot.isEmpty());
    }
}
