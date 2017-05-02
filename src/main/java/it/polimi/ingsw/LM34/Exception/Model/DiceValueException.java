package it.polimi.ingsw.LM34.Exception.Model;

/**
 * Created by Giulio Comi on 5/2/2017.
 */

public class DiceValueException extends Exception {

    String message;

    public DiceValueException() {
        super("dice value exception");
    }

}
