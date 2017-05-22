package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 */

public class DevelopmentCardAcquireBonus extends AbstractEffect implements Observer {
    private DevelopmentCardColor color;  //TODO: initial attempt to handle more bonuses of this type in one instance only
    private Integer value;
    private Resources requirementsDiscount;


    //TODO: pico della mirandola, filippo brunelleschi, santa rita
    /**
     * applied only if value is not null
     * if true - the value is applied when the user goes on a tower action space.
     * else - the user can go to a tower action space without using the family member
     */
    private Boolean relative;

    public DevelopmentCardAcquireBonus(DevelopmentCardColor color, Integer value, Boolean relative) {
        this.color = color;
        this.value = value;
        this.requirementsDiscount = null;
        this.relative = relative;
    }

    public DevelopmentCardAcquireBonus(DevelopmentCardColor color, Integer value, Resources requirementsDiscount) {
        this.color = color;
        this.value = value;
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
        Player player = (Player) arg;

    }


    //TODO: reset to the value before this context started


    public DevelopmentCardAcquireBonus mergeInOneObserverInstance(DevelopmentCardAcquireBonus dcae) {

        //TODO: implement this method
        return null;
    }


    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts)  {
        Utilities.getContextByType(contexts, ContextType.DEVELOPMENT_CARD_ACQUIRE_CONTEXT).addObserver(this);
    }


    public void resetApplyFlag() {

    }



}
