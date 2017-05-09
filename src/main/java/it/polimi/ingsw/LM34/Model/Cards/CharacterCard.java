package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class CharacterCard implements DevelopmentCardInterface {
    private String characterName;
    private DevelopmentCardColor color= DevelopmentCardColor.BLUE;
    private Integer coinsRequired;
    private Integer period;
    private PermanentBonus permanentBonus;

    public CharacterCard(String characterName,Integer period, Integer coinsRequired, PermanentBonus instantBonus, PermanentBonus permanentBonus)  {
        this.coinsRequired= coinsRequired;
        this.characterName= characterName;
        this.period= period;
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
    public PermanentBonus getPermanentBonus() {
        return this.permanentBonus;
    }


    //TODO: evaluate if this is useful
    public String toString() {
        return "characterCard";
    }
}
