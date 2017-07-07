package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.MarketAreaContext;
import it.polimi.ingsw.LM34.Exceptions.Controller.MarketBanException;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.MARKET_AREA_CONTEXT;

public class MarketBan extends AbstractEffect implements Observer {
    private static final long serialVersionUID = -6001761259317118310L;

    public MarketBan() {}

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(MARKET_AREA_CONTEXT).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        MarketAreaContext callerContext = (MarketAreaContext) arg;
        callerContext.setBan();
    }
}
