package it.polimi.ingsw.LM34.Exceptions.Model;

public class InvalidResourceTypeException extends Exception {

    public InvalidResourceTypeException() {
        super("resource type is unkown");
    }
}
