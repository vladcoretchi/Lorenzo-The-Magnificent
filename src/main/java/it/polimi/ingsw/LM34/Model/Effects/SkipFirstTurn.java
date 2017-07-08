package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.TurnContext;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TURN_CONTEXT;

public class SkipFirstTurn extends AbstractEffect implements Observer {
    private static final long serialVersionUID = -4964914894768670L;
    private Boolean used;
    public SkipFirstTurn() {
        this.used = false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!this.used) {
            TurnContext turnContext = (TurnContext) arg;
            turnContext.skipTurn();
            setUsed(true);
        }
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(TURN_CONTEXT).addObserver(this);
    }

    public void setUsed(Boolean isUsed) {
        this.used = isUsed;
    }
}