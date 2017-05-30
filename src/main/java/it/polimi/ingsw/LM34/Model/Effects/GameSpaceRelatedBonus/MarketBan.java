package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.MARKET_AREA_CONTEXT;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class MarketBan extends AbstractEffect implements Observer {

public MarketBan() {

    //this.observableContexts = new ArrayList<>();
}
    @Override
    public void applyEffect(AbstractGameContext callerContext, Player player) {
     callerContext.getContextByType(MARKET_AREA_CONTEXT).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        //Disable the possibility to the player to place a pawn in a market slot
        //((MarketAreaContext) o).setBan();
        System.out.println("Market Ban a rapporto!");

    }

  //market

}
