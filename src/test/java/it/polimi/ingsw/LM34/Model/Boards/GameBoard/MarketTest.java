package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.FamilyMember;
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
        ActionSlot actionSlot = new ActionSlot();
        actionSlots.add(actionSlot); //null familyMember, unless insertFamilymember will fail
        Market market = new Market(actionSlots);
        market.insertFamilyMember(slotIndex, familyMember);
    }

}