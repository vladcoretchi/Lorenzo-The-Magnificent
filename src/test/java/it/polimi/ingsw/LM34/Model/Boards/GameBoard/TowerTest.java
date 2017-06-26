package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TowerTest {
    @Test
    public void addCard() throws Exception {
        Tower tower = new Tower(DevelopmentCardColor.BLUE);
        AbstractDevelopmentCard abstractDevelopmentCard = new TestAbstractDevelopmentCard();
        tower.addCard(abstractDevelopmentCard);

        assertNotNull(tower);

    }

    @Test
    public void isTowerEmpty() throws Exception {
        Tower tower = new Tower(DevelopmentCardColor.BLUE);
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        TowerSlot towerSlot = new TowerSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        towerSlot.insertFamilyMember(familyMember);
        List<TowerSlot> towerSlots = new ArrayList<>();
        towerSlots.add(towerSlot);
        AbstractDevelopmentCard abstractDevelopmentCard = new TestAbstractDevelopmentCard();
        tower.addCard(abstractDevelopmentCard);
        tower.setSlots(towerSlots);

        tower.setSlots(towerSlots);

        assertFalse(tower.isTowerEmpty());
    }

    @Test
    public void sweep() throws Exception {
        Tower tower = new Tower(DevelopmentCardColor.BLUE);
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        TowerSlot towerSlot = new TowerSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        towerSlot.insertFamilyMember(familyMember);
        List<TowerSlot> towerSlots = new ArrayList<>();
        towerSlots.add(towerSlot);
        AbstractDevelopmentCard abstractDevelopmentCard = new TestAbstractDevelopmentCard();
        tower.addCard(abstractDevelopmentCard);
        tower.setSlots(towerSlots);
        //verify that before sweep, the tower isn`t already null
        assertFalse(tower.isTowerEmpty());
        tower.sweep();

        assertTrue(tower.isTowerEmpty());

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
