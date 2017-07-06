package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TowerTest {
    /**
     * this test will test if cards will be correctly added into tower's slot
     */
    @Test
    public void addCard() throws Exception {
        List<TowerSlot> towerSlots = new ArrayList<>();
        Resources resources1 = new Resources(1,1,1,1,1,1,1);
        Resources resources2 = new Resources(2,2,2,2,2,2,2);
        ResourcesBonus resourcesBonus1 = new ResourcesBonus(resources1, 1);
        ResourcesBonus resourcesBonus2 = new ResourcesBonus(resources2, 2);
        TowerSlot towerSlot1 = new TowerSlot(true, 1, resourcesBonus1);
        TowerSlot towerSlot2 = new TowerSlot(true, 2, resourcesBonus2);
        towerSlots.add(towerSlot1);
        towerSlots.add(towerSlot2);
        Tower tower = new Tower(DevelopmentCardColor.BLUE, towerSlots);
        AbstractDevelopmentCard abstractDevelopmentCard1 = new InitializeDevelopmentCard("carta1", 1, null, null, null, null);
        AbstractDevelopmentCard abstractDevelopmentCard2 = new InitializeDevelopmentCard("carta2", 2, null, null, null, null);
        tower.addCard(abstractDevelopmentCard1);
        tower.addCard(abstractDevelopmentCard2);

        assertEquals(2, tower.getCardsStored().size());
    }

    /**
     * this test will check if a tower is empty
     */
    @Test
    public void isTowerEmpty() throws Exception {
        List<TowerSlot> towerSlots = new ArrayList<>();
        Tower tower = new Tower(DevelopmentCardColor.BLUE, towerSlots);

        assertTrue(tower.isTowerEmpty());

        Resources resources1 = new Resources(1,1,1,1,1,1,1);
        Resources resources2 = new Resources(2,2,2,2,2,2,2);
        ResourcesBonus resourcesBonus1 = new ResourcesBonus(resources1, 1);
        ResourcesBonus resourcesBonus2 = new ResourcesBonus(resources2, 2);
        TowerSlot towerSlot1 = new TowerSlot(true, 1, resourcesBonus1);
        TowerSlot towerSlot2 = new TowerSlot(true, 2, resourcesBonus2);
        towerSlots.add(towerSlot1);
        towerSlots.add(towerSlot2);

        Tower tower2 = new Tower(DevelopmentCardColor.GREEN, towerSlots);

        assertTrue(tower2.isTowerEmpty());
    }

    /**
     * this tower will check if a tower will properly sweep
     */
    @Test
    public void sweep() {
        List<TowerSlot> towerSlots = new ArrayList<>();
        Resources resources1 = new Resources(1,1,1,1,1,1,1);
        Resources resources2 = new Resources(2,2,2,2,2,2,2);
        ResourcesBonus resourcesBonus1 = new ResourcesBonus(resources1, 1);
        ResourcesBonus resourcesBonus2 = new ResourcesBonus(resources2, 2);
        TowerSlot towerSlot1 = new TowerSlot(true, 1, resourcesBonus1);
        TowerSlot towerSlot2 = new TowerSlot(true, 2, resourcesBonus2);
        towerSlots.add(towerSlot1);
        towerSlots.add(towerSlot2);

        Tower tower = new Tower(DevelopmentCardColor.BLUE, towerSlots);

        tower.sweep();

        assertTrue(tower.isTowerEmpty());


    }

    class InitializeDevelopmentCard extends AbstractDevelopmentCard {

        public InitializeDevelopmentCard(String name, Integer period, DevelopmentCardColor color, AbstractEffect permanentBonus, List<AbstractEffect> instantBonus, Resources resourceRequired) {

            this.name = name;
            this.period = period;
            this.color = color;
            this.permanentBonus = permanentBonus;
            this.instantBonus = instantBonus;
            this.resourceRequired = resourceRequired;
        }
    }

}