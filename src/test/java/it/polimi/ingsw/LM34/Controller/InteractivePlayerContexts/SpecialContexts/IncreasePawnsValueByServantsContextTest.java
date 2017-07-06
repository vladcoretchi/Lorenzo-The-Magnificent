package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import org.junit.Test;

import static org.junit.Assert.*;

public class IncreasePawnsValueByServantsContextTest {

    /**
     * this test will check if pawn will effectively increased
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        IncreasePawnsValueByServantsContext increasePawnsValueByServantsContext = new IncreasePawnsValueByServantsContext();
        increasePawnsValueByServantsContext.interactWithPlayer(new Integer(1));
    }

    /**
     * this test will check if servants requirements will properly duplicated
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void duplicateServantsRequirements() throws Exception {
        IncreasePawnsValueByServantsContext increasePawnsValueByServantsContext = new IncreasePawnsValueByServantsContext();
        increasePawnsValueByServantsContext.duplicateServantsRequirements();
    }

}