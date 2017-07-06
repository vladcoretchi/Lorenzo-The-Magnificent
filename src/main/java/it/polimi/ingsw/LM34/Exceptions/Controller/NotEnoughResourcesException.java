package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * Exception thrown in {@link TowersContext}
 * when the player has not enough resources for buying the selected card
 */
public class NotEnoughResourcesException extends Exception {
    private static final long serialVersionUID = -2659068756481284550L;
    /** Serial Version */

    /** Invoke superclass constructor
     *  with the costum message as parameter
     */
    public NotEnoughResourcesException() {
        super("You do not have enough resources");
    }
}
