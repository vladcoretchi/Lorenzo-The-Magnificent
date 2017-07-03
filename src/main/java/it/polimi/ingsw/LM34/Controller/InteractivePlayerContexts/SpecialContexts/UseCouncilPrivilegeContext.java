package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class UseCouncilPrivilegeContext extends AbstractGameContext {
    private List<Resources> rewardsForPrivilege;

    public UseCouncilPrivilegeContext() {
        this.contextType = USE_COUNCIL_PRIVILEGE_CONTEXT;
        this.rewardsForPrivilege = null;
    }

    public void setRewards(List<Resources> rewards) {
        this.rewardsForPrivilege = rewards;
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException {
        Integer numberOfCouncilPrivileges;
        try {
            numberOfCouncilPrivileges = (Integer) args[0];
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        ResourceIncomeContext incomeContext = (ResourceIncomeContext) gameManager.getContextByType(RESOURCE_INCOME_CONTEXT);
        List<Resources> rewardsAvailable = this.rewardsForPrivilege;

        for (int i = 0; i < numberOfCouncilPrivileges; i++) {
            Integer choice = councilPrivilegeRewardSelection(rewardsAvailable);
            incomeContext.setIncome(rewardsAvailable.get(choice));
            rewardsAvailable.remove(choice.intValue());
        }

        return null;
    }

    private Integer councilPrivilegeRewardSelection(List<Resources> rewardsAvailable) {
        Integer choice = this.gameManager.getActivePlayerNetworkController().selectCouncilPrivilegeBonus(rewardsAvailable);
        try {
            Validator.checkValidity(choice, rewardsAvailable);
            return choice;
        } catch(IncorrectInputException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            return councilPrivilegeRewardSelection(rewardsAvailable);
        }
    }
}
