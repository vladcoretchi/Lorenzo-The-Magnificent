package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Exceptions.Controller.MarketBanException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.List;
import java.util.Observable;

public abstract class AbstractGameContext extends Observable  {
    protected ContextType contextType;

    /* Reference Game manager */
    protected GameManager gameManager;

    /**
     * method used to send requests to the client, receive the selected actions and do stuff
     */
    public abstract Object interactWithPlayer(Object... args) throws
            IncorrectInputException,
            MarketBanException,
            OccupiedSlotException,
            NotEnoughResourcesException;

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

    public  AbstractGameContext getContextByType(PlayerSelectableContexts contextType) {
        return this.gameManager.getContextByType(contextType);
    }

    public Player getCurrentPlayer() {
        return this.gameManager.getCurrentPlayer();
    }

    public final void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}
