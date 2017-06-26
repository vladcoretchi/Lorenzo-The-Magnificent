package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;
import static org.junit.Assert.*;

public class TowerSlotTest {

    @Test
    public void hasCard() throws Exception {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        TowerSlot towerSlot = new TowerSlot(true, 3, resourcesBonus);
        AbstractDevelopmentCard abstractDevelopmentCard = new TestAbstractDevelopmentCard();

        assertFalse(towerSlot.hasCard());

        towerSlot.setCardStored(abstractDevelopmentCard);

        assertTrue(towerSlot.hasCard());

    }

    @Test
    public void sweepTowerSlot() throws Exception {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        TowerSlot towerSlot = new TowerSlot(true, 3, resourcesBonus);
        AbstractDevelopmentCard abstractDevelopmentCard = new TestAbstractDevelopmentCard();

        towerSlot.setCardStored(abstractDevelopmentCard);

        towerSlot.sweepTowerSlot();

        assertFalse(towerSlot.hasCard());

    }

    private class TestAbstractDevelopmentCard extends AbstractDevelopmentCard {

        public TestAbstractDevelopmentCard() {
            this.name = "testCard";
            this.period = 1;
            this.color = DevelopmentCardColor.BLUE;
            this.permanentBonus = null;
            this.resourceRequired = new Resources(1,1,1,1,1,1,1);
        }

    }

}
