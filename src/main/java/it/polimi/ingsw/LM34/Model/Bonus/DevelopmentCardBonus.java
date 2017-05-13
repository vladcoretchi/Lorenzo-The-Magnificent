package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;

/**
 * Created by vladc on 5/13/2017.
 */
public class DevelopmentCardBonus implements BonusInterface {
    private DevelopmentCardColor color;
    private Integer value;

    public DevelopmentCardBonus(DevelopmentCardColor color, Integer value) {
        this.color = color;
        this.value = value;
    }

    public DevelopmentCardColor getDevelopmentCardColor() {
        return this.color;
    }

    public Integer getValue() {
        return value;
    }
}
