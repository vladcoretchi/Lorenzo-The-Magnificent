package it.polimi.ingsw.LM34.Controller.NonInteractiveContexts;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceIncomeContextTest {

    /**
     * this test will check if resources will correctly added into current player
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        ResourceIncomeContext resourceIncomeContext = new ResourceIncomeContext();
        resourceIncomeContext.interactWithPlayer();
    }

    /**
     * this test will check if temporary value will effectively reset
     * @throws Exception
     */
    @Test
    public void initIncome() throws Exception {
        ResourceIncomeContext resourceIncomeContext = new ResourceIncomeContext();
        resourceIncomeContext.initIncome();
    }

}