package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.GameRoom;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.HashMap;
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

    public Integer marketSlotSelection(Market market) {
        return serverConnection.marketSlotSelection(market);
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

    /**
     *Presentation to players of final points earned by each player
     * @param victoryPointsToPlayers that the UI would represents as a ranking
     */
    public void endGameResults(HashMap<Player, Integer> victoryPointsToPlayers) {
        //TODO
    }

    public Integer bonusTileSelection(List<BonusTile> bonusTiles) {
        return 0; //TODO
    }

    public Integer leaderSelection(List<LeaderCard> leaderCards) {
        return 0; //TODO
    }

    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        return 0; //TODO
    }

    public Integer servantsSelection(Integer servantsOwned) {
        return 0; //TODO
    }
}
