package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * Exception thrown in {@link TowersContext}
 * when the player has already one pawn of the same color in the {@link it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower},
 * unless it is of the NEUTRAL {@link it.polimi.ingsw.LM34.Enums.Model.PawnColor}
 */
public class CannotPlacePawnException extends Exception {
    private static final long serialVersionUID = -2659068756481284550L;
    /** Serial Version */

    /** Invoke superclass constructor
     *  with the custom message as parameter
     */
    public CannotPlacePawnException() {
        super("You cannot place your pawn here, unless it is of type Neutral");
    }
}