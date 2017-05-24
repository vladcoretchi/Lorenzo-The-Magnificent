package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

/**
 * Created by GiulioComi on 23/05/2017.
 */
public class ResourchExchangeContext extends AbstractGameContext {

    @Override
    public void initContext() {

    }

    @Override
    public ContextType getType() {
        return ContextType.RESOURCE_EXCHANGE_CONTEXT;
    }

    @Override
    public void endContext() {
        //phaseContext.interactWithPlayer();
    }
}
