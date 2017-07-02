package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

public class TowerTest {
   /* @Test
    public void addCard() throws Exception {
       /* Tower tower = new Tower(DevelopmentCardColor.BLUE, null);
        AbstractDevelopmentCard abstractDevelopmentCard = new TestAbstractDevelopmentCard();
        tower.addCard(abstractDevelopmentCard);

        assertNotNull(tower);

    }

    @Test
    public void isTowerEmpty() throws Exception {
        Tower tower;
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        TowerSlot towerSlot = new TowerSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        towerSlot.insertFamilyMember(familyMember);
        List<TowerSlot> towerSlots = new ArrayList<>();
        towerSlots.add(towerSlot);
        AbstractDevelopmentCard abstractDevelopmentCard = new TestAbstractDevelopmentCard();
        tower = new Tower(DevelopmentCardColor.BLUE, towerSlots);
        tower.addCard(abstractDevelopmentCard);


        assertFalse(tower.isTowerEmpty());
    }

    @Test
    public void sweep() throws Exception {
        Tower tower;
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        TowerSlot towerSlot = new TowerSlot(true, 3, resourcesBonus);
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        towerSlot.insertFamilyMember(familyMember);
        List<TowerSlot> towerSlots = new ArrayList<>();
        towerSlots.add(towerSlot);
        AbstractDevelopmentCard abstractDevelopmentCard = new TestAbstractDevelopmentCard();
        tower = new Tower(DevelopmentCardColor.BLUE, towerSlots);
        tower.addCard(abstractDevelopmentCard);
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

    }*/

}
