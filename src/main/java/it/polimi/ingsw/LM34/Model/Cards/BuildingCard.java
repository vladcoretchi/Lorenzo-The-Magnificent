package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.List;

public class BuildingCard extends AbstractDevelopmentCard {

    private Integer diceValueToProduct;

    public BuildingCard(String buildingName, Integer diceValueToProduct, Integer period, Resources resourcesRequired,
                        List<AbstractEffect> instantBonus, AbstractEffect permanentBonus) {
        this.resourceRequired = resourcesRequired;
        this.name = buildingName;
        this.period = period;
        this.permanentBonus = permanentBonus;
        this.diceValueToProduct = diceValueToProduct;
        this.instantBonus = instantBonus;
        this.color = DevelopmentCardColor.YELLOW;
    }



    public Integer getDiceValueToProduct() {
        return diceValueToProduct;
    }

    @Override
    public String toString() {
        return "buildingCard";
    }
}
