package it.polimi.ingsw.LM34.Exceptions.Model;

public class ActionSlotInconsistencyException extends Exception {
    public ActionSlotInconsistencyException() {
        super("no slots configured during loading for harvest/production areas");
    }
}
