package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.InvalidLeaderCardAction;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurator;
import it.polimi.ingsw.LM34.Utils.Validator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.LEADER_CARDS_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * In this context the player can discard a leader in favor of a privilege or activate his ability
 * In the latter case, all requirements of the leader to activate are verified
 */
public class LeaderCardsContext extends AbstractGameContext {
    private Integer totalLeadersDiscarded;
    private String leaderToActivate; //TODO

public LeaderCardsContext() {
    this.contextType = LEADER_CARDS_CONTEXT;
}

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException, InvalidLeaderCardAction, NotEnoughResourcesException {
        Pair<Integer, LeaderCardsAction> leaderCardAction;
        try {
            Pair<?, ?> actionArg = (Pair<?, ?>) args[0];
            leaderCardAction = new ImmutablePair<>((Integer) actionArg.getLeft(), (LeaderCardsAction) actionArg.getRight());
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        Validator.checkValidity(leaderCardAction.getLeft(), this.getCurrentPlayer().getActivatedLeaderCards());

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
        this.getCurrentPlayer().discardLeaderCard(selectedCard);

        return null;
    }


    private void playLeaderCard(LeaderCard card) throws NotEnoughResourcesException {
        Optional<Resources> resourcesRequirements = card.getRequirements().getResourcesRequirements();
        Optional<Map<DevelopmentCardColor, Integer>> cardsRequirements = card.getRequirements().getCardRequirements();

        if(resourcesRequirements.isPresent())
            if(this.getCurrentPlayer().hasEnoughResources(resourcesRequirements.get()))
                this.getCurrentPlayer().subResources(resourcesRequirements.get());
            else
                throw new NotEnoughResourcesException();

        if(cardsRequirements.isPresent())
            for(DevelopmentCardColor color : DevelopmentCardColor.values())
                if(cardsRequirements.get().containsKey(color) && !this.getCurrentPlayer().hasEnoughCardsOfType(color, cardsRequirements.get().get(color)))
                    throw new NotEnoughResourcesException();

        card.activate();
        card.getBonus().applyEffect(this);

        setChanged();
        notifyObservers(this);
    }

    private void discardLeaderCard(LeaderCard card) {
        try {
            ((UseCouncilPrivilegeContext) this.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer(Configurator.COUNCIL_PRIVILEGES_FOR_DISCARDED_LEADER_CARD);
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

        /*Integer leaderToCopy = gameManager.getActivePlayerNetworkController().copyLeaderSelection(allLeadersActivatedByOthers);

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
