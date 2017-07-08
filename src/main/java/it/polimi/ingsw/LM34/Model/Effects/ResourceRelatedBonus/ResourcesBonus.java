package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/*Basic Instant effects are handled here*/
//This represents also the excomunication malus on resources
public class ResourcesBonus extends AbstractEffect implements Observer {
    private static final long serialVersionUID = -6351217096331197402L;

    private Resources resources;
    private Integer councilPrivilege;
    private Integer developmentCardsGoodsMultiplier; /*santa rita*/

    public ResourcesBonus(Resources resources, Integer councilPrivilege) {
        this.resources = resources;
        this.councilPrivilege = councilPrivilege;
        this.developmentCardsGoodsMultiplier = null;
    }

    public ResourcesBonus(Integer developmentCardsGoodsMultiplier) {
        this.resources = null;
        this.councilPrivilege = null;
        this.developmentCardsGoodsMultiplier = developmentCardsGoodsMultiplier;
    }

    public Resources getResources() {
        return this.resources;
    }

    public Integer getCouncilPrivilege() {
        return this.councilPrivilege;
    }

    public Integer getDevelopmentCardsGoodsMultiplier() {
        return this.developmentCardsGoodsMultiplier;
    }

    @Override
    public void update(Observable o, Object arg) {
        AbstractGameContext callerContext = (AbstractGameContext) arg;
        ResourceIncomeContext incomeContext = (ResourceIncomeContext) callerContext.getContextByType(RESOURCE_INCOME_CONTEXT);
        incomeContext.setIncome(resources);
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        if(this.developmentCardsGoodsMultiplier != null && this.developmentCardsGoodsMultiplier > 1)
            callerContext.getContextByType(RESOURCE_INCOME_CONTEXT).addObserver(this);

        if(this.councilPrivilege != null && this.councilPrivilege > 0)
            try {
                ((UseCouncilPrivilegeContext) callerContext.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer(councilPrivilege);
            } catch(IncorrectInputException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            }
        if(this.resources != null)
            ((ResourceIncomeContext) callerContext.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT)).setIncome(this.resources);
    }
}
