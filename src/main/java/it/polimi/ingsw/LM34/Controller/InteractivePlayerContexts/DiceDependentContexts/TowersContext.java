package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.IncreasePawnsValueByServantsContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.CardTypeNumLimitReachedException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughMilitaryPoints;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.VentureCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurator;
import it.polimi.ingsw.LM34.Utils.Validator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.*;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class TowersContext extends AbstractGameContext {
    private Boolean slotsRewardPenalty;
    private Boolean noOccupiedTowerTax;
    private Boolean ignoreMilitaryPointsRequirements;
    private DevelopmentCardColor towerColor;
    private Integer slotDiceValue;

    //free action variables
    private Boolean freeAction;
    private Resources requirementsDiscount;

    public TowersContext() {
        this.contextType = ContextType.TOWERS_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args)
            throws IncorrectInputException, OccupiedSlotException, NotEnoughResourcesException, NotEnoughMilitaryPoints, CardTypeNumLimitReachedException {
        Pair<DevelopmentCardColor, Integer> slotSelection;
        DevelopmentCardColor freeActionCard = null;
        Integer freeActionValue = 0;
        try {
            Pair<?, ?> slotArg = (Pair<?, ?>) args[0];
            if(args.length == 3) {
                freeActionCard = (DevelopmentCardColor) args[1];
                freeActionValue = (Integer) args[2];
            }
            slotSelection = new ImmutablePair<>((DevelopmentCardColor) slotArg.getLeft(), (Integer) slotArg.getRight());
            Validator.checkValidity(slotSelection.getRight(), 0, Configurator.MAX_TOWER_LEVELS-1);
        } catch (Exception ex) {
            LOGGER.log(Level.FINEST, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        Tower selectedTower = null;
        for (Tower t : this.gameManager.getTowers()) {
            if(t.getCardColor() == slotSelection.getLeft())
                selectedTower = t;
        }
        if(selectedTower == null)
            throw new IncorrectInputException();

        if(freeActionCard != null && freeActionCard != DevelopmentCardColor.MULTICOLOR && !freeActionCard.name().equals(selectedTower.getCardColor().name()))
            throw new IncorrectInputException();

        TowerSlot slot = selectedTower.getTowerSlots().get(slotSelection.getRight());

        if(!slot.isEmpty())
            throw new OccupiedSlotException();

        AbstractDevelopmentCard card = slot.getCardStored();
        if(card != null && this.getCurrentPlayer().hasEnoughCardsOfType(card.getColor(), Configurator.MAX_ACQUIRABLE_CARDS_PER_TYPE))
            throw new CardTypeNumLimitReachedException();

        this.towerColor = selectedTower.getCardColor();
        this.slotDiceValue = slot.getDiceValue();
        this.slotsRewardPenalty = false;
        this.noOccupiedTowerTax = false;
        this.ignoreMilitaryPointsRequirements = false;
        this.freeAction = false;
        this.requirementsDiscount = null;

        setChanged();
        notifyObservers(this);

        if(this.freeAction && freeActionValue > 0 && slot.getDiceValue() > freeActionValue) {
            Integer selectedServants = ((IncreasePawnsValueByServantsContext) getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT)).
                    interactWithPlayer(slot.getDiceValue() - freeActionValue);

            this.getCurrentPlayer().getResources().subResources(new Resources(0,0,0,selectedServants));
        }

        Optional<List<AbstractDevelopmentCard>> currentPlayerTerritoryCards = this.getCurrentPlayer().getPersonalBoard()
                                                                            .getDevelopmentCardsByType(DevelopmentCardColor.GREEN);

        //Check if player has not passed the limit of territory cards he can have based on his MILITARY_POINTS
        if(this.towerColor == DevelopmentCardColor.GREEN && !this.ignoreMilitaryPointsRequirements && currentPlayerTerritoryCards.isPresent() &&
                this.gameManager.getConfigurator().getMilitaryPointsForTerritories().get(currentPlayerTerritoryCards.get().size()) > this.getCurrentPlayer().getResources().getResourceByType(MILITARY_POINTS))
            throw new NotEnoughMilitaryPoints();

        Resources ventureCardAlternative = null;
        if(card != null && card.getColor() == DevelopmentCardColor.PURPLE && ((VentureCard) card).isThereAlternativeToMilitaryPointsPayment())
            ventureCardAlternative = new Resources(((VentureCard) card).getMilitaryPointsRequired(), 0, 0);

        Resources requirements = null;
        if(card != null && !(card.getColor() == DevelopmentCardColor.PURPLE && card.getResourcesRequired().getResources().isEmpty()))
            requirements = new Resources(
                    card.getResourcesRequired().getResourceByType(COINS),
                    card.getResourcesRequired().getResourceByType(WOODS),
                    card.getResourcesRequired().getResourceByType(STONES),
                    card.getResourcesRequired().getResourceByType(SERVANTS),
                    card.getResourcesRequired().getResourceByType(MILITARY_POINTS),
                    card.getResourcesRequired().getResourceByType(FAITH_POINTS),
                    card.getResourcesRequired().getResourceByType(VICTORY_POINTS));

        if(this.requirementsDiscount != null) {
            if(ventureCardAlternative != null)
                ventureCardAlternative.subResources(new Resources(
                        Math.min(ventureCardAlternative.getResourceByType(COINS), requirementsDiscount.getResourceByType(COINS)),
                        Math.min(ventureCardAlternative.getResourceByType(WOODS), requirementsDiscount.getResourceByType(WOODS)),
                        Math.min(ventureCardAlternative.getResourceByType(STONES), requirementsDiscount.getResourceByType(STONES)),
                        Math.min(ventureCardAlternative.getResourceByType(SERVANTS), requirementsDiscount.getResourceByType(SERVANTS)),
                        Math.min(ventureCardAlternative.getResourceByType(MILITARY_POINTS), requirementsDiscount.getResourceByType(MILITARY_POINTS)),
                        Math.min(ventureCardAlternative.getResourceByType(FAITH_POINTS), requirementsDiscount.getResourceByType(FAITH_POINTS)),
                        Math.min(ventureCardAlternative.getResourceByType(VICTORY_POINTS), requirementsDiscount.getResourceByType(VICTORY_POINTS))
                ));

            if(requirements != null)
                requirements.subResources(new Resources(
                        Math.min(requirements.getResourceByType(COINS), requirementsDiscount.getResourceByType(COINS)),
                        Math.min(requirements.getResourceByType(WOODS), requirementsDiscount.getResourceByType(WOODS)),
                        Math.min(requirements.getResourceByType(STONES), requirementsDiscount.getResourceByType(STONES)),
                        Math.min(requirements.getResourceByType(SERVANTS), requirementsDiscount.getResourceByType(SERVANTS)),
                        Math.min(requirements.getResourceByType(MILITARY_POINTS), requirementsDiscount.getResourceByType(MILITARY_POINTS)),
                        Math.min(requirements.getResourceByType(FAITH_POINTS), requirementsDiscount.getResourceByType(FAITH_POINTS)),
                        Math.min(requirements.getResourceByType(VICTORY_POINTS), requirementsDiscount.getResourceByType(VICTORY_POINTS))
                ));
        }

        if(!this.noOccupiedTowerTax && !selectedTower.isTowerEmpty()) {
            if(ventureCardAlternative != null)
                ventureCardAlternative.sumResources(Configurator.TOWER_OCCUPIED_COST);
            if(requirements != null)
                requirements.sumResources(Configurator.TOWER_OCCUPIED_COST);
        }

        if((requirements != null && !this.gameManager.getCurrentPlayer().hasEnoughResources(requirements)) ||
                (ventureCardAlternative != null && !this.gameManager.getCurrentPlayer().hasEnoughResources(ventureCardAlternative)))
            throw new NotEnoughResourcesException();

        try {
            if(!freeAction) {
                FamilyMember selectedFamilyMember = ((FamilyMemberSelectionContext) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT))
                        .interactWithPlayer(slot.getDiceValue(), false, this.contextType);

                if(!this.freeAction && selectedTower.getTowerSlots().stream().anyMatch(ts ->
                        ts.getFamilyMembers().stream().anyMatch(fm ->
                                fm.getFamilyMemberColor().name().equals(this.getCurrentPlayer().getPawnColor().name())
                                        && !fm.getDiceColorAssociated().name().equals(DiceColor.NEUTRAL.name())
                                        && !selectedFamilyMember.getDiceColorAssociated().name().equals(DiceColor.NEUTRAL.name())))) {
                    this.gameManager.resetFamilyMemberValue(selectedFamilyMember);
                    throw new IncorrectInputException();
                }

                slot.insertFamilyMember(selectedFamilyMember);
            }

            this.gameManager.getCurrentPlayer().getPersonalBoard().addCard(card);
            slot.setCardStored(null);

            if(requirements != null && ventureCardAlternative != null) {
                Boolean alternativePayment = false;
                if(this.getCurrentPlayer().isConnected())
                    try {
                        alternativePayment = this.gameManager.getActivePlayerNetworkController().alternativeRequirementsPayment();
                    } catch (NetworkConnectionException ex) {
                        LOGGER.log(Level.INFO, ex.getMessage(), ex);
                        this.getCurrentPlayer().setDisconnected();
                    }
                if(alternativePayment)
                    this.gameManager.getCurrentPlayer().subResources(new Resources(
                            ventureCardAlternative.getResourceByType(COINS),
                            ventureCardAlternative.getResourceByType(WOODS),
                            ventureCardAlternative.getResourceByType(STONES),
                            ventureCardAlternative.getResourceByType(SERVANTS),
                            ((VentureCard) card).getMilitaryPointsSubstraction(),
                            ventureCardAlternative.getResourceByType(FAITH_POINTS),
                            ventureCardAlternative.getResourceByType(VICTORY_POINTS)));
                else
                    this.gameManager.getCurrentPlayer().subResources(requirements);
            }
            else if(requirements != null)
                this.gameManager.getCurrentPlayer().subResources(requirements);
            else
                this.gameManager.getCurrentPlayer().subResources((new Resources(
                        ventureCardAlternative.getResourceByType(COINS),
                        ventureCardAlternative.getResourceByType(WOODS),
                        ventureCardAlternative.getResourceByType(STONES),
                        ventureCardAlternative.getResourceByType(SERVANTS),
                        ((VentureCard) card).getMilitaryPointsSubstraction(),
                        ventureCardAlternative.getResourceByType(FAITH_POINTS),
                        ventureCardAlternative.getResourceByType(VICTORY_POINTS))));

            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();
            card.getInstantBonus().forEach(effect -> effect.applyEffect(this));
            if(card.getColor() != DevelopmentCardColor.GREEN && card.getColor() != DevelopmentCardColor.YELLOW && card.getPermanentBonus() != null)
                card.getPermanentBonus().applyEffect(this);

            if(!this.slotsRewardPenalty)
                slot.getResourcesReward().applyEffect(this);
            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).interactWithPlayer();
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

    public void noFamilyMemberRequired() {
        this.freeAction = true;
    }

    public void setRequirementsDiscount(Resources res) {
        this.requirementsDiscount = res;
    }
}