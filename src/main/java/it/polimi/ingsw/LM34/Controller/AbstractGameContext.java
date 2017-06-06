package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by GiulioComi on 17/05/2017.
 */
public abstract class AbstractGameContext extends Observable  {
    /*Reference Game manager*/
    protected GameManager gameManager;
    /*All observers subscribed to the context*/
    protected ArrayList<AbstractGameContext> contextsToSubscribeTo;
    protected ContextType contextType;

    /**
     * @param player that is playing his turn and changes context as he pleases
     */
    public abstract void interactWithPlayer();

    /**
     This list is asked by contexts in order to now to which context subscribe the effects once they are activated
     */
    public ArrayList<AbstractGameContext> getContextsToSubscribeTo() {
        return this.contextsToSubscribeTo;
    }


    public final ContextType getType() {
        return this.contextType;
    }


    /**
     *Kind of bridge beetwen contexts and effects, still valid widely
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
