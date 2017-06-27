package it.polimi.ingsw.LM34.Exceptions.Controller;


public class NoSuchContextException extends Exception {

    public NoSuchContextException() {
        super("error in context type");
    }
}
