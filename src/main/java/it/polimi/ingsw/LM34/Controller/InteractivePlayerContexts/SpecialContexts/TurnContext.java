package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.*;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Utils.Configurator;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.LEADER_CARDS_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * This is the main context, from which to control the flow of the interaction with the player
 */
public class TurnContext extends AbstractGameContext {
    private Boolean skipTurn;

    /**
     * Constructor called only at the game setup
     */
    public TurnContext() {
        this.contextType = ContextType.TURN_CONTEXT;
    }

    /**
     * Reactivate all observers that are associated to the player that is going to play
     * NOTE: OncePerRound observers are excluded in this reactivation
     */
    public void initContext() {

        //this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player)
        //            .informInGamePlayers(GameInformationType.INFO_NEW_PLAYER_TURN, getCurrentPlayer().getPlayerName(), getCurrentPlayer().getPawnColor()));

        this.gameManager.updateClientPlayers();

        this.getCurrentPlayer().getExcommunicationCards().forEach(excommunicationCard -> {
            excommunicationCard.getPenalty().applyEffect(this);
        });

        this.skipTurn = false;
        setChanged();
        notifyObservers(this);

        if(!this.skipTurn) {
            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();

            this.getCurrentPlayer().getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.BLUE)
                    .ifPresent(cards -> cards.forEach(card -> card.getPermanentBonus().applyEffect(this)));

            this.getCurrentPlayer().getActivatedLeaderCards().forEach(card -> {
                if(!card.isOncePerRound() || !card.isUsed()) {
                    card.getBonus().applyEffect(this);
                    if (!card.isUsed())
                        card.setUsed(true);

                    this.getCurrentPlayer().getExcommunicationCards().forEach(excommunicationCard -> {
                        excommunicationCard.getPenalty().applyEffect(this);
                    });
                }
            });

            try {
                ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).interactWithPlayer();
            } catch(IncorrectInputException e) {
                LOGGER.log(Level.INFO, e.getMessage(), e);
            }

            interactWithPlayer();
        }

        endContext();
    }

    @Override
    public Void interactWithPlayer(Object... args) {
        //interrupting the socket's blocking read on input stream results in a corrupted stream with the impossibility t communicate with the client
        /*FutureTask<Void> future = new FutureTask<>(() -> {
            try {
                Boolean stillMainAction;
                do {
                    stillMainAction = playerAction(Optional.empty());
                    this.gameManager.updateClientPlayers();
                } while (stillMainAction != null && stillMainAction);
                if(stillMainAction != null)
                    playerSecondaryAction(Optional.empty());
            } catch(Exception ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            }
            return null;
        });

        try {
            ExecutorService service = Executors.newCachedThreadPool();
            service.execute(future);
            try {
                future.get(this.gameManager.getConfigurator().PLAYER_MOVE_TIMEOUT, TimeUnit.SECONDS);
            } catch (Exception ex) {
                LOGGER.log(Level.INFO, ex.getMessage(), ex);
            }

            FutureTask<Void> interruptTask = new FutureTask<>(() -> {Thread.currentThread().interrupt(); return null;});
            service.execute(interruptTask);
            interruptTask.get();
            service.shutdown();
            //service.submit(future);
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            return null;
        }*/

        Boolean stillMainAction;
        do {
            stillMainAction = playerAction(Optional.empty());
            this.gameManager.updateClientPlayers();
        } while (stillMainAction != null && stillMainAction);
        if(stillMainAction != null)
            playerSecondaryAction(Optional.empty());
        return null;
    }

    private Boolean playerAction(Optional<Exception> error) {
        if(this.getCurrentPlayer().isConnected())
            try {
                PlayerAction action = this.gameManager.getPlayerNetworkController(this.gameManager.getCurrentPlayer()).turnMainAction(error);
                Validator.checkPlayerActionValidity(action);

                if(action.getContext() == null)
                    return null;

                AbstractGameContext actionContext = getContextByType(action.getContext());
                actionContext.interactWithPlayer(action.getAction());
                if(action.getContext().name().equals(LEADER_CARDS_CONTEXT.name()))
                    return true;
                else
                    return false;
            } catch (IncorrectInputException |
                    OccupiedSlotException |
                    MarketBanException |
                    NotEnoughResourcesException |
                    NotEnoughMilitaryPoints |
                    CardTypeNumLimitReachedException |
                    InvalidLeaderCardAction |
                    NoMoreLeaderCardsAvailable ex) {
                LOGGER.log(Level.FINER, ex.getMessage(), ex);
                return playerAction(Optional.of(ex));
            } catch (NetworkConnectionException ex) {
                LOGGER.log(Level.INFO, ex.getMessage(), ex);
                this.getCurrentPlayer().setDisconnected();
                return null;
            }
        return null;
    }

    private void playerSecondaryAction(Optional<Exception> error) {
        if(this.getCurrentPlayer().isConnected())
            try {
                PlayerAction action = this.gameManager.getPlayerNetworkController(this.gameManager.getCurrentPlayer()).turnSecondaryAction(error);
                Validator.checkPlayerActionValidity(action);

                if(action.getContext() == null)
                    return;

                LeaderCardsContext actionContext = (LeaderCardsContext) getContextByType(action.getContext());
                actionContext.interactWithPlayer(action.getAction());

                playerSecondaryAction(Optional.empty());
            } catch (IncorrectInputException |
                    NotEnoughResourcesException |
                    InvalidLeaderCardAction |
                    NoMoreLeaderCardsAvailable ex) {
                LOGGER.log(Level.FINER, ex.getMessage(), ex);
                playerSecondaryAction(Optional.of(ex));
            } catch (NetworkConnectionException ex) {
                LOGGER.log(Level.INFO, ex.getMessage(), ex);
                this.getCurrentPlayer().setDisconnected();
            }
    }

    /**
     * Deactivates all bonus observers of the player that finished the turn
     */
    public void endContext() {
        for (ContextType ct : ContextType.values())
            if(this.getContextByType(ct) != null)
                this.getContextByType(ct).deleteObservers();

        if(this.getCurrentPlayer().isConnected())
            try {
                this.gameManager.getActivePlayerNetworkController().endTurn();
            } catch(NetworkConnectionException ex) {
                LOGGER.log(Level.FINER, ex.getMessage(), ex);
                this.getCurrentPlayer().setDisconnected();
            }

        this.gameManager.nextTurn();
    }

    public void skipTurn() {
        this.skipTurn = true;
    }
}
