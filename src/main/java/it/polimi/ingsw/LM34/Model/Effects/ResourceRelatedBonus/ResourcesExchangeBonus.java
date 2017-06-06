package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.ResourcesExchangeContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;

/**
 * Created by vladc on 5/13/2017.
 *
 * This class handles the multiple choice that some BuildingCards' permanent bonus offers
 */

//TODO: think of a way to have two alternatives for the special building cards
public class ResourcesExchangeBonus extends AbstractEffect {
    private List<Pair<Resources, ResourcesBonus>> resourceExchange;
    private Integer diceValueToActivate;
    //private ArrayList<ContextType> contextToBeSubscribedTo;


    public ResourcesExchangeBonus(Player player, Integer diceValue,List<Pair<Resources, ResourcesBonus>> resourceExchange) {
        //contextToBeSubscribedTo.add(ContextType.RESOURCE_EXCHANGE_CONTEXT);
        this.resourceExchange = resourceExchange;
        this.diceValueToActivate = diceValue;
    }



    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        Player player = callerContext.getCurrentPlayer();
        ResourcesExchangeContext resourceExchangeContext;
        resourceExchangeContext= (ResourcesExchangeContext) callerContext.getContextByType(ContextType.RESOURCE_EXCHANGE_CONTEXT);
        resourceExchangeContext.setBonuses(player, resourceExchange);
        resourceExchangeContext.interactWithPlayer();

    }


    public void activateResourcesExchange(AbstractGameContext callerContext, FamilyMember familyMember) {
        Player player = callerContext.getCurrentPlayer();

        for (Pair<Resources, ResourcesBonus> pair : resourceExchange) {
            /*check if the player has enough resources to activate the card permanent effect*/
            if (player.hasEnoughResources(pair.getLeft())) {
                /*retrieve from player the resources he needs to pay*/
                player.subResources(pair.getLeft());
                /*activate the card permanent effect providing the player with the resources desired*/
                ((ResourceIncomeContext)callerContext.getContextByType(RESOURCE_INCOME_CONTEXT)).setIncome(pair.getRight().getResources());
                player.addCouncilPrivileges(pair.getRight().getCouncilPrivilege());
                ((UseCouncilPrivilegeContext)callerContext.getContextByType(USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer();

            }
        }
    }
}
