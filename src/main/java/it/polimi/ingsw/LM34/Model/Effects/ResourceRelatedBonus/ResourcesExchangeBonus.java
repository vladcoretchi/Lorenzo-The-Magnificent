package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.ResourcesExchangeContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractableContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;

/**
 * Created by vladc on 5/13/2017.
 *
 * This class handles the multiple choice that some BuildingCards' permanent bonus offers
 */

//TODO: think of a way to have two alternatives for the special building cards
public class ResourcesExchangeBonus extends AbstractEffect implements Observer {
    private Player player;
    private List<Pair<Resources, ResourcesBonus>> resourceExchange;
    private Integer diceValueToActivate;
    //private ArrayList<ContextType> contextToBeSubscribedTo;


    public ResourcesExchangeBonus(Player player, Integer diceValue,List<Pair<Resources, ResourcesBonus>> resourceExchange) {
        //contextToBeSubscribedTo.add(ContextType.RESOURCE_EXCHANGE_CONTEXT);
        this.resourceExchange = resourceExchange;
        this.diceValueToActivate = diceValue;
    }



    @Override
    public void applyEffect(AbstractGameContext callerContext, Player player) {
        ResourcesExchangeContext resourceExchangeContext;
        resourceExchangeContext= (ResourcesExchangeContext) callerContext.getContextByType(ContextType.RESOURCE_EXCHANGE_CONTEXT);
        resourceExchangeContext.setBonuses(player, resourceExchange);


        /*Subscribe to Production area context*/
        callerContext.getContextByType(PRODUCTION_AREA_CONTEXT).addObserver(this);
    }




    @Override
    public void update(Observable o, Object arg) {
        FamilyMember familyMember = (FamilyMember) arg;
        AbstractGameContext callerContext = (AbstractGameContext) o;

        /*check if dice value is enough to activate the card permanent effect*/
        if(familyMember.getValue() >= diceValueToActivate) {
            activateResourcesExchange(callerContext, familyMember);
        }

    }

    public void activatePrivilegeExchange(AbstractGameContext callerContext, FamilyMember familyMember) {

        for(Pair<Resources, ResourcesBonus> pair : resourceExchange) {
            /*check if the player has enough resources to activate the card permanent effect*/
            if (player.hasEnoughResources(pair.getLeft())) {
                player.subResources(pair.getLeft());
                /*activate the card permanent effect providing the player with the resources desired*/
                player.addCouncilPrivileges(pair.getRight().getCouncilPrivilege());
                ((UseCouncilPrivilegeContext)callerContext.getContextByType(USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer(player);
            }
        }
    }

    public void activateResourcesExchange(AbstractGameContext callerContext, FamilyMember familyMember) {

        for (Pair<Resources, ResourcesBonus> pair : resourceExchange) {
            /*check if the player has enough resources to activate the card permanent effect*/
            if (player.hasEnoughResources(pair.getLeft())) {
                /*retrieve from player the resources he needs to pay*/
                player.subResources(pair.getLeft());
                /*activate the card permanent effect providing the player with the resources desired*/
                ((ResourceIncomeContext)callerContext.getContextByType(RESOURCE_INCOME_CONTEXT)).handleResources(player, pair.getRight().getResources());


            }
        }
    }

//production area
}
