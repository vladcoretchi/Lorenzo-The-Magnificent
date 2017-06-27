package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.TurnContext;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TURN_CONTEXT;

public class SkipFirstTurn extends AbstractEffect implements Observer {

    public SkipFirstTurn() {}

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof TurnContext) {
            TurnContext turnContext = (TurnContext) arg;
            turnContext.endContext();
            turnContext.deleteObserver(this);
        }

        /**
         * Unregister this observer because it is applicable once per round; it will be reactivated next round in the phase context
         */
        //Utilities.getContextByType(contexts, ContextType.TURN_CONTEXT).deleteObserver(this);
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(TURN_CONTEXT).addObserver(this);
    }

    @Override
    public boolean isOncePerRound() {
        return true;
    }
}