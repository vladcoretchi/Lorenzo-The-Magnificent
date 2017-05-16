package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 05/05/2017.
 */

public class TerritoryCard extends AbstractDevelopmentCard {
    private DevelopmentCardColor color= DevelopmentCardColor.GREEN;
    private String name;
    private Integer period;
    private ResourcesBonus permanentBonus;
    private ResourcesBonus  instantBonus;
    private Integer diceValueToHarvest;
    private Resources resourceRequired;


    //territories does not cost anytype of resources or points as said in the game rules
    public TerritoryCard(String territoryName, Integer diceValueToHarvest, Integer period, ResourcesBonus instantBonus, ResourcesBonus  permanentBonus) {
        this.name = territoryName;
        this.period = period;
        this.diceValueToHarvest = diceValueToHarvest;
        this.permanentBonus = permanentBonus;
        this.instantBonus = instantBonus;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getPeriod() {
        return this.period;
    }

    public ResourcesBonus getPermanentBonus() {
        return permanentBonus;
    }

    @Override
    public ResourcesBonus getInstantBonus() {
        return instantBonus;
    }

    /**
     *
     * @return a resources with all type of goods set to 0 because territory cards do not have requirements
     */
    public Resources getResourcesRequired() {
        return new Resources();
    }

    @Override
    public DevelopmentCardColor getColor() { return this.color; }

}
