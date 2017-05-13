package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.InstantBonus;
import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Model.Bonus.TerritoryCardPermanentBonus;

/**
 * Created by GiulioComi on 05/05/2017.
 */

public class TerritoryCard extends DevelopmentCardInterface {
    private DevelopmentCardColor color= DevelopmentCardColor.GREEN;
    private String territoryName;
    private Integer period;
    private PermanentBonus permanentBonus;
    private InstantBonus instantBonus;
    private Integer diceValueToHarvest;
    private Resources resourceRequired;


    //territories does not cost anytype of resources or points as said in the game rules
    public TerritoryCard(String territoryName, Integer diceValueToHarvest, Integer period, TerritoryCardPermanentBonus permanentBonus) {
        this.territoryName = territoryName;
        this.period = period;
        this.diceValueToHarvest = diceValueToHarvest;
        this.permanentBonus = permanentBonus;
    }


    @Override
    public Integer getPhase() {
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

    @Override
    public InstantBonus getInstantBonus() {
        return instantBonus;
    }

    public String toString() {
        return "territoryCard";
    }

    @Override
    public Resources getResourcesRequired() {
        return null;
    }

}
