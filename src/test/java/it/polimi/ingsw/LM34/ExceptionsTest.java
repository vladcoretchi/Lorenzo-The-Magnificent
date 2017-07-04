package it.polimi.ingsw.LM34;

import it.polimi.ingsw.LM34.Exceptions.CLI.PlayerException;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ExceptionsTest {

    @Test
    public void testExceptions() {
        assertTrue(new PlayerException("test playerException").toString().contains("test playerException"));
        assertTrue(new PlayerException("test cardTypeNumLimitReachedException").toString().contains("test cardTypeNumLimitReachedException"));
        assertTrue(new PlayerException("test invalidLeaderCardActionException").toString().contains("test invalidLeaderCardActionException"));
        assertTrue(new PlayerException("test marketBanException").toString().contains("test marketBanException"));
        assertTrue(new PlayerException("test noResourcesException").toString().contains("test noResourcesException"));
        assertTrue(new PlayerException("test noSuchContextException").toString().contains("test noSuchContextException"));
        assertTrue(new PlayerException("test notEnoughMilitaryPointsException").toString().contains("test notEnoughMilitaryPointsException"));
        assertTrue(new PlayerException("test notEnoughResourcesException").toString().contains("test notEnoughResourcesException"));
        assertTrue(new PlayerException("test notEnoughValueException").toString().contains("test notEnoughValueException"));
        assertTrue(new PlayerException("test actionSlotInconsistencyException").toString().contains("test actionSlotInconsistencyException"));
        assertTrue(new PlayerException("test invalidCardTypeException").toString().contains("test invalidCardTypeException"));
        assertTrue(new PlayerException("test invalidResourceTypeException").toString().contains("test invalidResourceTypeException"));
        assertTrue(new PlayerException("test noSuchAvailableSlotException").toString().contains("test noSuchAvailableSlotException"));
        assertTrue(new PlayerException("test occupiedSlotException").toString().contains("test occupiedSlotException"));
        assertTrue(new PlayerException("test incorrectInputException").toString().contains("test incorrectInputException"));
    }

}
