package it.polimi.ingsw.LM34.Controller.SupportContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by GiulioComi on 23/05/2017.
 */
public class UseCouncilPrivilegeContext extends AbstractGameContext {
    private Integer numberOfCouncilePrivileges = 0;
    private ContextType type;

    public UseCouncilPrivilegeContext() {
        contextType = ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;
    }

    public void initContext(Player player) {
        numberOfCouncilePrivileges = player.getCouncilPrivileges();
    }



    @Override
    public void interactWithPlayer(Player player) {
        System.out.println("siamo in UseCouncilPrivilegeContext");

        //ResourceIncomeContext incomeContext = (ResourceIncomeContext) gameManager.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
        numberOfCouncilePrivileges = player.getCouncilPrivileges();

        for(Integer used=0; used<numberOfCouncilePrivileges; used++) {
            System.out.println("Scegli il bonus dato dalle pergamene");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                Integer.parseInt(br.readLine());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            //TODO: let the player choice a resourcebonus, but different from the other choosed at the same moment

    }

}
