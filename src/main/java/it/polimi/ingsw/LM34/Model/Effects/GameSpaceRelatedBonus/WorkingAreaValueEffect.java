package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.*;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.FAMILY_MEMBER_SELECTION_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class WorkingAreaValueEffect extends AbstractEffect implements Observer {
    private ContextType influenceableContext; //PRODUCTION_CONTEXT OR HARVEST_CONTEXT
    private Integer diceValue;

    /**
     *The additional value on dice is absolute or relative depending on the card effects
     */
    private Boolean isRelative;

    public WorkingAreaValueEffect(ContextType influenceableContext, Integer value, Boolean relative) {
        this.influenceableContext = influenceableContext;
        this.diceValue = value;
        this.isRelative = relative;
    }

    public ContextType getType() {
        return this.influenceableContext;
    }

    public Integer getValue() {
        return this.diceValue;
    }

    public Boolean isRelative() {
        return this.isRelative;
    }

    @Override
    public void update(Observable o, Object arg) {
        FamilyMemberSelectionContext callerContext = (FamilyMemberSelectionContext) arg;
        if(callerContext.getCurrentActionContext() == influenceableContext)
            callerContext.changeFamilyMemberValue(this.diceValue, this.isRelative);
    }


    @Override
    public void applyEffect(AbstractGameContext callerContext)  {
        if(this.isRelative)
            callerContext.getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT).addObserver(this);
        else //TODO (card instant effect - free harvest/production)
            try {
                callerContext.getContextByType(influenceableContext).interactWithPlayer();
            } catch (IncorrectInputException |
                    MarketBanException |
                    OccupiedSlotException |
                    NotEnoughResourcesException |
                    NotEnoughMilitaryPoints |
                    CardTypeNumLimitReachedException |
                    InvalidLeaderCardAction |
                    NoMoreLeaderCardsAvailable ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            }
    }
}
