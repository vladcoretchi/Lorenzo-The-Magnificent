package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;

import java.util.List;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;

public class UseCouncilPrivilegeContext extends AbstractGameContext {
    private Integer numberOfCouncilePrivileges = 0;
    private ContextType type;
    private List<ResourcesBonus> rewardsForPrivilege;

    public UseCouncilPrivilegeContext() {
        //TODO: load bonus for privileges from configurator... rewardsForPrivilege = Configurator.getPalace();
        numberOfCouncilePrivileges = 0;
        contextType = ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;
    }

    public void initContext(Integer councilPrivileges) {
        this.numberOfCouncilePrivileges = councilPrivileges;
    }

    //TODO: before calling this without having councilPrivileges, it is better to check from caller contexts
    @Override
    public void interactWithPlayer() {
        if(this.numberOfCouncilePrivileges > 0) {
            List<ResourcesBonus> rewardsAvailable = this.rewardsForPrivilege;
            ResourceIncomeContext incomeContext = (ResourceIncomeContext) gameManager.getContextByType(RESOURCE_INCOME_CONTEXT);

            Integer used = 0;
            while (used < numberOfCouncilePrivileges) {
                /*Integer selected = gameManager.getActivePlayerNetworkController().privilegeRewardSelection(rewardsAvailable);
                ResourcesBonus reward = rewardsAvailable.get(selected);
                incomeContext.setIncome(reward.getResources());
                //Remove temporarily the reward already choosed
                rewardsAvailable.remove(selected);
                used++;*/
            }
        }
    }
}
