package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.IncreasePawnsValueByServantsContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;

public class HalveServantsValue extends AbstractEffect implements Observer {

    public HalveServantsValue() {}

    @Override
    public void update(Observable o, Object arg) {
        IncreasePawnsValueByServantsContext callerContext = (IncreasePawnsValueByServantsContext) arg;
        callerContext.duplicateServantsRequirements();
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT).addObserver(this);
    }
}
