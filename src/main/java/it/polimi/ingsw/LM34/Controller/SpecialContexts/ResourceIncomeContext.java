package it.polimi.ingsw.LM34.Controller.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 16/05/2017.
 */

//This context does not provide an interaction with the player but it is important
// for the effects that are applied when a player receives resources
public class ResourceIncomeContext extends AbstractGameContext {
    //TODO: handle santa rita here or in towerscontext?

    @Override
    public ContextType getType() {
        return ContextType.RESOURCE_INCOME_CONTEXT;
    }


    public void handleResources(Player player, Resources resources) {
        setChanged();
        notifyObservers(resources);
        //here the resources have already been changed by malus and bonus observers
        player.addResources(resources);

    }
}
