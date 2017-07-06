package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * This exception has the aim to be thrown if the user selects an action that is not registered
 * in the enum {@link it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction}
 */
public class InvalidLeaderCardAction extends Exception {
    private static final long serialVersionUID = -2669414246027138329L;

    public InvalidLeaderCardAction() {
        super("Your selected action is not valid");
    }
}
