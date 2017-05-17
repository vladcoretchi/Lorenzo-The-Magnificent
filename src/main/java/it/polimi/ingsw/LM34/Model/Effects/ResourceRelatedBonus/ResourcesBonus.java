package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by vladc on 5/13/2017.
 */
public class ResourcesBonus extends AbstractEffect implements Observer {
    private Resources resources;
    private Integer councilPrivilege;
    private Integer developmentCardsGoodsMultiplier;

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
        Player player = (Player) arg;
        player.addResources(this.resources);
        System.out.println("gli observer dell'endgame han finito");
    }
}
