package Exceptions;

public class PlayerException extends Exception {

    public PlayerException(String playerException) {

        super(playerException);
    }

    public String getErrorMessage() {
        return super.getMessage();
    }
}
