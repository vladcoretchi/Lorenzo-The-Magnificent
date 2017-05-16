package it.polimi.ingsw.LM34.Exceptions.Model;

/**
 * Created by Julius on 03/05/2017.
 */
public class ActionSlotInconsistencyException extends Exception {
    public ActionSlotInconsistencyException() {
        super("no slots configured during loading for harvest/production areas");
    }
}
