package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Enums.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 */
//TODO: rename this class or merge it in a new one called TowerBonus
public class DevelopmentCardAcquireEffect extends AbstractEffect implements Observer {
    private DevelopmentCardColor color;
    private Integer value;
    private Resources requirementsDiscount;

    /**
     * applied only if value is not null
     * if true - the value is applied when the user goes on a tower action space.
     * else - the user can go to a tower action space without using the family member
     */
    private Boolean relative;

    public DevelopmentCardAcquireEffect(DevelopmentCardColor color, Integer value, Boolean relative) {
        this.color = color;
        this.value = value;
        this.requirementsDiscount = null;
        this.relative = relative;
    }

    public DevelopmentCardAcquireEffect(DevelopmentCardColor color, Resources requirementsDiscount) {
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

    @Override
    public void update(Observable o, Object arg) {

    }
}
