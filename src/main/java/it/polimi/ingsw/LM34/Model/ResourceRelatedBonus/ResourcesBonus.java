package it.polimi.ingsw.LM34.Model.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.WOODS;


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
        System.out.println(resources.getResourceByType(WOODS));
        /*ResourceIncomeContext callerContext = (ResourceIncomeContext) o;
        ContextType contextType = callerContext.getType();
        if(contextType == ContextType.TOWERS_CONTEXT)
            resources = (Resources) arg;
            resources.sumResources(this.resources);

        Player player = (Player) arg;*/
    }

    /*santa rita*/
    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(contexts, ContextType.RESOURCE_INCOME_CONTEXT).addObserver(this);
        //Utilities.getContextByType(ContextType.TOWERS_CONTEXT).addObserver(this);
    }

    @Override
    public void applyEffect(ArrayList<AbstractGameContext> contexts, Player player) {

        //TODO: access through resourcesincomecontext
        //then i register...
        // resourcesincomecontext.handleResources(player, resources);
        //subscribeObserverToContext(contexts);

        //player.addCouncilPrivileges(councilPrivilege);
        //player.addResources(resources);

    }


}
