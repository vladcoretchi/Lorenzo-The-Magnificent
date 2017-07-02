package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Exceptions.Controller.MarketBanException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.BuildingCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;
import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.YELLOW;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class ProductionAreaContext extends AbstractGameContext {
    private Integer slotDiceValue;

    public ProductionAreaContext() {
        this.contextType = PRODUCTION_AREA_CONTEXT;
        this.slotDiceValue = 0;
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException, MarketBanException, OccupiedSlotException, NotEnoughResourcesException {
        Integer selectedSlot;
        try {
            selectedSlot = (Integer) args[0];
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        this.slotDiceValue = this.gameManager.getProductionArea().getActionSlots().get(selectedSlot).getDiceValue();

        setChanged();
        notifyObservers(this);

        ActionSlotContext actionSlotContext = (ActionSlotContext) getContextByType(ACTION_SLOT_CONTEXT);
        Boolean canPlace = actionSlotContext.interactWithPlayer(this, selectedSlot);
        if (canPlace) {
            FamilyMember selectedFamilyMember = (FamilyMember) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT).interactWithPlayer(this.slotDiceValue, true, this.contextType);

            if (actionSlotContext.getIgnoreOccupiedSlot())
                this.gameManager.getProductionArea().getActionSlots().get(selectedSlot).insertFamilyMemberIgnoringSlotLimit(selectedFamilyMember);
            else
                this.gameManager.getProductionArea().getActionSlots().get(selectedSlot).insertFamilyMember(selectedFamilyMember);
            selectedFamilyMember.placePawn();

            this.gameManager.getCurrentPlayer().getPersonalBoard().getDevelopmentCardsByType(YELLOW).ifPresent(list ->
            list.forEach(card -> {
                if (((BuildingCard) card).getDiceValueToProduct() <= selectedFamilyMember.getValue())
                    card.getPermanentBonus().applyEffect(this);
                })
            );
        }



        return null;
    }

    public void changeSlotDiceValue(Integer value, Boolean relative) {
        this.slotDiceValue = relative ? this.slotDiceValue + value : value;
    }
}
