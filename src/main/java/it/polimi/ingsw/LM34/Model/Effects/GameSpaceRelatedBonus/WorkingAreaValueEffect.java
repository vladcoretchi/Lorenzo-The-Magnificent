package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.WorkingAreaType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
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
    private WorkingAreaType areaType;
    private Integer diceValue;

    /**
     * if true - the value is applied when the user does a production or harvest action.
     * else - the user can do a production or harvest action without using the family member
     */
    private Boolean relative;

    public WorkingAreaValueEffect(WorkingAreaType areaType, Integer value, Boolean relative) {
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

    @Override
    public void update(Observable o, Object arg) {

    }

    public void registerObserverToContext(ArrayList<AbstractGameContext> contexts) throws NoSuchContextException {
        ContextType contextType;

        if (areaType == WorkingAreaType.PRODUCTION)
            contextType = ContextType.PRODUCTION_AREA_CONTEXT;
        else
            contextType = ContextType.HARVEST_AREA_CONTEXT;

        Utilities.getContextByType(contexts, ContextType.PRODUCTION_AREA_CONTEXT).addObserver(this);
    }
}
