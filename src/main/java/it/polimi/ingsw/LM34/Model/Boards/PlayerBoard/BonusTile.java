package it.polimi.ingsw.LM34.Model.Boards.PlayerBoard;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by Giulio Comi on 03/05/2017.
 */

public class BonusTile {
private Resources productionBonus;
private Resources harvestBonus;


//to be initialized before the starting of the game with the bonus values reported in the bonus tiles
    public BonusTile(Resources pb, Resources hb) {
	this.productionBonus = pb;
	this.harvestBonus = hb;
    }

    public void setProductionBonus(ResourcesBonus b) {

    }

    public void setHarvestBonus(ResourcesBonus b) {
    }

    public Resources getHarvestBonus() {
        return this.harvestBonus;
    }

    public Resources getProductionBonus() {
        return this.productionBonus;
    }
}