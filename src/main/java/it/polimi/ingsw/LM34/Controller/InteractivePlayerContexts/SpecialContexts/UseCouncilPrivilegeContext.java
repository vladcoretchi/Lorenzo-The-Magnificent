package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractableContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;

/**
 * Created by GiulioComi on 23/05/2017.
 */
public class UseCouncilPrivilegeContext extends AbstractGameContext {
    private Integer numberOfCouncilePrivileges = 0;
    private ContextType type;
    private ArrayList<ResourcesBonus> rewardsForPrivilege;

    public UseCouncilPrivilegeContext() {
        //TODO: load bonus for privileges from configurator... rewardsForPrivilege = Configurator.getPalace();
        numberOfCouncilePrivileges = 0;
        contextType = ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;
    }

    public void initContext(Player player) {
        numberOfCouncilePrivileges = player.getCouncilPrivileges();
    }



    //TODO: before calling this without having councilPrivileges, it is better to check from caller contexts
    @Override
    public void interactWithPlayer(Player player) {
        ArrayList<ResourcesBonus> rewardsAvailable = rewardsForPrivilege;
        ResourceIncomeContext incomeContext = (ResourceIncomeContext)gameManager.getContextByType(RESOURCE_INCOME_CONTEXT);
        numberOfCouncilePrivileges = player.getCouncilPrivileges();

        Integer used =0;
        while(used < numberOfCouncilePrivileges) {
            Integer selected = gameManager.getActivePlayerNetworkController().privilegeRewardSelection(rewardsAvailable);
            ResourcesBonus reward = rewardsAvailable.get(selected);
            reward.applyEffect(this, player);
            incomeContext.handleResources(player, reward);
            /*Remove temporarily the reward already choosed*/
            rewardsAvailable.remove(selected);


            used++;
        }
    }
}
