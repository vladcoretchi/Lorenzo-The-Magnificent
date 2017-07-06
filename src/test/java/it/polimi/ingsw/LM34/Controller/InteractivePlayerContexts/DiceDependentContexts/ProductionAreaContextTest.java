package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductionAreaContextTest {

    /**
     * this class wil test if action slot will properly managed
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        ProductionAreaContext productionAreaContext = new ProductionAreaContext();
        productionAreaContext.interactWithPlayer(new Integer(1));
    }

}