package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

/**
 * Created by GiulioComi on 15/05/2017.
 */

//here all excomunication cards of III period are all notified to apply their malus
public class EndGameContext  extends AbstractGameContext {

    @Override
    public void initContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }

    @Override
    public ContextType getType() {
        return ContextType.END_GAME_CONTEXT;
    }

    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }

    }

