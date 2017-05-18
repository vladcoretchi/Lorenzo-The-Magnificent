package it.polimi.ingsw.LM34.Exceptions.CLI;

public class PlayerException extends Exception {

    public PlayerException(String playerException) {

        super(playerException);
    }

    public String getErrorMessage() {
        return super.getMessage();
    }
}
