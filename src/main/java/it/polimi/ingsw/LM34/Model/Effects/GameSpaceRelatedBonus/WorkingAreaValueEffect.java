package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;
import java.util.List;
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
    //TODO: "sforza", "da vinci"


    /**
     *The additional value on dice is absolute or relative depending on the card effects
     */
    private Boolean isRelative;

    public WorkingAreaValueEffect(Player player, ContextType areaType, Integer value, Boolean relative) {
        /*this.observableContexts = new ArrayList<>();
        observableContexts.add(areaType);*/
        this.player = player;
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
        List<FamilyMember> pawns = (ArrayList<FamilyMember>) arg;

        pawns.forEach(p -> p.setValue(diceValue + (isRelative ? p.getValue() : 0)));
    }


    @Override
    public void applyEffect(AbstractGameContext callerContext)  {
        //TODO: handle "cardinale"
        //register the permanent bonus as observer
        callerContext.getContextByType(areaType).addObserver(this);
        //if(isInstant) //check if this is instant effect
        callerContext.getContextByType(areaType).interactWithPlayer();

    }
}
