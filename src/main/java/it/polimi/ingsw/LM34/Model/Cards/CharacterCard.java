package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.List;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class CharacterCard extends AbstractDevelopmentCard {
    private Integer coinsRequired;


    public CharacterCard(String name, Integer period, Integer coinsRequired, List<AbstractEffect> instantBonus, AbstractEffect permanentBonus)  {
        this.coinsRequired = coinsRequired;
        color= DevelopmentCardColor.BLUE;
        this.name = name;
        this.period = period;
        this.instantBonus = instantBonus;
        this.permanentBonus = permanentBonus;
    }

    @Override
    public Resources getResourcesRequired () {
        return new Resources(this.coinsRequired, 0, 0, 0);
    }

    @Override
    public String toString() {
        return "characterCard";
    }

    public DevelopmentCardColor getColor() { return this.color; }

    public String getName() {
        return name;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public List<AbstractEffect> getInstantBonus() {
        return this.instantBonus;
    }

}
