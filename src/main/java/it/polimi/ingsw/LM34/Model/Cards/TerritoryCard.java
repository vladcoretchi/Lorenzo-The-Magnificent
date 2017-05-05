package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 05/05/2017.
 */

public class TerritoryCard implements DevelopmentCardInterface {
    private DevelopmentCardColor color= DevelopmentCardColor.GREEN;
    private String territoryName;
    private Integer period;
    private Bonus instantBonus;
    private Bonus permanentBonus;
    private Integer diceValue;

    //territories does not cost anytype of resources or points as said in the game rules
    public TerritoryCard(String territoryName,Integer period,Bonus instantBonus, Bonus permanentBonus) {
        this.territoryName= territoryName;
        this.period= period;
    }




    @Override
    public Integer getPeriod() {
        return period;
    }

    @Override
    public String getName() {
        return territoryName;
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
        return "territoryCard";
    }

    @Override
    public Resources getResourcesRequired() {
        return null;
    }

}
