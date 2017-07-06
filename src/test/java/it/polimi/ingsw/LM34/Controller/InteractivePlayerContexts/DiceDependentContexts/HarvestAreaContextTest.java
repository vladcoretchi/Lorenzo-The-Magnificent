package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import org.junit.Test;

import static org.junit.Assert.*;

public class HarvestAreaContextTest {

    /**
     * this test will check if harvest area slot will properly managed
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        HarvestAreaContext harvestAreaContext = new HarvestAreaContext();
        harvestAreaContext.interactWithPlayer(new Integer(1));
    }

}