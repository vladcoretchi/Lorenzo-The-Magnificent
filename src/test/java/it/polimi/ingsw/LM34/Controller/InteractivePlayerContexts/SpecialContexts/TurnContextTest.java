package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TurnContextTest {
    /**
     * this test will check if all observers will properly reactivated
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void initContext() throws Exception {
        TurnContext turnContext = new TurnContext();
        turnContext.initContext();
    }

    /**
     * this test will check if player action will be properly valid
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        TurnContext turnContext = new TurnContext();
        turnContext.interactWithPlayer(new Integer(1));
    }

    /**
     * this test will check if all observers will properly deleted
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void endContext() throws Exception {
        TurnContext turnContext = new TurnContext();
        turnContext.endContext();
    }

    /**
     * this test will check if skip turn will effectively set to true
     * @throws Exception
     */
    @Test
    public void skipTurn() throws Exception {
        TurnContext turnContext = new TurnContext();
        turnContext.skipTurn();
    }

}