package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * Exception thrown in {@link it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market}
 * or {@link it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea}s if the number of players
 * is not sufficient to access the advanced action slot of those game spaces
 */

public class NotAvailableSpace extends Exception {
    private static final long serialVersionUID = -2659068756481284550L;
    /** Serial Version */

    /** Invoke superclass constructor
     *  with the custom message as parameter
     */
    public NotAvailableSpace() {
        super("This slot is not available for this number of players in the game");
    }
}

