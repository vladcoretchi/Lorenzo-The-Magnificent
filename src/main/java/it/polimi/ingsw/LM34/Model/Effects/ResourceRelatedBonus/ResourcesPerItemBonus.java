package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.List;
import java.util.Optional;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.MILITARY_POINTS;

/**
 * Created by vladc on 5/13/2017.
 */
public class ResourcesPerItemBonus extends AbstractEffect {
    private Player player;
    private Resources bonusResources;
    private DevelopmentCardColor cardColor; //"nobile, araldo, cortigiana,governatore, zecca, teatro, esattoria,arco di triongo"
    private Integer militaryPointsRequired; //for "generale" card

    public ResourcesPerItemBonus(Resources bonusResources, DevelopmentCardColor cardColor) {
        this.bonusResources = bonusResources;
        this.cardColor = cardColor; //"nobile, araldo, cortigiana,governatore, zecca, teatro, esattoria,arco di triongo"
        this.militaryPointsRequired = militaryPointsRequired; //"generale" card
    }

    /*Constructor for "generale" card*/
    public ResourcesPerItemBonus(Resources bonusResources, Integer militaryPointsRequired) {
        this.player = player;
        this.bonusResources = bonusResources;
        this.cardColor = null; //"nobile, araldo, cortigiana,governatore, zecca, teatro, esattoria,arco di triongo"
        this.militaryPointsRequired = militaryPointsRequired; //"generale" card
    }


    /**
     * Only for building cards permanent bonuses
     * @param contexts
     */


    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        Integer cardTypeOwnedNum = 0;
        ResourceIncomeContext incomeContext;
        incomeContext = ((ResourceIncomeContext)callerContext.getContextByType(RESOURCE_INCOME_CONTEXT));
        if(militaryPointsRequired > 0) {
            bonusResources.multiplyResources(player.getResources().getResourceByType(MILITARY_POINTS) / militaryPointsRequired);
            incomeContext.setIncome(bonusResources);
        }

        Optional<List<AbstractDevelopmentCard>> cards = player.getPersonalBoard().getDevelopmentCardsByType(cardColor);
        cards.ifPresent(cardsList -> {
            bonusResources.multiplyResources(cardsList.size());
            incomeContext.setIncome(bonusResources);
        });
    }
}



