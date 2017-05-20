package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class DevelopmentCardAcquireContext extends AbstractGameContext {
    //TODO: use a temporary dice value instead of modifying the real dice value stored in the game manager
    //TODO: handle Filippo Brunelleschi, Cesare Borgia

    @Override
    public void initContext() {
        setChanged();
        notifyObservers(ContextStatus.ENTERED);
    }

    @Override
    public ContextType getType() {
        return ContextType.DEVELOPMENT_CARD_ACQUIRE_CONTEXT;
    }

    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);

        //applyInstantEffect();
    }
}
