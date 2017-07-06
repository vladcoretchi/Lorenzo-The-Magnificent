package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.ActionSlotContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.HarvestAreaContext;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;

public class ActionSlotLimitBypass extends AbstractEffect implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        ActionSlotContext callerContext = (ActionSlotContext) arg;
        String referenceContextType = callerContext.getReferenceContext().getType().name();

        if(referenceContextType.equals(MARKET_AREA_CONTEXT.name()) ||
                referenceContextType.equals(PRODUCTION_AREA_CONTEXT.name()) ||
                referenceContextType.equals(HARVEST_AREA_CONTEXT.name()))
            callerContext.ignoreSlotLimit();
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(ACTION_SLOT_CONTEXT).addObserver(this);
    }
}
