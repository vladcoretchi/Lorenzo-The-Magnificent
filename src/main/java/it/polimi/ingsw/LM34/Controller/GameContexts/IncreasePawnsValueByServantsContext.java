package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class IncreasePawnsValueByServantsContext extends AbstractGameContext {


    @Override
    public void initContext() {
        setChanged();
        notifyObservers(ContextStatus.ENTERED);
    }

    @Override
    public ContextType getType() {
        return ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
    }

    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }
}
