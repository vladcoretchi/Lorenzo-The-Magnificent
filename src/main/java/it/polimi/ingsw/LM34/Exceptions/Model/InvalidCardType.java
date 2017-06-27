package it.polimi.ingsw.LM34.Exceptions.Model;

public class InvalidCardType extends Throwable {
    public InvalidCardType(String s) {
        super("invalid card type");
    }
}
