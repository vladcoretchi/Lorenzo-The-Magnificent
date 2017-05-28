package it.polimi.ingsw.LM34.Model.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.SupportContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


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
        this.developmentCardsGoodsMultiplier = developmentCardsGoodsMultiplier; //santa rita, 2 di solito
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
        ResourceIncomeContext callerContext = (ResourceIncomeContext) GameManager.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
        callerContext.setIncome(resources);
        System.out.println("Sono un observer della resourcheincomecontext e ho applicato il mio effetto");

        /*ContextType contextType = callerContext.getType();
        if(contextType == ContextType.TOWERS_CONTEXT)
            resources = (Resources) arg;*/
           // resources.sumResources(this.resources);
    }

    /*santa rita*/
    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(contexts, ContextType.RESOURCE_INCOME_CONTEXT).addObserver(this);
        //Utilities.getContextByType(ContextType.TOWERS_CONTEXT).addObserver(this);
    }



    @Override
    public void applyEffect(Player player) {

        ResourceIncomeContext incomeContext = (ResourceIncomeContext) GameManager.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT);
        incomeContext.handleResources(player, resources);


        if(councilPrivilege > 0) {
            player.addCouncilPrivileges(councilPrivilege);
            GameManager.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT).interactWithPlayer(player);
        }
    }

}
