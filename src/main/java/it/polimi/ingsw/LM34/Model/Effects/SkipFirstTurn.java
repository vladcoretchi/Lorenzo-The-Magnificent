package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/14/2017.
 */


public class SkipFirstTurn extends AbstractEffect implements Observer {
    private Boolean skipFirstTurn;

    public SkipFirstTurn(Boolean skipFirstTurn) {
        this.skipFirstTurn = skipFirstTurn;
    }

    public Boolean hasToSkipFirstTurn() {
        return this.skipFirstTurn;
    }


    @Override
    public void update(Observable o, Object arg) {
    }

    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) throws NoSuchContextException {
        Utilities.getContextByType(contexts, ContextType.PHASE_CONTEXT).addObserver(this);
    }
}