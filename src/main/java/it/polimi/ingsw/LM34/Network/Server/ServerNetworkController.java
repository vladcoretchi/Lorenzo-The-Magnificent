package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.GameRoom;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladc on 5/28/2017.
 */
public class ServerNetworkController {
    private AbstractConnection serverConnection;
    private GameRoom gameRoom;

    public ServerNetworkController(AbstractConnection connection) {
        this.serverConnection = connection;
        this.gameRoom = Server.addPlayerToGameRoom(connection.getUsername(), this);
    }

    public Integer contextSelection(List<PlayerSelectableContexts> contexts) {
        return serverConnection.contextSelection(contexts);
    }

    public Integer actionSlotSelection(ArrayList<ActionSlot> actionSlots) {
        //TODO return serverConnection.actionSlotSelection(actionSlots);
        return 0;
    }

    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> resForResBonus) {
        //TODO: return option choosed return serverConnection.actionSlotSelection(resForRes, resForPriv);
        return 0;
    }

    //TODO: replay is yes or no from client
    public Boolean curchReportDecision() {
        return true; //TODO: implement this in UI
    }

    public Integer copyLeaderSelection(List<LeaderCard> allLeadersActivatedByOthers) {
        return 0; //TODO
    }

    public Integer privilegeRewardSelection(List<ResourcesBonus> rewardsForPrivilege) {
        return 0; //TODO
    }
}
