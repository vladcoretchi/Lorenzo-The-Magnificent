package it.polimi.ingsw.LM34.Exceptions.Model;

public class InvalidCardType extends Exception {
    public InvalidCardType(String s) {
        super("invalid card type");
    }
}
