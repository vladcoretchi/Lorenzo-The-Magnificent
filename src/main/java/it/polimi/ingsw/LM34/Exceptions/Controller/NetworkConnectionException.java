package it.polimi.ingsw.LM34.Exceptions.Controller;

public class NetworkConnectionException extends Exception {
    private static final long serialVersionUID = -3458557577483028844L;

    public NetworkConnectionException() {
        super("Player disconnected");
    }
}
