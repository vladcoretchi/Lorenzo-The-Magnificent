package it.polimi.ingsw.LM34.Controller.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 23/05/2017.
 */

/**
 *Building permanent effects make the player access
 *this contexts so that he can chooses what option he want to activate
 */
public class ResourchExchangeContext extends AbstractGameContext {


    @Override
    public ContextType getType() {
        return ContextType.RESOURCE_EXCHANGE_CONTEXT;
    }


    @Override
    public void  interactWithPlayer(Player player) {
        //TODO: let the player choose the options...
    }
}
