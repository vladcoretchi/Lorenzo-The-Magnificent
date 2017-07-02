package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Exceptions.Controller.MarketBanException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughMilitaryPoints;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;

public class CouncilPalaceContext extends AbstractGameContext {

    public CouncilPalaceContext() {
        this.contextType = COUNCIL_PALACE_CONTEXT;
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException {
        CouncilPalace councilPalace = this.gameManager.getCouncilPalace();

        FamilyMember selectedFamilyMember = ((FamilyMemberSelectionContext) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT)).interactWithPlayer(councilPalace.getActionSlot().getDiceValue(), false, this.contextType);

        councilPalace.addFamilyMember(selectedFamilyMember);
        selectedFamilyMember.placePawn();

        ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).setIncome(councilPalace.getActionSlot().getResourcesReward().getResources());
        ((UseCouncilPrivilegeContext) getContextByType(USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer(councilPalace.getActionSlot().getResourcesReward().getCouncilPrivilege());

        return null;
    }
}