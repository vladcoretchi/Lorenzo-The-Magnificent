package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDevelopmentCard implements Serializable {
    protected String name;
    protected Integer period;
    protected DevelopmentCardColor color;
    protected AbstractEffect permanentBonus;
    protected List<AbstractEffect> instantBonus;
    protected Resources resourceRequired;

    public Resources getResourcesRequired() { return this.resourceRequired; }

    public Integer getPeriod() {
        return this.period;
    }

    public String getName() {
        return this.name;
    }

    public DevelopmentCardColor getColor () { return this.color; }

    public List<AbstractEffect> getInstantBonus() { return this.instantBonus; }

    public AbstractEffect getPermanentBonus() { return this.permanentBonus; }

}


