package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class DevelopmentCardAcquireContext extends AbstractGameContext {

    public void initContext(Player player) {

    }

    @Override
    public ContextType getType() {
        return ContextType.DEVELOPMENT_CARD_ACQUIRE_CONTEXT;
    }

    @Override
    public void endContext() {

    }
}
