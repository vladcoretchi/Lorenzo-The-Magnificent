package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.MarketBanException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Utils.Configurator;
import it.polimi.ingsw.LM34.Utils.Validator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class TurnContext extends AbstractGameContext {
    private Boolean skipTurn;

    /**
     * Constructor called only at the game setup
     */
    public TurnContext() {
        this.contextType = ContextType.TURN_CONTEXT;
    }

    /**
     * @param player about to begin his turn
     * Reactivate all observers that are of the player that is going to play
     * NOTE: OncePerRound observers are excluded
     */
    public void initContext() {
        this.skipTurn = false;
        setChanged();
        notifyObservers(this); //for SkipTurn observer

        if(this.skipTurn) {
        /*List<AbstractEffect> observers = this.getCurrentPlayer().getObservers();
        for (AbstractEffect observer : observers)
            if (!observer.isOncePerRound())
                observer.subscribeObserverToContext(contexts);
            notifyObservers(player); //for PerRoundLeaderReward*/

            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();
            interactWithPlayer();
        }

        endContext();
    }

    @Override
    public Void interactWithPlayer(Object... args) {
        playerAction(Optional.empty());
        return null;
    }

    private void playerAction(Optional<Exception> error) {
        PlayerAction action = this.gameManager.getPlayerNetworkController(this.gameManager.getCurrentPlayer()).turnMainAction(error);
        try {
            Validator.checkPlayerActionValidity(action);

            AbstractGameContext actionContext = getContextByType(action.getContext());
            actionContext.interactWithPlayer();
        } catch (IncorrectInputException | OccupiedSlotException | MarketBanException | NotEnoughResourcesException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            playerAction(Optional.of(ex));
        }
    }

    /**
     * Deactivates all bonus observers of the player that finished the turn
     */
    public void endContext() {
        for (ContextType ct : ContextType.values())
            if(this.getContextByType(ct) != null)
                this.getContextByType(ct).deleteObservers();

        this.gameManager.nextTurn();
    }

    public void skipTurn() {
        this.skipTurn = true;
    }
}
