package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Exceptions.Controller.*;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractGameContextTest {

    /**
     * this test will check if any exception will throw from AbstractGameContext.interactWithPlayer
     * @throws Exception
     */
    @Test
    public void interactWithPlayer() throws Exception {
        InitializeAbstractGameContext abstractGameContext = new InitializeAbstractGameContext();
        abstractGameContext.interactWithPlayer(new Object());
    }

    class InitializeAbstractGameContext extends AbstractGameContext {

        @Override
        public Object interactWithPlayer(Object... args) throws IncorrectInputException, MarketBanException, OccupiedSlotException, NotEnoughResourcesException, NotEnoughMilitaryPoints, InvalidLeaderCardAction, CardTypeNumLimitReachedException {
            return null;
        }
    }

}