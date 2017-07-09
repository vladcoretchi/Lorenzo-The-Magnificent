package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.TurnContext;
import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Utils.Configurator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class ServerNetworkController {
    private AbstractConnection serverConnection;
    private GameRoom gameRoom;

    public ServerNetworkController(AbstractConnection connection) {
        this.serverConnection = connection;
        this.gameRoom = Server.addPlayerToGameRoom(connection.getUsername(), this);
    }

    public void removeConnection() {
        this.serverConnection.remove();
    }


    public void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints) throws NetworkConnectionException {
        this.serverConnection.loadMapTerritoriesToVictoryPoints(mapTerritoriesToVictoryPoints);
    }

    public void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapMilitaryPointsForTerritories) throws NetworkConnectionException {
        this.serverConnection.loadMapMilitaryPointsForTerritories(mapMilitaryPointsForTerritories);
    }

    public void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints) throws NetworkConnectionException {
        this.serverConnection.loadMapCharactersToVictoryPoints(mapCharactersToVictoryPoints);
    }

    public void loadFaithPath(Map<Integer, Integer> faithPath) throws NetworkConnectionException {
        this.serverConnection.loadFaithPath(faithPath);
    }

    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) throws NetworkConnectionException {
        this.serverConnection.setExcommunicationCards(excommunicationCards);
    }

    public void updateTowers(List<Tower> towers) throws NetworkConnectionException {
        this.serverConnection.updateTowers(towers);
    }

    public void updateCouncilPalace(CouncilPalace councilPalace) throws NetworkConnectionException{
        this.serverConnection.updateCouncilPalace(councilPalace);
    }

    public void updateMarket(Market market) throws NetworkConnectionException {
        this.serverConnection.updateMarket(market);
    }

    public void updateProductionArea(WorkingArea productionArea) throws NetworkConnectionException {
        this.serverConnection.updateProductionArea(productionArea);
    }

    public void updateHarvestArea(WorkingArea harvestArea) throws NetworkConnectionException {
        this.serverConnection.updateHarvestArea(harvestArea);
    }

    public void updatePlayersData(List<Player> players) throws NetworkConnectionException {
        this.serverConnection.updatePlayersData(players);
    }

    public void updateDiceValues(List<Dice> diceValues) throws NetworkConnectionException {
        this.serverConnection.updateDiceValues(diceValues);
    }

    public void startGame() throws NetworkConnectionException {
        this.serverConnection.startGame();
    }

    public PlayerAction turnMainAction(Optional<Exception> lastActionValid) throws NetworkConnectionException {
        return this.serverConnection.turnMainAction(lastActionValid);
    }

    public PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) throws NetworkConnectionException {
        return  this.serverConnection.turnSecondaryAction(lastActionValid);
    }


    public Integer familyMemberSelection(List<FamilyMember> familyMembers) throws NetworkConnectionException {
        return this.serverConnection.familyMemberSelection(familyMembers);
    }

    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) throws NetworkConnectionException {
        return this.serverConnection.servantsSelection(servantsAvailable, minimumServantsRequested);
    }

    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) throws NetworkConnectionException {
        return this.serverConnection.resourceExchangeSelection(choices);
    }

    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        return this.serverConnection.leaderCardSelection(leaderCards);
    }

    public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        return this.serverConnection.leaderCardSelectionPhase(leaderCards);
    }

    public Boolean churchSupport() throws NetworkConnectionException {
        return this.serverConnection.churchSupport();
    }

    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) throws NetworkConnectionException {
        return this.serverConnection.selectCouncilPrivilegeBonus(availableBonuses);
    }

    public Integer bonusTileSelection(List<BonusTile> bonusTiles) throws NetworkConnectionException {
        return this.serverConnection.bonusTileSelection(bonusTiles);
    }

    public Boolean alternativeRequirementsPayment() throws NetworkConnectionException {
        return this.serverConnection.alternativeRequirementsPayment();
    }

    public void endGame(List<Player> players) throws NetworkConnectionException {
        this.serverConnection.endGame(players);
    }

    public void endTurn() throws NetworkConnectionException {
        this.serverConnection.endTurn();
    }

    public void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor) throws NetworkConnectionException {
        this.serverConnection.informInGamePlayers(infoType, playerName, playerColor);
    }

    public PlayerAction freeAction(PlayerAction availableAction, Optional<Exception> lastActionValid) throws NetworkConnectionException {
        return this.serverConnection.freeAction(availableAction, lastActionValid);
    }

    public Integer leaderCardCopy(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        return this.serverConnection.leaderCardCopy(leaderCards);
    }
}
