package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Exceptions.Controller.MarketBanException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class MarketAreaContext extends AbstractGameContext {
    private Boolean ban;

    public MarketAreaContext() {
        this.contextType = MARKET_AREA_CONTEXT;
        this.ban = false;
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException, MarketBanException, OccupiedSlotException {
        this.ban = false;
        setChanged();
        notifyObservers(this);

        if (this.ban)
            throw new MarketBanException();

        Integer selectedSlot;
        try {
            selectedSlot = (Integer) args[0];
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        ActionSlot slot = this.gameManager.getMarket().getActionSlots().get(selectedSlot);

        ActionSlotContext actionSlotContext = (ActionSlotContext) getContextByType(ACTION_SLOT_CONTEXT);
        Boolean canPlace = actionSlotContext.interactWithPlayer(this, selectedSlot);
        if (canPlace) {
            FamilyMember selectedFamilyMember = ((FamilyMemberSelectionContext) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT)).interactWithPlayer(slot.getDiceValue(), false, this.contextType);

            if (actionSlotContext.getIgnoreOccupiedSlot())
                slot.insertFamilyMemberIgnoringSlotLimit(selectedFamilyMember);
            else
                slot.insertFamilyMember(selectedFamilyMember);
            selectedFamilyMember.placePawn();

            ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).setIncome(slot.getResourcesReward().getResources());
            ((UseCouncilPrivilegeContext) getContextByType(USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer(slot.getResourcesReward().getCouncilPrivilege());
        }

        return null;
    }

    public void setBan() {
        this.ban = true;
    }
}
