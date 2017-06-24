package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Model.NoSuchAvailableSlotException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MarketTest {

    private Market market;
    private ResourcesBonus resourcesBonus = new ResourcesBonus(5);
    private FamilyMember fm = new FamilyMember(PawnColor.RED, DiceColor.BLACK);

    @Before
    public void setUp() {
        ArrayList<ActionSlot> as = new ArrayList<>();
        ActionSlot actionSlot = new ActionSlot(true, 3, resourcesBonus);
        as.add(0, actionSlot);
        as.add(1, actionSlot);
        as.add(2, actionSlot);
        Market market = new Market(as);
    }

    @Test(expected = NoSuchAvailableSlotException.class)
    public void insertFamilyMembertest() throws OccupiedSlotException, NoSuchAvailableSlotException {

        market.insertFamilyMember(resourcesBonus, fm);
    }

    @Test
    public void sizetest() {

        assertTrue(market.getSize() == 0);
    }

}
