package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Enum.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by vladc on 5/13/2017.
 */
public class DevelopmentCardBonus implements EffectInterface {
    private DevelopmentCardColor color;
    private Integer value;
    private Resources requirementsDiscount;

    /**
     * applied only if value is not null
     * if true - the value is applied when the user goes on a tower action space.
     * else - the user can go to a tower action space without using the family member
     */
    private Boolean relative;

    public DevelopmentCardBonus(DevelopmentCardColor color, Integer value, Boolean relative) {
        this.color = color;
        this.value = value;
        this.requirementsDiscount = null;
        this.relative = relative;
    }

    public DevelopmentCardColor(DevelopmentCardColor color, Resources requirementsDiscount) {
        this.color = color;
        this.value = null;
        this.requirementsDiscount = requirementsDiscount;
        this.relative = false;
    }

    public DevelopmentCardColor getDevelopmentCardColor() {
        return this.color;
    }

    public Integer getValue() {
        return this.value;
    }

    public Resources getRequirementsDiscount() {
        return this.requirementsDiscount;
    }

    public Boolean isRelative() {
        return this.relative;
    }
}
