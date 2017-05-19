package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */

//TODO: this is also the context when a player decides to receive resources for the council privileges
public class ResourceIncomeContext extends AbstractGameContext {


    //TODO: handle Santa Rita here
    public void initContext(Player player) {

    }


    @Override
    public ContextType getType() {
        return ContextType.RESOURCE_INCOME_CONTEXT;
    }

    public void endContext() {

    }
}
