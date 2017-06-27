package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.ACTION_SLOT_CONTEXT;

public class ActionSlotLimitBypass extends AbstractEffect implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(ACTION_SLOT_CONTEXT).addObserver(this);
        /*@Override
        public void applyEffect(AbstractGameContext callerContext, Player player) {
            callerContext.getContextByType(MARKET_AREA_CONTEXT).addObserver(this);
            callerContext.getContextByType(TOWERS_CONTEXT).addObserver(this);
            callerContext.getContextByType(PRODUCTION_AREA_CONTEXT).addObserver(this);
            callerContext.getContextByType(HARVEST_AREA_CONTEXT).addObserver(this);
            callerContext.getContextByType(COUNCIL_PALACE_CONTEXT).addObserver(this);
        }*/
    }
}
