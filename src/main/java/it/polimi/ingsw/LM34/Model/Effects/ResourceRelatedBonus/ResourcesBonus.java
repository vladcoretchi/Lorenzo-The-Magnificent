package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TOWERS_CONTEXT;


/**
 * Created by vladc on 5/13/2017.
 */

/*Basic Instant effects are handled here*/
//This represents also the excomunication malus on resources
public class ResourcesBonus extends AbstractEffect implements Observer, Serializable {
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
        this.resources = new Resources();
        this.councilPrivilege = 0;
        this.developmentCardsGoodsMultiplier = developmentCardsGoodsMultiplier;
    }

    public Resources getResources() {
        return this.resources;
    }

    public void sumResourcesBonus(ResourcesBonus resBonus) {
        this.resources.sumResources(resBonus.getResources());
        this.councilPrivilege += resBonus.getCouncilPrivilege();
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

        //TODO
         /*santa rita*/
        //resource income context

    }



    @Override
    public void applyEffect(AbstractGameContext callerContext) {

        if(developmentCardsGoodsMultiplier > 1)
            callerContext.getContextByType(TOWERS_CONTEXT).addObserver(this);

        UseCouncilPrivilegeContext councilPrivilegeContext = (UseCouncilPrivilegeContext) callerContext.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT);
        councilPrivilegeContext.initContext(councilPrivilege);
        councilPrivilegeContext.interactWithPlayer();
    }

}
