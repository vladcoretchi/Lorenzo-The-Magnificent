package it.polimi.ingsw.LM34.Exception.Model;


/**
 * Created by Julius on 03/05/2017.
 */
public class NoSuchAvailableSlotException extends Exception {
    public NoSuchAvailableSlotException() {
        super("There is no slot with that reward available for placing the pawn");
    }
}
