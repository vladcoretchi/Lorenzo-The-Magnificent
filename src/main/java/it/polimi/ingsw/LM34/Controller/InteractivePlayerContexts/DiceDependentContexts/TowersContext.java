package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.MarketBanException;
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

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.FAMILY_MEMBER_SELECTION_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.*;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class TowersContext extends AbstractGameContext {
    //TODO: handle Cesare Borgia

    private Boolean slotRewardPenalty;
    private Boolean noOccupiedTowerTax;
    private Integer slotDiceValue;
    private DevelopmentCardColor towerColor;

    public TowersContext() {
        this.contextType = ContextType.TOWERS_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException, MarketBanException, OccupiedSlotException, NotEnoughResourcesException {
        Pair<Integer, Integer> slotSelection;
        try {
            Pair<?, ?> slotArg = (Pair<?, ?>) args[0];
            slotSelection = new ImmutablePair<>((Integer) slotArg.getLeft(), (Integer) slotArg.getRight());
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        Tower selectedTower = this.gameManager.getTowers().get(slotSelection.getLeft());
        TowerSlot slot = selectedTower.getTowerSlots().get(slotSelection.getRight());

        if(!slot.isEmpty())
            throw new OccupiedSlotException();

        this.slotDiceValue = slot.getDiceValue();
        this.towerColor = selectedTower.getCardColor();
        this.slotRewardPenalty = false;
        this.noOccupiedTowerTax = false;

        setChanged();
        notifyObservers(this);

        AbstractDevelopmentCard card = slot.getCardStored();

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

        FamilyMember selectedFamilyMember = (FamilyMember) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT).interactWithPlayer(this.slotDiceValue, false, this.contextType);

        try {
            slot.insertFamilyMember(selectedFamilyMember);
            selectedFamilyMember.placePawn();

            this.gameManager.getCurrentPlayer().getPersonalBoard().addCard(card);
            slot.sweepTowerSlot();

            this.gameManager.getCurrentPlayer().subResources(requirements);

            card.getInstantBonus().forEach(effect -> effect.applyEffect(this));
            card.getPermanentBonus().applyEffect(this);

            if(!this.slotRewardPenalty)
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

    public void changeSlotDiceValue(Integer value, Boolean relative) {
        this.slotDiceValue = relative ? this.slotDiceValue + value : value;
    }

    public void setSlotRewardPenalty() {
        this.slotRewardPenalty = true;
    }

    public void avoidOccupiedTowerTax() {
        this.noOccupiedTowerTax = true;
    }
}