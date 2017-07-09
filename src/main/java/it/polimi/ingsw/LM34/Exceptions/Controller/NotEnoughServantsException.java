package it.polimi.ingsw.LM34.Exceptions.Controller;


/**
 * Exception thrown when the player does not have enough servants to increase the
 * {@link it.polimi.ingsw.LM34.Model.FamilyMember} value,
 */
public class NotEnoughServantsException extends Exception {
    private static final long serialVersionUID = -290120521066571862L;

    /** Invoke superclass constructor
     *  with the custom message as parameter
     */
    public NotEnoughServantsException() {
        super("You do not have enough servants to perform this action");
    }
}
