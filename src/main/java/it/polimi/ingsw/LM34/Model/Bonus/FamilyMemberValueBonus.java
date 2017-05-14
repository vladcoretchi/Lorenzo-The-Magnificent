package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Enum.DiceColor;

/**
 * Created by vladc on 5/13/2017.
 */
public class FamilyMemberValueBonus implements EffectInterface {
    /**
     * if null then the value is related to the neutral family member
     */
    private DiceColor color;

    private Integer value;

    /**
     * indicates if the value is:
     *      absolute (substitutes the old value)
     *      relative (adds to old value)
     */
    private Boolean relative;

    public FamilyMemberValueBonus(DiceColor color, Integer value, Boolean relative) {
        this.color = color;
        this.value = value;
        this.relative = relative;
    }

    public DiceColor getPawnColor() {
        return this.color;
    }

    public Integer getValue() {
        return this.value;
    }

    public Boolean isRelative() {
        return this.relative;
    }
}
