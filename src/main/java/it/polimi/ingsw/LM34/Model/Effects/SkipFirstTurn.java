package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.TurnContext;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TURN_CONTEXT;

public class SkipFirstTurn extends AbstractOncePerRoundEffect implements Observer {

    public SkipFirstTurn() {}

    @Override
    public void update(Observable o, Object arg) {
        if(!this.used) {
            TurnContext turnContext = (TurnContext) arg;
            turnContext.skipTurn();
            this.used = true;
        }
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
       // if(!this.used)
            callerContext.getContextByType(TURN_CONTEXT).addObserver(this);
    }

    @Override
    public boolean isOncePerRound() {
        return true;
    }
}