package it.polimi.ingsw.LM34.Exceptions.Model;

/**
 * Created by Giulio Comi on 5/2/2017.
 */

public class InvalidResourceTypeException extends Exception {

    public InvalidResourceTypeException() {
        super("resource type is unkown");
    }
}
