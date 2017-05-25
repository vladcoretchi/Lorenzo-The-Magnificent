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
        //TODO: handle the player discard
        setChanged();
        notifyObservers(totalLeadersDiscarded);
        //TODO: the useCouncilPrivilegeContext is registered as observer
    }


    public void setTotalLeadersDiscarded(Integer totalLeadersDiscarded) {
        this.totalLeadersDiscarded = totalLeadersDiscarded;
    }

    public Integer getTotalLeadersDiscarded() {
        return this.totalLeadersDiscarded;
    }
}
