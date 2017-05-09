package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Model.Bonus.TerritoryCardPermanentBonus;

/**
 * Created by GiulioComi on 05/05/2017.
 */

public class TerritoryCard implements DevelopmentCardInterface {
    private DevelopmentCardColor color= DevelopmentCardColor.GREEN;
    private String territoryName;
    private Integer period;
    private PermanentBonus permanentBonus;
    private Integer diceValueToHarvest;


    //territories does not cost anytype of resources or points as said in the game rules
    public TerritoryCard(String territoryName, Integer diceValueToHarvest, Integer period, TerritoryCardPermanentBonus permaBonus) {
        this.territoryName = territoryName;
        this.period = period;
        this.diceValueToHarvest = diceValueToHarvest;
        this.permanentBonus = permaBonus;
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
    public PermanentBonus getPermanentBonus() {
        return permanentBonus;
    }

    public String toString() {
        return "territoryCard";
    }

    @Override
    public Resources getResourcesRequired() {
        return null;
    }

}
