package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MarketTest {

    @Test
    public void insertFamilyMember() throws Exception {
        Integer slotIndex = 0;
        FamilyMember familyMember = new FamilyMember(PawnColor.GREEN, DiceColor.BLACK);
        List<ActionSlot> actionSlots = new ArrayList<>();
        ActionSlot actionSlot = new ActionSlot(false, 2, new ResourcesBonus(new Resources(1,2,3,4), 5));
        actionSlots.add(actionSlot); //null familyMember, unless insertFamilymember will fail
        Market market = new Market(actionSlots);
        market.insertFamilyMember(slotIndex, familyMember);
    }

}