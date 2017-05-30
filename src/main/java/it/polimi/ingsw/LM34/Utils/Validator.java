package it.polimi.ingsw.LM34.Utils;

/**
 * Created by GiulioComi on 30/05/2017.
 */

import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectDataException;

import java.util.List;

/**
 * This static class is unbelievable useful for validate data inserted by player both client and server side
 * Client side is used as a preliminary approach to avoid malicious input
 *Server side is adopted broadly by all contexts that calls the overloaded methods they need
 * Server side is needed to enforce and grant consistency of the games tate
 */
public final class Validator {

    //TODO: test if this is valid for contextypes, list of Integer (for ranges of market slots, ecc)
    /*Validate data type and ranges*/
    public static final void checkValidity(String input,List<?> dataType) throws IncorrectDataException {
        try {
            /*Try to extract an Integer from input*/
            Integer.parseInt(input);
            /*Calculates range*/
            Integer min = 1;
            Integer max = dataType.toArray().length;
        } catch (Exception e) {
            throw new IncorrectDataException();
        }
    }

    /*Validate Number type inputs*/
    public static final void checkValidity(String input) throws IncorrectDataException {
        if(Integer.parseInt(input) <= 0)
            throw new IncorrectDataException();
    }

}
