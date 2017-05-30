package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Player;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.LEADER_ACTIVATE_OR_DISCARD_CONTEXT;

/**
 * Created by GiulioComi on 25/05/2017.
 */

/**
 * In this context the player can discard a leader in favor of a privilege or activate his ability
 * In the latter case, all requirements of the leader to activate are verified
 */
public class LeaderActivateOrDiscardContext extends AbstractGameContext {
    private Integer totalLeadersDiscarded;

public LeaderActivateOrDiscardContext() {
    contextType = LEADER_ACTIVATE_OR_DISCARD_CONTEXT;
}

    @Override
    public void interactWithPlayer(Player player) {
        totalLeadersDiscarded = 0; //default value at the start of this context
        /*Activate leader cards*/
        //TODO: check if the player can activate a leader, then register it to the right observer


        /* Discard leader cards*/
        totalLeadersDiscarded = 2;
        System.out.println("carte scartate "+ totalLeadersDiscarded);
        setChanged();
        notifyObservers(totalLeadersDiscarded);

        System.out.println("Ora siamo nel leader discard context con carte scartate "+totalLeadersDiscarded);
        //TODO: handle the player discards and count how many councilPrivileges he deserves (totalleaders = ...)
        //for(Integer i = 0; i<totalLeadersDiscarded; i++)
           // getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT).interactWithPlayer(player);
    }
}
