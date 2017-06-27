package it.polimi.ingsw.LM34.Model.Boards.PlayerBoard;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;

import java.io.Serializable;

public class BonusTile implements Serializable {
private ResourcesBonus productionBonus;
private ResourcesBonus harvestBonus;
private Integer harvDiceValue;
private Integer prodDiceValue;


//to be initialized before the starting of the game with the bonus values reported in the bonus tiles
    public BonusTile(Integer harvDiceValue, Integer prodDiceValue, ResourcesBonus pb, ResourcesBonus hb) {
	    this.productionBonus = pb;
	    this.harvestBonus = hb;
	    this.harvDiceValue = harvDiceValue;
	    this.prodDiceValue = prodDiceValue;
    }

    public Integer getHarvestDiceValue() {
        return this.harvDiceValue;
    }
    public Integer getProductionDiceValue() {
        return this.prodDiceValue;
    }

    public ResourcesBonus getHarvestBonus() {
        return this.harvestBonus;
    }

    public ResourcesBonus getProductionBonus() {
        return this.productionBonus;
    }
}