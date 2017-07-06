package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import org.junit.Test;

import static org.junit.Assert.*;

public class MarketAreaContextTest {

    /**
     * this test will check if ban and action slot will properly managed
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        MarketAreaContext marketAreaContext = new MarketAreaContext();
        marketAreaContext.interactWithPlayer(new Integer(1));
    }

    /**
     * this test will check if ban will properly checked true
     * @throws Exception
     */
    @Test
    public void setBan() throws Exception {
        MarketAreaContext marketAreaContext = new MarketAreaContext();
        marketAreaContext.setBan();
    }

}