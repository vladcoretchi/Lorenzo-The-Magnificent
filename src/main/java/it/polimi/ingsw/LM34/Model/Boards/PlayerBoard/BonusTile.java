package it.polimi.ingsw.LM34.Model.Boards.PlayerBoard;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;

import java.io.Serializable;

public class BonusTile implements Serializable {
    private static final long serialVersionUID = -6130532497303242455L;

    private ResourcesBonus productionBonus;
    private ResourcesBonus harvestBonus;
    private Integer harvDiceValue;
    private Integer prodDiceValue;

    /**
     * Constructor of the bonus tile
     * @param harvestDiceValue
     * @param productionDiceValue
     * @param productionReward
     * @param harvestReward
     */
    public BonusTile(Integer harvDiceValue, Integer prodDiceValue, ResourcesBonus prodBonus, ResourcesBonus harvBonus) {
        this.productionBonus = prodBonus;
        this.harvestBonus = harvBonus;
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