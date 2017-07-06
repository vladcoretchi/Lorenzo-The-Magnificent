package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.TowersContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.CardTypeNumLimitReachedException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughMilitaryPoints;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughResourcesException;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class DevelopmentCardAcquireEffect extends AbstractEffect implements Observer {
    private static final long serialVersionUID = 9154894195925064951L;

    private DevelopmentCardColor color;
    private Integer value;
    private ResourcesBonus requirementsDiscount;

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

    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        //TODO
        TowersContext towerContext = (TowersContext) callerContext.getContextByType(ContextType.TOWERS_CONTEXT);
        towerContext.addObserver(this);

        if(requirementsDiscount != null)
            towerContext.addObserver(this.requirementsDiscount);


        try {
            towerContext.interactWithPlayer();
        } catch(IncorrectInputException | NotEnoughMilitaryPoints | OccupiedSlotException | NotEnoughResourcesException | CardTypeNumLimitReachedException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }
        towerContext.deleteObserver(this);
    }
}
