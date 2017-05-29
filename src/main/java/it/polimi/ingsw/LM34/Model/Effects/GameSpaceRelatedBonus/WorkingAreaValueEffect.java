package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 */

//TODO: evaluate to 'merge' in FamilyMemberValueEffect observer class

public class WorkingAreaValueEffect extends AbstractEffect implements Observer {
    private Player player;
    private ContextType areaType; //PRODUCTION_CONTEXT OR HARVEST_CONTEXT
    private Integer diceValue;
    private Boolean isInstant; //TODO: "sforza", "da vinci"


    /**
     *The additional value on dice is absolute or relative depending on the card effects
     */
    private Boolean isRelative;

    public WorkingAreaValueEffect(Player player, Boolean isInstant, ContextType areaType, Integer value, Boolean relative) {
        this.player = player;
        this.isInstant = isInstant;
        this.areaType = areaType;
        this.diceValue = value;
        this.isRelative = relative;
    }

    public ContextType getType() {
        return this.areaType;
    }

    public Integer getValue() {
        return this.diceValue;
    }

    public Boolean isRelative() {
        return this.isRelative;
    }

    @Override
    public void update(Observable o, Object arg) {

        AbstractGameContext gameContext = (AbstractGameContext) o;
        ContextType currentContext = gameContext.getType();
        FamilyMember familyMember = (FamilyMember) arg;

        if(areaType == currentContext)
            //TODO: get dice value from family member currently selected and apply bonus;
            // TODO: remember to reset the dice value powered before exiting this context


            if(isRelative)
                familyMember.setValue(diceValue + familyMember.getValue());
            else
                familyMember.setValue(diceValue);

            //Utilities.getContextByType(areaType).continue(diceContextValue);






    }

    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts)  {
        Utilities.getContextByType(contexts, areaType).addObserver(this);

    }

    //TODO: remove nulls
    @Override
    public void applyEffect(Player player)  {
        if(isInstant) //check if this is instant effect
        Utilities.getContextByType(null, areaType).interactWithPlayer(player);
        else //register the permanent bonus as observer
        subscribeObserverToContext(null);
    }
}
