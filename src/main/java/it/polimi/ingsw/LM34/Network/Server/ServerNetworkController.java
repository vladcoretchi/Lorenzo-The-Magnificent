package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class ServerNetworkController {
    private AbstractConnection serverConnection;
    private GameRoom gameRoom;

    public ServerNetworkController(AbstractConnection connection) {
        this.serverConnection = connection;
        this.gameRoom = Server.addPlayerToGameRoom(connection.getUsername(), this);
    }


    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        this.serverConnection.setExcommunicationCards(excommunicationCards);
    }

    public void updateTowers(List<Tower> towers) {
        this.serverConnection.updateTowers(towers);
    }

    public void updateCouncilPalace(CouncilPalace councilPalace){
        this.serverConnection.updateCouncilPalace(councilPalace);
    }

    public void updateMarket(Market market) {
        this.serverConnection.updateMarket(market);
    }

    public void updateProductionArea(WorkingArea productionArea) {
        this.serverConnection.updateProductionArea(productionArea);
    }

    public void updateHarvestArea(WorkingArea harvestArea) {
        this.serverConnection.updateHarvestArea(harvestArea);
    }

    public void updatePlayersData(List<Player> players) {
        this.serverConnection.updatePlayersData(players);
    }

    public void updateDiceValues(List<Dice> diceValues) {
        this.serverConnection.updateDiceValues(diceValues);
    }


    public PlayerAction turnMainAction(Optional<Boolean> lastActionValid) {
        return this.serverConnection.turnMainAction(lastActionValid);
    }

    public PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid) {
        return  this.serverConnection.turnSecondaryAction(lastActionValid);
    }


    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        return this.serverConnection.familyMemberSelection(familyMembers);
    }

    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        return this.serverConnection.servantsSelection(servantsAvailable, minimumServantsRequested);
    }

    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        return this.serverConnection.resourceExchangeSelection(choices);
    }

    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
        return this.serverConnection.leaderCardSelection(leaderCards);
    }

    public Boolean churchSupport() {
        return this.serverConnection.churchSupport();
    }

    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        return this.serverConnection.selectCouncilPrivilegeBonus(availableBonuses);
    }
}
