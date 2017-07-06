package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;

public class CouncilPalaceContext extends AbstractGameContext {

    public CouncilPalaceContext() {
        this.contextType = COUNCIL_PALACE_CONTEXT;
    }
    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException {
        CouncilPalace councilPalace = this.gameManager.getCouncilPalace();
        ActionSlot palaceSlot = councilPalace.getActionSlot();

        FamilyMember selectedFamilyMember = ((FamilyMemberSelectionContext) getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT))
                                                    .interactWithPlayer(palaceSlot.getDiceValue(), false, this.contextType);

        councilPalace.addFamilyMember(selectedFamilyMember);
        selectedFamilyMember.placePawn();

        ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).initIncome();
        ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT))
                                    .setIncome(palaceSlot.getResourcesReward().getResources());
        ((UseCouncilPrivilegeContext) getContextByType(USE_COUNCIL_PRIVILEGE_CONTEXT))
                                    .interactWithPlayer(palaceSlot.getResourcesReward().getCouncilPrivilege());
        ((ResourceIncomeContext) getContextByType(RESOURCE_INCOME_CONTEXT)).interactWithPlayer();

        return null;
    }
}