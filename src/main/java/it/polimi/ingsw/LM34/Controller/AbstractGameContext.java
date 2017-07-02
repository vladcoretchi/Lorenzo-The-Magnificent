package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Exceptions.Controller.*;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;

public abstract class AbstractGameContext extends Observable  {
    /**
     * The {@link ContextType} of the context that extend {@link AbstractGameContext}
     */
    protected ContextType contextType;

    /**
     * Reference to the {@link GameManager}
     */
    protected GameManager gameManager;

    /**
     * method used to send requests to the client, receive the selected actions and do stuff
     */
    public abstract Object interactWithPlayer(Object... args) throws
            IncorrectInputException,
            MarketBanException,
            OccupiedSlotException,
            NotEnoughResourcesException,
            NotEnoughMilitaryPoints,
            InvalidLeaderCardAction,
            CardTypeNumLimitReachedException;

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
        return this.gameManager.getContextByType(contextType);
    }

    /**
     *
     * @param contextType {@link PlayerSelectableContexts} from which to retrieve the {@link ContextType}
     * @return the {@link ContextType} that correspond to the {@link PlayerSelectableContexts}
     */
    public  AbstractGameContext getContextByType(PlayerSelectableContexts contextType) {
        return this.gameManager.getContextByType(contextType);
    }

    /**
     * @return the player that is playing right now
     */
    public Player getCurrentPlayer() {
        return this.gameManager.getCurrentPlayer();
    }

    /**
     * A connection between the game manager and its contexts
     * @param gameManager of the game
     */
    public final void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}
