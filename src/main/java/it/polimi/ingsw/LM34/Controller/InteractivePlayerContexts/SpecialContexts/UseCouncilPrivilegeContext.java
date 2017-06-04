package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

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



    @Override
    public void interactWithPlayer(Player player) {
        ArrayList<ResourcesBonus> rewardsAvailable = rewardsForPrivilege;
        //ResourceIncomeContext incomeContext = (ResourceIncomeContext) gameManager.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
        numberOfCouncilePrivileges = player.getCouncilPrivileges();

        Integer used =0;
        while(used < numberOfCouncilePrivileges) {
            Integer selected = gameManager.getActivePlayerNetworkController().privilegeRewardSelection(rewardsAvailable);
            rewardsAvailable.get(selected).applyEffect(this, player);
            /*Remove temporarily the reward already choosed*/
            rewardsAvailable.remove(selected);

            used++;
        }
    }
}
