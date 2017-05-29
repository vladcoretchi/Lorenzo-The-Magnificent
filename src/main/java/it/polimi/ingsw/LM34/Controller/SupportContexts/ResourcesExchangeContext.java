package it.polimi.ingsw.LM34.Controller.SupportContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by GiulioComi on 23/05/2017.
 */

/**
 *Building permanent effects make the player access
 *this contexts so that he can chooses what option he want to activate
 */
public class ResourcesExchangeContext extends AbstractGameContext {
    private Pair<Resources, Resources>[] resourceExchange;
    private Pair<Resources, Integer>[] resourceForPrivileges;


    public ResourcesExchangeContext() {}

    @Override
    public ContextType getType() {
        return ContextType.RESOURCE_EXCHANGE_CONTEXT;
    }


    public void setBonuses(Player player, Pair<Resources, Resources>[] resourceExchange, Pair<Resources, Integer>[] resourceForPrivileges)  {
        this.resourceExchange = resourceExchange;
        this.resourceForPrivileges = resourceForPrivileges;

        interactWithPlayer(player);
    }
    @Override
    public void  interactWithPlayer(Player player) {
        //TODO: let the player choose the options...
        System.out.println("Scegli una delle due alternative ");
    }
}
