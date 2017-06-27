package it.polimi.ingsw.LM34.Exceptions.Model;

public class OccupiedSlotException extends Exception {

    public OccupiedSlotException() {
        super("this slot is already occupied by another pawn");
    }
}
