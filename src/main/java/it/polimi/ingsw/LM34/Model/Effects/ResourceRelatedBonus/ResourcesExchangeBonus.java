package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.ResourcesExchangeContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * This class handles the multiple choice that some BuildingCards' permanent bonus offers
 */
public class ResourcesExchangeBonus extends AbstractEffect {
    private List<Pair<Resources, ResourcesBonus>> resourceExchange;

    public ResourcesExchangeBonus(List<Pair<Resources, ResourcesBonus>> resourceExchange) {
        this.resourceExchange = resourceExchange;
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        Player player = callerContext.getCurrentPlayer();
        Boolean hasResources = false;

        for (Pair<Resources, ResourcesBonus> pair : resourceExchange)
            if(player.hasEnoughResources(pair.getLeft()))
                hasResources = true;

        if(hasResources) {
            ResourcesExchangeContext resourceExchangeContext = (ResourcesExchangeContext) callerContext.getContextByType(ContextType.RESOURCE_EXCHANGE_CONTEXT);
            try {
                resourceExchangeContext.interactWithPlayer(resourceExchange);
            } catch (IncorrectInputException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
