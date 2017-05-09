package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class BuildingCard implements DevelopmentCardInterface {
    private DevelopmentCardColor color= DevelopmentCardColor.YELLOW;


    private PermanentBonus permanentBonus;
    private String buildingName;
    private Integer diceValueToProduct;
    private Resources resourcesRequired; //we do not store single type of resources as Integer but we wrap them in a Resources class
    private Integer period;
    //this two variables together represents the instant Bonus
    private Integer councilPrivilege;
    private Resources resourcesBonus;


    public BuildingCard(String characterName,Integer diceValueToProduct, Integer period, Resources resourcesRequired, Resources resourcesBonus, Integer councilPrivilege, PermanentBonus permanentBonus) {
        this.resourcesRequired= resourcesRequired;
        this.buildingName= buildingName;
        this.period= period;
        this.permanentBonus= permanentBonus;
        this.diceValueToProduct= diceValueToProduct;
        this.resourcesBonus = resourcesBonus;
        this.councilPrivilege = councilPrivilege;
    }

    public Resources getResourcesRequired() {
        return resourcesRequired;
    }

    @Override
    public Integer getPeriod() {
        return period;
    }

    @Override
    public String getName() {
        return buildingName;
    }

    @Override
    public PermanentBonus getPermanentBonus() {
        return permanentBonus;
    }


    public Integer getDiceValueToProduct() {
        return diceValueToProduct;
    }

    public String toString() {
        return "buildingCard";
    }
}
