package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 25/05/2017.
 */
public class LeaderDiscardContext extends AbstractGameContext {
    private Integer totalLeadersDiscarded;


    @Override
    public ContextType getType() {
        return ContextType.LEADER_DISCARD_CONTEXT;
    }


    @Override
    public void interactWithPlayer(Player player) {
        totalLeadersDiscarded = 2;
        System.out.println("carte scartate "+ totalLeadersDiscarded);
        setChanged();
        notifyObservers(totalLeadersDiscarded);

        System.out.println("Ora siamo nel leader discard context con carte scartate "+totalLeadersDiscarded);
        //TODO: handle the player discards and count how many councilPrivileges he deserves (totalleaders = ...)
        //for(Integer i = 0; i<totalLeadersDiscarded; i++)
           // getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT).interactWithPlayer(player);
    }


    public void setTotalLeadersDiscarded(Integer passedByObserver) {
        this.totalLeadersDiscarded = passedByObserver;
    }
}
