package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.InvalidLeaderCardAction;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoMoreLeaderCardsAvailable;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.LEADER_CARDS_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * In this context the player can discard a leader in favor of a privilege or activate his ability
 * In the latter case, all requirements of the leader to activate are verified
 */
public class LeaderCardsContext extends AbstractGameContext {
    List<LeaderCard> leaderCards;

    public LeaderCardsContext() {
    this.contextType = LEADER_CARDS_CONTEXT;
}

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException, InvalidLeaderCardAction, NotEnoughResourcesException, NoMoreLeaderCardsAvailable {
        leaderCards = this.getCurrentPlayer().getPendingLeaderCards();

        if(leaderCards.isEmpty())
            throw new NoMoreLeaderCardsAvailable();

        Pair<Integer, LeaderCardsAction> leaderCardAction = leaderCardsAction();
        LeaderCard selectedCard = this.getCurrentPlayer().getPendingLeaderCards().get(leaderCardAction.getLeft());

        switch (leaderCardAction.getRight()) {
            case PLAY:
                playLeaderCard(selectedCard);
                break;
            case DISCARD:
                discardLeaderCard(selectedCard);
                break;
            default:
                throw new InvalidLeaderCardAction();
        }

        return null;
    }

    private Pair<Integer, LeaderCardsAction> leaderCardsAction() {
        Pair<String, LeaderCardsAction> action = this.gameManager.getActivePlayerNetworkController().leaderCardSelection(leaderCards);

        for(int i = 0; i < leaderCards.size(); i++)
            if(leaderCards.get(i).getName().equals(action.getLeft()))
                return new ImmutablePair<>(i, action.getRight());

        return leaderCardsAction();
    }


    private void playLeaderCard(LeaderCard card) throws NotEnoughResourcesException {
        Optional<Resources> resourcesRequirements = card.getRequirements().getResourcesRequirements();
        Optional<Map<DevelopmentCardColor, Integer>> cardsRequirements = card.getRequirements().getCardRequirements();

        if(resourcesRequirements.isPresent())
            if(!this.getCurrentPlayer().hasEnoughResources(resourcesRequirements.get()))
                throw new NotEnoughResourcesException();

        if(cardsRequirements.isPresent())
            for(DevelopmentCardColor color : DevelopmentCardColor.values())
                if(cardsRequirements.get().containsKey(color) && !this.getCurrentPlayer().hasEnoughCardsOfType(color, cardsRequirements.get().get(color)))
                    throw new NotEnoughResourcesException();

        card.activate();
        ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();
        card.getBonus().applyEffect(this);
        if(card.isOncePerRound())
            card.setUsed(true);
        try {
            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).interactWithPlayer();
        } catch(IncorrectInputException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }

        /*trigger Copy Leader ability*/
        setChanged();
        notifyObservers(this);
    }

    private void discardLeaderCard(LeaderCard card) {
        try {
            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();
            ((UseCouncilPrivilegeContext) this.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT))
                                              .interactWithPlayer(Configurator.COUNCIL_PRIVILEGES_FOR_DISCARDED_LEADER_CARD);
            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).interactWithPlayer();

            this.getCurrentPlayer().discardLeaderCard(card);
        } catch(IncorrectInputException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
    /**
     * An interactive method that unlocks to the player the ability to clone another leader
     */
    /*Called by CopyOtherLeader observer*/
    public void copyOtherLeaderAbility() {
        List<LeaderCard> activatedLeaderCards = new ArrayList<>();

        for(Player player : this.gameManager.getPlayers())
            if(player != this.getCurrentPlayer())
                activatedLeaderCards.addAll(player.getActivatedLeaderCards());

        /*TODO: Integer leaderToCopy = gameManager.getActivePlayerNetworkController().leaderCardCopy(allLeadersActivatedByOthers);
        try {
            Validator.checkValidity(leaderToCopy, activatedLeaderCards);
            LeaderCard selectedLeader = activatedLeaderCards.get(leaderToCopy);
            this.getCurrentPlayer().addLeaderCard(selectedLeader);
            selectedLeader.activate();
            selectedLeader.getBonus().applyEffect(this);
        } catch(IncorrectInputException ide){
            copyOtherLeaderAbility();
        }*/
    }
}
