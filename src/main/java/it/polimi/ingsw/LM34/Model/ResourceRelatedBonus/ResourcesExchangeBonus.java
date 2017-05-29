package it.polimi.ingsw.LM34.Model.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.SupportContexts.ResourcesExchangeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 *
 * This class handles the multiple choice that some BuildingCards' permanent bonus offers
 */

//TODO: think of a way to have two alternatives for the special building cards
public class ResourcesExchangeBonus extends AbstractEffect implements Observer {
    private Player player;
    private Pair<Resources, Resources>[] resourceExchange;
    private Pair<Resources, Integer>[] resourceForPrivileges; //Only for "Residenza" building card
    private Integer diceValueToActivate;
    private ArrayList<ContextType> contextToBeSubscribedTo;


    public ResourcesExchangeBonus(Player player, Integer diceValue,Pair<Resources, Resources>[] resourceExchange, Pair<Resources, Integer>[] resourceForPrivileges) {
        contextToBeSubscribedTo.add(ContextType.RESOURCE_EXCHANGE_CONTEXT);
        this.resourceExchange = resourceExchange;
        this.resourceForPrivileges = resourceForPrivileges;
        this.diceValueToActivate = diceValue;
    }



    @Override
    public void applyEffect(Player player) {
        ResourcesExchangeContext resourceExchangeContext;
        resourceExchangeContext= (ResourcesExchangeContext) GameManager.getContextByType(ContextType.RESOURCE_EXCHANGE_CONTEXT);
        resourceExchangeContext.setBonuses(player, resourceExchange, resourceForPrivileges);
    }




    @Override
    public void update(Observable o, Object arg) {
        FamilyMember familyMember = (FamilyMember) arg;

        /*check if dice value is enough to activate the card permanent effect*/
        if(familyMember.getValue() >= diceValueToActivate) {
            activatePrivilegeExchange(familyMember);
            activateResourcesExchange(familyMember);
        }

    }

    public void activatePrivilegeExchange(FamilyMember familyMember) {

        for(Pair<Resources, Integer> pair : resourceForPrivileges) {
            /*check if the player has enough resources to activate the card permanent effect*/
            if (player.hasEnoughResources(pair.getLeft())) {
                player.subResources(pair.getLeft());
                /*activate the card permanent effect providing the player with the resources desired*/
                player.addCouncilPrivileges(pair.getRight());
            }
        }
    }

    public void activateResourcesExchange(FamilyMember familyMember) {

        for (Pair<Resources, Resources> pair : resourceExchange) {
            /*check if the player has enough resources to activate the card permanent effect*/
            if (player.hasEnoughResources(pair.getLeft())) {
                /*retrieve from player the resources he needs to pay*/
                player.subResources(pair.getLeft());
                /*activate the card permanent effect providing the player with the resources desired*/
                player.addResources(pair.getRight());
            }
        }
    }

//production area
}
