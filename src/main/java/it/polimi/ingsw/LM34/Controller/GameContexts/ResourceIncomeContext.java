package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

/**
 * Created by GiulioComi on 16/05/2017.
 */

//TODO: this is also the context when a player decides to receive resources for the council privileges
public class ResourceIncomeContext extends AbstractGameContext {

    //TODO: handle santa rita here
    @Override
    public void initContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }


    @Override
    public ContextType getType() {
        return ContextType.RESOURCE_INCOME_CONTEXT;
    }

    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }
}
