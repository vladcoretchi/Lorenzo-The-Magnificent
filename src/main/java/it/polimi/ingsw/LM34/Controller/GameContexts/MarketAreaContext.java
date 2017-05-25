package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 24/05/2017.
 */
public class MarketAreaContext extends AbstractGameContext {

    @Override
    public ContextType getType() {
        return ContextType.MARKET_AREA_CONTEXT;
    }


    @Override
    public void interactWithPlayer(Player player) {

        //TODO
        //turnContext.interactWithPlayer();
    }

    public void endContext() {
        //turnContext.interactWithPlayer();
    }
}
