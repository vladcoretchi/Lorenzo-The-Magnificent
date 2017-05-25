package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */

public class ResourceIncomeContext extends AbstractGameContext {

    //TODO: handle santa rita here


    @Override
    public void interactWithPlayer(Player player) {
        setChanged();
        notifyObservers();

        //turnContext.interactWithPlayer();
    }

    @Override
    public ContextType getType() {
        return ContextType.RESOURCE_INCOME_CONTEXT;
    }


}
