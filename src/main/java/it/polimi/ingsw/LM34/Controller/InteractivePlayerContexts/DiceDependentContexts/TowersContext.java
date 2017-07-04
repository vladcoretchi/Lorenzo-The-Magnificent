package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.CardTypeNumLimitReachedException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughMilitaryPoints;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.FAMILY_MEMBER_SELECTION_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.*;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class TowersContext extends AbstractGameContext {
    private Boolean slotsRewardPenalty;
    private Boolean noOccupiedTowerTax;
    private Boolean ignoreMilitaryPointsRequirements;
    private DevelopmentCardColor towerColor;
    private Integer slotDiceValue;

    public TowersContext() {
        this.contextType = ContextType.TOWERS_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args)
            throws IncorrectInputException, OccupiedSlotException, NotEnoughResourcesException, NotEnoughMilitaryPoints, CardTypeNumLimitReachedException {
        Pair<DevelopmentCardColor, Integer> slotSelection;
        try {
            Pair<?, ?> slotArg = (Pair<?, ?>) args[0];
            slotSelection = new ImmutablePair<>((DevelopmentCardColor) slotArg.getLeft(), (Integer) slotArg.getRight());
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        Tower selectedTower = null;
        for (Tower t : this.gameManager.getTowers()) {
            if(t.getCardColor() == slotSelection.getLeft())
                selectedTower = t;
        }
        if(selectedTower == null)
            throw new IncorrectInputException();

        TowerSlot slot = selectedTower.getTowerSlots().get(slotSelection.getRight());

        if(!slot.isEmpty())
            throw new OccupiedSlotException();

        AbstractDevelopmentCard card = slot.getCardStored();
        if(this.getCurrentPlayer().hasEnoughCardsOfType(card.getColor(), Configurator.MAX_ACQUIRABLE_CARDS_PER_TYPE))
            throw new CardTypeNumLimitReachedException();

        this.towerColor = selectedTower.getCardColor();
        this.slotDiceValue = slot.getDiceValue();
        this.slotsRewardPenalty = false;
        this.noOccupiedTowerTax = false;
        this.ignoreMilitaryPointsRequirements = false;

        setChanged();
        notifyObservers(this);

        Optional<List<AbstractDevelopmentCard>> currentPlayerTerritoryCards = this.getCurrentPlayer().getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.GREEN);
        if(this.towerColor == DevelopmentCardColor.GREEN && !this.ignoreMilitaryPointsRequirements && currentPlayerTerritoryCards.isPresent() &&
                Configurator.getMilitaryPointsForTerritories().get(currentPlayerTerritoryCards.get().size()) > this.getCurrentPlayer().getResources().getResourceByType(MILITARY_POINTS))
            throw new NotEnoughMilitaryPoints();

        Resources requirements = new Resources(card.getResourcesRequired().getResourceByType(COINS),
                card.getResourcesRequired().getResourceByType(WOODS),
                card.getResourcesRequired().getResourceByType(STONES),
                card.getResourcesRequired().getResourceByType(SERVANTS),
                card.getResourcesRequired().getResourceByType(MILITARY_POINTS),
                card.getResourcesRequired().getResourceByType(FAITH_POINTS),
                card.getResourcesRequired().getResourceByType(VICTORY_POINTS));

        if(!this.noOccupiedTowerTax)
            requirements.sumResources(Configurator.TOWER_OCCUPIED_COST);

        if(!this.gameManager.getCurrentPlayer().hasEnoughResources(requirements))
            throw new NotEnoughResourcesException();

        FamilyMember selectedFamilyMember = ((FamilyMemberSelectionContext) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT)).interactWithPlayer(slot.getDiceValue(), false, this.contextType);

        try {
            slot.insertFamilyMember(selectedFamilyMember);
            selectedFamilyMember.placePawn();

            this.gameManager.getCurrentPlayer().getPersonalBoard().addCard(card);
            slot.setCardStored(null);

            //TODO: add military points choice for venture cards
            this.gameManager.getCurrentPlayer().subResources(requirements);

            card.getInstantBonus().forEach(effect -> effect.applyEffect(this));
            card.getPermanentBonus().applyEffect(this);

            if(!this.slotsRewardPenalty)
                slot.getResourcesReward().applyEffect(this);
        } catch(InvalidCardType | OccupiedSlotException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }

        return null;
    }

    public DevelopmentCardColor getTowerColor() {
        return this.towerColor;
    }

    public Integer getSlotDiceValue() {
        return this.slotDiceValue;
    }

    public void setSlotsRewardPenalty() {
        this.slotsRewardPenalty = true;
    }

    public void avoidOccupiedTowerTax() {
        this.noOccupiedTowerTax = true;
    }

    public void ignoreMilitaryPointsRequirementsForTerritoryCards() {
        if(this.towerColor == DevelopmentCardColor.GREEN)
            this.ignoreMilitaryPointsRequirements = true;
    }
}