package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * This exception is invoked whe the user has more cards than the limit specified in
 * the Rules by CranioCreations
 */
public class CardTypeNumLimitReachedException extends Exception {
    private static final long serialVersionUID = 1202100723431902599L;

    public CardTypeNumLimitReachedException() {
        super("You have reached the limit of cards specified by the Rules of the Game");
    }
}
