package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;
import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.GREEN;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class HarvestAreaContext extends AbstractGameContext {

    public HarvestAreaContext() {
        this.contextType = HARVEST_AREA_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException, OccupiedSlotException {
        WorkingArea harvestArea = this.gameManager.getHarvestArea();
        Integer selectedSlot;
        Integer slotDiceValue;
        ActionSlot slot;
        try {
            selectedSlot = (Integer) args[0];
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        slot = (selectedSlot == 0) ? harvestArea.getSingleSlot() : harvestArea.getAdvancedSlot();

        slotDiceValue = slot.getDiceValue();

        setChanged();
        notifyObservers(this);

        ActionSlotContext actionSlotContext = (ActionSlotContext) getContextByType(ACTION_SLOT_CONTEXT);
        Boolean canPlace = actionSlotContext.interactWithPlayer(this, slot);
        if (canPlace) {
            FamilyMember selectedFamilyMember = ((FamilyMemberSelectionContext) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT)).interactWithPlayer(slotDiceValue, true, this.contextType);

            if (actionSlotContext.getIgnoreOccupiedSlot())
                this.gameManager.getProductionArea().getActionSlots().get(selectedSlot).insertFamilyMemberIgnoringSlotLimit(selectedFamilyMember);
            else
                this.gameManager.getProductionArea().getActionSlots().get(selectedSlot).insertFamilyMember(selectedFamilyMember);
            selectedFamilyMember.placePawn();

            this.gameManager.getCurrentPlayer().getPersonalBoard().getDevelopmentCardsByType(GREEN).ifPresent(list ->
                list.forEach(card -> {
                    if (((TerritoryCard) card).getDiceValueToHarvest() <= selectedFamilyMember.getValue())
                        card.getPermanentBonus().applyEffect(this);
                })
            );
        }

        return null;
    }


}
