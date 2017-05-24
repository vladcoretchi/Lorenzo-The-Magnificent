package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameContexts.MarketAreaContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class MarketBan extends AbstractEffect implements Observer {


    public void applyEffect() {
      // marketContext.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {
        //TODO: disable the possibility to the player to place a pawn in a market slot
        MarketAreaContext marketContext = (MarketAreaContext) o;
        marketContext.endContext();

    }

    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts)  {
        Utilities.getContextByType(contexts, ContextType.MARKET_AREA_CONTEXT).addObserver(this);
    }



}
