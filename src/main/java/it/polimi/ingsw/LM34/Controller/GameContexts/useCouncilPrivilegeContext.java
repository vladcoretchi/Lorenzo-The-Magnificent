package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 23/05/2017.
 */
public class useCouncilPrivilegeContext extends AbstractGameContext implements Observer {
    private Player player;
    private Integer numberOfCouncilePrivileges;


    public void initContext(Player player) {
        numberOfCouncilePrivileges = player.getCouncilPrivileges();
    }

    @Override
    public ContextType getType() {
        return ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT;
    }

    public void interactWithPlayer(Integer numberOfCouncilePrivileges) {
        //TODO: handle the player choice on how to use the privileges
        for(Integer used=0; used<numberOfCouncilePrivileges; used++)
            //TODO: let the player choice a resourcebonus, but different from the other choosed at the same moment
            player.addResources(new Resources(2,0,0,0));
    }


    public void update(Observable o, Player player) {
        AbstractGameContext callerContext = (AbstractGameContext) o;
        switch (callerContext.getType()) {
            case LEADER_DISCARD_CONTEXT:
                numberOfCouncilePrivileges = ((LeaderDiscardContext) callerContext).getTotalLeadersDiscarded();
                //TODO
                // break;
            case ACTION_SLOT_CONTEXT:
                //TODO
                break;
            case DEVELOPMENT_CARD_ACQUIRE_CONTEXT:
                //TODO
                break;
            default:
                break;
        }
    }

    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(contexts, ContextType.DEVELOPMENT_CARD_ACQUIRE_CONTEXT).addObserver(this);
        Utilities.getContextByType(contexts, ContextType.ACTION_SLOT_CONTEXT).addObserver(this);
        Utilities.getContextByType(contexts, ContextType.LEADER_DISCARD_CONTEXT).addObserver(this);


    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
