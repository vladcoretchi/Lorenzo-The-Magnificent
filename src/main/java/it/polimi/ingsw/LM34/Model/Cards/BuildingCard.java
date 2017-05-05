package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Exception.Model.InvalidResourceTypeException;
import it.polimi.ingsw.LM34.Model.Bonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Enum.ResourceType;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class BuildingCard implements DevelopmentCardInterface {
    private DevelopmentCardColor color= DevelopmentCardColor.YELLOW;

    private Bonus instantBonus;
    private Bonus permanentBonus;
    private String buildingName;

    private Resources resourcesRequired; //we do not store single type of resources as Integer but we wrap them in a Resources class
    private Integer period;
    private Integer diceValue;


    public BuildingCard(String characterName,Integer period, Resources resourcesRequired, Bonus instantBonus, Bonus permanentBonus) {
        this.resourcesRequired= resourcesRequired;
        this.buildingName= buildingName;
        this.period= period;
        this.instantBonus= instantBonus;
        this.permanentBonus= permanentBonus;
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
    public Bonus getInstantBonus() {
        return instantBonus;
    }

    @Override
    public Bonus getPermanentBonus() {
        return permanentBonus;
    }

    @Override
    public Integer getDiceValueToActivateBonus() {
        return diceValue;
    }

    public String toString() {
        return "buildingCard";
    }
}
