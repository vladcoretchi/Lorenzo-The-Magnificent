package it.polimi.ingsw.LM34.Exceptions.Model;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class InvalidCardType extends Throwable {
    public InvalidCardType(String s) {
        super("invalid card type");
    }
}
