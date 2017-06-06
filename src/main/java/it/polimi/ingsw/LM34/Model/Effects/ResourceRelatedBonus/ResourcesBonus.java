package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TOWERS_CONTEXT;


/**
 * Created by vladc on 5/13/2017.
 */

/*Basic Instant effects are handled here*/
//This represents also the excomunication malus on resources
public class ResourcesBonus extends AbstractEffect implements Observer {
    private Resources resources;
    private Integer councilPrivilege;
    private Integer developmentCardsGoodsMultiplier;

    public ResourcesBonus(Resources resources, Integer councilPrivilege) {
        this.resources = resources;
        this.councilPrivilege = councilPrivilege;
        this.developmentCardsGoodsMultiplier = 1;
    }

    /*santa rita*/
    public ResourcesBonus(Integer developmentCardsGoodsMultiplier) {
        this.resources = null;
        this.councilPrivilege = 0;
        this.developmentCardsGoodsMultiplier = developmentCardsGoodsMultiplier;
    }

    public Resources getResources() {
        return this.resources;
    }

    public void sumResourcesBonus(ResourcesBonus res) {
        this.resources.sumResources(res.getResources());
        this.councilPrivilege += res.getCouncilPrivilege();

    }

    public Integer getCouncilPrivilege() {
        return this.councilPrivilege;
    }

    public Integer getDevelopmentCardsGoodsMultiplier() {
        return this.developmentCardsGoodsMultiplier;
    }

    @Override
    public void update(Observable o, Object arg) {
        AbstractGameContext callerContext = (AbstractGameContext) o;
        ResourceIncomeContext incomeContext = (ResourceIncomeContext) callerContext.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
        incomeContext.setIncome(resources);
        System.out.println("Sono un observer della resourcheincomecontext e ho applicato il mio effetto");

        /*ContextType contextType = callerContext.getType();
        if(contextType == ContextType.TOWERS_CONTEXT)
            resources = (Resources) arg;*/
           // resources.sumResources(this.resources);
    }

    /*santa rita*/
  //resource income context

    @Override
    public void applyEffect(AbstractGameContext callerContext) {

        if(developmentCardsGoodsMultiplier > 1)
            callerContext.getContextByType(TOWERS_CONTEXT).addObserver(this);

        ResourceIncomeContext incomeContext = (ResourceIncomeContext) callerContext.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT);
        //incomeContext.handleResources(player, new ResourcesBonus(resources, councilPrivilege));
        callerContext.getCurrentPlayer().addCouncilPrivileges(councilPrivilege);
        callerContext.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT).interactWithPlayer();

    }

}
