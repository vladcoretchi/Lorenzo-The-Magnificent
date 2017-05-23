package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 */

//TODO: evaluate to 'merge' in FamilyMemberValueEffect observer class

public class WorkingAreaValueEffect extends AbstractEffect implements Observer {
    private ContextType areaType;
    private Integer diceValue;
    private Boolean isActionFree; //TODO: "sforza", "da vinci"

    /**
     *The additional value on dice is absolute or relative depending on the cards
     */
    private Boolean relative;

    public WorkingAreaValueEffect(ContextType areaType, Integer value, Boolean relative) {
        this.areaType = areaType;
        this.diceValue = value;
        this.relative = relative;
    }

    public ContextType getType() {
        return this.areaType;
    }

    public Integer getValue() {
        return this.diceValue;
    }

    public Boolean isRelative() {
        return this.relative;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts)  {
        Utilities.getContextByType(contexts, areaType).addObserver(this);

    }
}
