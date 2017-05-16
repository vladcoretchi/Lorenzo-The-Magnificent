package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.EffectInterface;
import it.polimi.ingsw.LM34.Model.Bonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public abstract class AbstractDevelopmentCard {

    private String name;
    private Integer period;
    private DevelopmentCardColor color;
    private EffectInterface permanentBonus;
    private ResourcesBonus instantBonus;
    private Resources resourceRequired;


    public ResourcesBonus getInstantBonus() { return this.instantBonus; }

    public Resources getResourcesRequired() { return this.resourceRequired; }

    public Integer getPeriod() {
        return this.period;
    }

    public String getName() {
        return this.name;
    }

    public DevelopmentCardColor getColor () { return this.color; }

    public EffectInterface getPermanentBonus() { return this.permanentBonus; }

}


