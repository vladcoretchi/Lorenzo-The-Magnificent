package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Enum.WorkingAreaType;

/**
 * Created by vladc on 5/13/2017.
 */
public class WorkingAreaValueBonus implements EffectInterface {
    private WorkingAreaType areaType;
    private Integer diceValue;

    /**
     * if true - the value is applied when the user does a production or harvest action.
     * else - the user can do a production or harvest action without using the family member
     */
    private Boolean relative;

    public WorkingAreaValueBonus(WorkingAreaType areaType, Integer value, Boolean relative) {
        this.areaType = areaType;
        this.diceValue = value;
        this.relative = relative;
    }

    public WorkingAreaType getType() {
        return this.areaType;
    }

    public Integer getValue() {
        return this.diceValue;
    }

    public Boolean isRelative() {
        return this.relative;
    }
}
