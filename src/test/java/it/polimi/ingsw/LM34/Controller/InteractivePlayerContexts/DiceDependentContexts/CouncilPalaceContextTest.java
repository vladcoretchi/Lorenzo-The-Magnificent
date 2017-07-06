package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import org.junit.Test;

import static org.junit.Assert.*;

public class CouncilPalaceContextTest {

    /**
     * this test will check if family member will properly managed
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        CouncilPalaceContext councilPalaceContext = new CouncilPalaceContext();
        councilPalaceContext.interactWithPlayer();
    }

}