package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * This exception is launched if the {@link it.polimi.ingsw.LM34.Controller.ContextFactory}
 * if there is an error in forging the {@link it.polimi.ingsw.LM34.Controller.AbstractGameContext}
 * of the game based on the {@link ContextType}
 */
public class NoSuchContextException extends Exception {
    private static final long serialVersionUID = -687927726119826480L;

    public NoSuchContextException() {
        super("Error in context type");
    }
}
