package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Model.Player;
import java.util.List;
import java.util.Observable;

/**
 * Created by GiulioComi on 17/05/2017.
 */
public abstract class AbstractGameContext extends Observable  {
    protected ContextType contextType;

    /* Reference Game manager */
    protected GameManager gameManager;

    /* All observers subscribed to the context */
    protected List<AbstractGameContext> contextsToSubscribeTo;

    /**
     * method used to send requests to the client, receive the selected actions and do stuff
     */
    public abstract void interactWithPlayer();

    /**
     * This list is asked by contexts in order to know to which context subscribe the effects once they are activated
     */
    public List<AbstractGameContext> getContextsToSubscribeTo() {
        return this.contextsToSubscribeTo;
    }


    /**
     * @return Context's type
     */
    public final ContextType getType() {
        return this.contextType;
    }


    /**
     * Kind of bridge between contexts and effects, still valid widely
     * @param contextType
     * @return the reference to the context for which effects are interested in
     */
    public  AbstractGameContext getContextByType(ContextType contextType) {
        return gameManager.getContextByType(contextType);
    }

    public  AbstractGameContext getContextByType(PlayerSelectableContexts contextType) {
        return gameManager.getContextByType(contextType);
    }

    public Player getCurrentPlayer() {
        return gameManager.getCurrentPlayer();
    }

    public final void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}
