package it.polimi.ingsw.LM34.Exceptions.Model;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
public class OccupiedSlotException extends Exception {

    public OccupiedSlotException() {
        super("this slot is already occupied by another pawn");
    }
}
