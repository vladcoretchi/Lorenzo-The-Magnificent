package it.polimi.ingsw.LM34.Model.Boards.PlayerBoard;

import it.polimi.ingsw.LM34.Model.ResourceRelatedBonus.ResourcesBonus;

/**
 * Created by Giulio Comi on 03/05/2017.
 */

public class BonusTile {
private ResourcesBonus productionBonus;
private ResourcesBonus harvestBonus;


//to be initialized before the starting of the game with the bonus values reported in the bonus tiles
    public BonusTile(ResourcesBonus pb, ResourcesBonus hb) {
	this.productionBonus = pb;
	this.harvestBonus = hb;
    }

    public void setProductionBonus(ResourcesBonus b) {

    }

    public void setHarvestBonus(ResourcesBonus b) {
    }


}