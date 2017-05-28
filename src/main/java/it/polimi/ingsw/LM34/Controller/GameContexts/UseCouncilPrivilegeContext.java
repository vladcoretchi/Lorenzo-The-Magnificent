package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 23/05/2017.
 */
public class UseCouncilPrivilegeContext extends AbstractGameContext {
    private Integer numberOfCouncilePrivileges;


    public void initContext(Player player) {
        numberOfCouncilePrivileges = player.getCouncilPrivileges();
    }

    @Override
    public ContextType getType() {
        return ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;
    }

    public void interactWithPlayer(Player player, Integer numberOfCouncilePrivileges) {
        System.out.println("siamo in UseCouncilPrivilegeContext");
        ResourceIncomeContext incomeContext = new ResourceIncomeContext();
        //TODO: handle the player choice on how to use the privileges
        //for(Integer used=0; used<numberOfCouncilePrivileges; used++)
            //TODO: let the player choice a resourcebonus, but different from the other choosed at the same moment
            //incomeContext = (ResourceIncomeContext) getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
            //incomeContext.handleResources(player, new Resources(2,0,0,0));
    }



}
