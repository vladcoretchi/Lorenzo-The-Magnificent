package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Exception.Model.InvalidResourceTypeException;
import it.polimi.ingsw.LM34.Model.Bonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Enum.ResourceType;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class CharacterCard implements DevelopmentCardInterface {
    private String characterName;
    private DevelopmentCardColor color= DevelopmentCardColor.BLUE;
    private Integer coinsRequired;
    private Integer period;
    private Bonus instantBonus;
    private Bonus permanentBonus;
    private Integer diceValue;

    public CharacterCard(String characterName,Integer period, Integer coinsRequired, Bonus instantBonus, Bonus permanentBonus)  {
        this.coinsRequired= coinsRequired;
        this.characterName= characterName;
        this.period= period;
        this.instantBonus= instantBonus;
        this.permanentBonus= permanentBonus;
    }

    @Override
    public Resources getResourcesRequired () {
        return new Resources(this.coinsRequired, 0, 0, 0);
    }

    @Override
    public Integer getPeriod() {
        return this.period;
    }

    @Override
    public String getName() {
        return this.characterName;
    }

    @Override
    public Bonus getInstantBonus() {
        return instantBonus;
    }

    @Override
    public Bonus getPermanentBonus() {
        return this.permanentBonus;
    }

    @Override
    public Integer getDiceValueToActivateBonus() {
        return this.diceValue;
    }

    //TODO: evaluate if this is useful
    public String toString() {
        return "characterCard";
    }
}
