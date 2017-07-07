package it.polimi.ingsw.LM34.Utils;


import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Network.PlayerAction;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * This static class is unbelievably useful for validate data inserted by player both client and server side
 * Client side is used as a preliminary approach to avoid malicious input
 *Server side is adopted broadly by all contexts that calls the overloaded methods they need
 * Server side is needed to enforce and grant consistency of the game state
 */
public final class Validator {
    /*Proudly designed and implemented by 0 :-)*/

    private Validator() {}

    /**
     * Validate input based on expected data types and ranges
     */
    public static final Integer checkValidity(String input, List<?> data) throws IncorrectInputException {
        try {
            /*Try to extract an Integer from input*/
            Integer inputValue = Integer.parseInt(input);
            checkValidity(inputValue, data);
            return inputValue;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new IncorrectInputException();
        }
    }

    /**
     * Validate input based on expected data types and ranges
     */
    public static final void checkValidity(Integer input, List<?> data) throws IncorrectInputException {
            /*Calculates range*/
            Integer min = 0;
            Integer max = data.size();

            /*Check if input meets the requirements*/
            if(input < min || input >= max)
                throw new IncorrectInputException();
    }

    /**
     * Validate Number type inputs
     */
    public static final void checkValidity(String input) throws IncorrectInputException {
        try {
            Integer.parseInt(input);
        } catch(NumberFormatException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new IncorrectInputException();
        }
    }

    /**
     * Validate Number type inputs
     */
    public static final void checkValidity(Integer input, Integer max) throws IncorrectInputException {
        if(input < 0 || input > max)
            throw new IncorrectInputException();
    }

    /**
     * Validate Number type inputs within range min-max
     */
    public static final void checkValidity(Integer input, Integer min, Integer max) throws IncorrectInputException {
        if(input < min || input > max)
            throw new IncorrectInputException();
    }

    /**
     * Verify server-side that the action performed by the clients are valid
     * @param action to be evaluated
     * @throws IncorrectInputException if input from clients is not trusted
     */
    public static void checkPlayerActionValidity(PlayerAction action) throws IncorrectInputException {
        if (action == null)
            throw new IncorrectInputException();
    }
}
