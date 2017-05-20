package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * Created by GiulioComi on 20/05/2017.
 */
public class NoSuchContextException extends Exception {


    public NoSuchContextException() {
        super("error in context type");
    }
}
