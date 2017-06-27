package it.polimi.ingsw.LM34.Exceptions.Model;

public class NoSuchAvailableSlotException extends Exception {
    public NoSuchAvailableSlotException() {
        super("There is no slot with that reward available for placing the pawn");
    }
}
