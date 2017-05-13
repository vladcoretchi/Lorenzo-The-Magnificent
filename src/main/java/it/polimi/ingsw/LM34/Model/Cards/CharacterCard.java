package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;
import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 04/05/2017.
 */
public class CharacterCard extends DevelopmentCardInterface {
    private String characterName;
    private DevelopmentCardColor color= DevelopmentCardColor.BLUE;
    private Integer coinsRequired;
    private Integer period;
    private PermanentBonus permanentBonus;

    //cortigiana, araldo, nobile, governatore
    private DevelopmentCardColor victoryPointsRewardCardColor;

    public CharacterCard(String characterName, Integer period, Integer coinsRequired, PermanentBonus permanentBonus)  {
        this.coinsRequired= coinsRequired;
        this.characterName= characterName;
        this.period= period;
        this.permanentBonus= permanentBonus;
    }

    public CharacterCard(String characterName,Integer phase, Integer coinsRequired, PermanentBonus permanentBonus, DevelopmentCardColor developmentCardColor) {
        this(characterName, phase, coinsRequired, permanentBonus);
        this.victoryPointsRewardCardColor = developmentCardColor;
    }
    @Override
    public Resources getResourcesRequired () {
        return new Resources(this.coinsRequired, 0, 0, 0);
    }

    @Override
    public Integer getPhase() {
        return this.phase;
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
