package it.polimi.ingsw.LM34.Exceptions.Controller;

public class NoMoreLeaderCardsAvailable extends Exception {
    private static final long serialVersionUID = 7749039448632026717L;

    public NoMoreLeaderCardsAvailable() {
        super("You played or discarded all of your leader cards");
    }
}
