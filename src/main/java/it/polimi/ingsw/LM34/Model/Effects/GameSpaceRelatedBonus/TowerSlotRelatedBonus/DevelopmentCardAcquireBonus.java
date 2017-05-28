package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
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
    private Boolean isInstant;


    //TODO: pico della mirandola, filippo brunelleschi
    /**
     * applied only if value is not null
     * if true - the value is applied when the user goes on a tower action space.
     * else - the user can go to a tower action space without using the family member
     */
    private Boolean relative;

    public DevelopmentCardAcquireBonus(Boolean isInstant, DevelopmentCardColor color, Integer value, Boolean relative) {
        this.isInstant = isInstant;
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
        //get dice select by player, add value bonus and discounts
        //TODO
    }


    //TODO: reset to the value before this context started





    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts)  {
        Utilities.getContextByType(contexts, ContextType.TOWERS_CONTEXT).addObserver(this);
    }


    @Override
    public void applyEffect(Player player) {
        if(isInstant) {
            //subscribeObserverToContext(contexts);
            AbstractGameContext context = GameManager.getContextByType(ContextType.TOWERS_CONTEXT);
            context.interactWithPlayer(player);
            context.deleteObserver(this);
        }
        //else
           // subscribeObserverToContext(contexts);
    }



}
