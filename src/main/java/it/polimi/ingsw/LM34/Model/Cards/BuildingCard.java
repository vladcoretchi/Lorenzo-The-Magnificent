package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.EffectInterface;
import it.polimi.ingsw.LM34.Model.Bonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class BuildingCard extends AbstractDevelopmentCard {
    private DevelopmentCardColor color= DevelopmentCardColor.YELLOW;


    private EffectInterface permanentBonus;
    private String name;
    private Integer diceValueToProduct;
    private Resources resourcesRequired; //we do not store single type of resources as Integer but we wrap them in a Resources class
    private Integer period;
    //this two variables together represents the instant Bonus
    private ResourcesBonus instantBonus;
    private Resources resourcesBonus;


    public BuildingCard(String buildingName, Integer diceValueToProduct, Integer period, Resources resourcesRequired, ResourcesBonus instantBonus, EffectInterface permanentBonus) {
        this.resourcesRequired= resourcesRequired;
        this.name= buildingName;
        this.period = period;
        this.permanentBonus= permanentBonus;
        this.diceValueToProduct= diceValueToProduct;
        this.instantBonus = instantBonus;
    }

    public Resources getResourcesRequired() {
        return resourcesRequired;
    }

    public EffectInterface getPermanentBonus() {
        return this.permanentBonus;
    }

    public ResourcesBonus getInstantBonus() {
        return instantBonus;
    }

    public Integer getDiceValueToProduct() {
        return diceValueToProduct;
    }

    @Override
    public DevelopmentCardColor getColor() { return this.color; }

    public Integer getPeriod() {
        return this.period;
    }

    public String getName() {
        return name;
    }
}
