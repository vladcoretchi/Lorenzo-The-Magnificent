package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.List;

import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.GREEN;

public class TerritoryCard extends AbstractDevelopmentCard {
    private static final long serialVersionUID = -8495567739023202787L;

    private Integer diceValueToHarvest;

    public TerritoryCard(String territoryName, Integer diceValueToHarvest, Integer period, List<AbstractEffect> instantBonus, ResourcesBonus permanentBonus) {
        this.name = territoryName;
        this.period = period;
        this.diceValueToHarvest = diceValueToHarvest;
        this.permanentBonus = permanentBonus;
        this.instantBonus = instantBonus;
        this.color = GREEN;
        this.resourceRequired = new Resources(); //territories does not cost resources or points as said in the game rules
    }

    public Integer getDiceValueToHarvest() {
        return this.diceValueToHarvest;
    }

    @Override
    public String toString() {
        return "territoryCard";
    }
}
