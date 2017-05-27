package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 23/05/2017.
 */

/*TODO: probably to be removed or implement Observer and register to bonuses of production*/
public class ResourchExchangeContext extends AbstractGameContext {


    @Override
    public ContextType getType() {
        return ContextType.RESOURCE_EXCHANGE_CONTEXT;
    }


    @Override
    public void  interactWithPlayer(Player player) {

        //exit normally to get back to the caller context
    }
}
