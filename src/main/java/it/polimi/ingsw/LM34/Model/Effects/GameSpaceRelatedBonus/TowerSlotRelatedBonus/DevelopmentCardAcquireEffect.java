package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 */

public class DevelopmentCardAcquireEffect extends AbstractEffect implements Observer {
    private DevelopmentCardColor color;
    private Integer value;
    private ResourcesBonus requirementsDiscount; //TODO

    //TODO: pico della mirandola, filippo brunelleschi
    /**
     * applied only if value is not null
     * if true - the value is applied when the user goes on a tower action space.
     * else - the user can go to a tower action space without using the family member
     */
    private Boolean relative;

    public DevelopmentCardAcquireEffect(DevelopmentCardColor color, Integer value, Boolean relative) {
        this(color, value, relative, null);
    }

    public DevelopmentCardAcquireEffect(DevelopmentCardColor color, Integer value, Boolean relative, ResourcesBonus requirementsDiscount) {
        this.color = color;
        this.value = value;
        this.requirementsDiscount = requirementsDiscount;
        this.relative = relative;
    }

    public DevelopmentCardColor getDevelopmentCardColor() {
        return this.color;
    }

    public Integer getValue() {
        return this.value;
    }

    public ResourcesBonus getRequirementsDiscount() {
        return this.requirementsDiscount;
    }

    public Boolean isRelative() {
        return this.relative;
    }

    @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) arg;
        //get dice select by player, add value bonus and discounts
        //TODO
    }


    //TODO: reset to the value before this context started


//towers

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        AbstractGameContext towerContext = callerContext.getContextByType(ContextType.TOWERS_CONTEXT);
        towerContext.addObserver(this);

        //TODO:blablabla verifare pico della mirandola soddisfatto assieme
        if(requirementsDiscount != null)
            towerContext.addObserver(this.requirementsDiscount);

        towerContext.interactWithPlayer();
        towerContext.deleteObserver(this);
    }



}
