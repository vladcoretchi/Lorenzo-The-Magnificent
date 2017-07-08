package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.*;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.Optional;
import java.util.logging.Level;

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
        /*this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player)
                        .informInGamePlayers(
                        GameInformationType.INFO_NEW_PLAYER_TURN, this.getCurrentPlayer().getPlayerName(), this.getCurrentPlayer().getPawnColor()));
*/
        this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player).setExcommunicationCards(this.gameManager.getExcommunicationCards()));
        this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player).updatePlayersData(this.gameManager.getPlayers()));
        this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player).updateTowers(this.gameManager.getTowers()));
        this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player).updateProductionArea(this.gameManager.getProductionArea()));
        this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player).updateHarvestArea(this.gameManager.getHarvestArea()));
        this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player).updateMarket(this.gameManager.getMarket()));
        this.gameManager.getPlayers().forEach(player -> this.gameManager.getPlayerNetworkController(player).updateCouncilPalace(this.gameManager.getPalace()));

        this.skipTurn = false;
        setChanged();
        notifyObservers(this); //for SkipTurn observer


        if(!this.skipTurn) {

            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();

            this.getCurrentPlayer().getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.BLUE)
                    .ifPresent(cards -> cards.forEach(card -> card.getPermanentBonus().applyEffect(this)));

            this.getCurrentPlayer().getActivatedLeaderCards().forEach(card -> {
                if(card.isOncePerRound() && card != null && !card.isUsed() || !card.isOncePerRound()) {
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
        playerAction(Optional.empty());
        return null;
    }

    private void playerAction(Optional<Exception> error) {
        PlayerAction action = this.gameManager.getPlayerNetworkController(this.gameManager.getCurrentPlayer()).turnMainAction(error);

        try {
            Validator.checkPlayerActionValidity(action);

            if(action.getContext() == null)
                return;

            AbstractGameContext actionContext = getContextByType(action.getContext());
            actionContext.interactWithPlayer(action.getAction());
        } catch (IncorrectInputException |
                OccupiedSlotException |
                MarketBanException |
                NotEnoughResourcesException |
                NotEnoughMilitaryPoints |
                CardTypeNumLimitReachedException |
                InvalidLeaderCardAction |
                NoMoreLeaderCardsAvailable ex) {
            LOGGER.log(Level.FINER, ex.getMessage(), ex);
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

        this.gameManager.getActivePlayerNetworkController().endTurn();
        this.gameManager.nextTurn();
    }

    public void skipTurn() {
        this.skipTurn = true;
    }
}
