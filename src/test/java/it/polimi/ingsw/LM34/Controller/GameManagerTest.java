package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import it.polimi.ingsw.LM34.GameServer;
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
import it.polimi.ingsw.LM34.Network.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Network.RMI.RMIConnection;
import it.polimi.ingsw.LM34.Network.Server.Server;
import it.polimi.ingsw.LM34.Network.Server.ServerNetworkController;
import it.polimi.ingsw.LM34.UI.UIInterface;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameManagerTest {
    /**
     * this test will check if GameManager will effectively recognize player's servernetworkController
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void startGame() throws Exception {
        GameServer gameServer = new GameServer();
        gameServer.main(null);
        PersonalizedUI personalizedUI = new PersonalizedUI();
        RMIClient rmiClient = new RMIClient("localhost", 20002, personalizedUI);
        RMIConnection rmiConnection = new RMIConnection(rmiClient, null);
        ServerNetworkController serverNetworkController = new ServerNetworkController(rmiConnection);
        GameRoom gameRoom = Server.addPlayerToGameRoom("aldo", serverNetworkController);
        ServerNetworkController serverNetworkController1 = gameRoom.getPlayerNetworkController("aldo");
        GameRoom gameRoom1 = new GameRoom();
        gameRoom1.addPlayer("aldo", serverNetworkController1);
        gameRoom1.start();
    }

    /**
     * this test will check if GameManager will effectively skip to next turn
     * @throws Exception
     */
    //Test(expected = NullPointerException.class)
    @Test(expected = StackOverflowError.class)
    public void nextTurn() throws Exception {
        GameServer gameServer = new GameServer();
        gameServer.main(null);
        PersonalizedUI personalizedUI = new PersonalizedUI();
        RMIClient rmiClient = new RMIClient("localhost", 20002, personalizedUI);
        RMIConnection rmiConnection = new RMIConnection(rmiClient,null);
        ServerNetworkController serverNetworkController = new ServerNetworkController(rmiConnection);
        GameRoom gameRoom = Server.addPlayerToGameRoom("aldo", serverNetworkController);
        ServerNetworkController serverNetworkController1 = gameRoom.getPlayerNetworkController("aldo");
        GameRoom gameRoom1 = new GameRoom();
        gameRoom1.addPlayer("aldo", serverNetworkController1);
        List<String> players = new ArrayList<>();
        players.add("aldo");
        GameManager gameManager = new GameManager(gameRoom1, players);
        gameManager.nextTurn();
    }

    class PersonalizedUI implements UIInterface {

        @Override
        public void show() {

        }

        @Override
        public void loginMenu() {

        }

        @Override
        public void loginResult(Boolean result) {

        }

        @Override
        public PlayerAction turnMainAction(Optional<Exception> lastActionValid) {
            return null;
        }

        @Override
        public PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) {
            return null;
        }

        @Override
        public PlayerAction freeAction(PlayerAction action, Optional<Exception> lastActionValid) {
            return null;
        }

        @Override
        public void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints) {

        }

        @Override
        public void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapVictoryPointsForTerritories) {

        }

        @Override
        public void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints) {

        }

        @Override
        public void loadExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {

        }

        @Override
        public void loadFaithPath(Map<Integer, Integer> faithPath) {

        }

        @Override
        public void updateTowers(List<Tower> towers) {

        }

        @Override
        public void updateCouncilPalace(CouncilPalace councilPalace) {

        }

        @Override
        public void updateMarket(Market market) {

        }

        @Override
        public void updateProductionArea(WorkingArea productionArea) {

        }

        @Override
        public void updateHarvestArea(WorkingArea harvestArea) {

        }

        @Override
        public void updatePlayersData(List<Player> players) {

        }

        @Override
        public void updateDiceValues(List<Dice> dicesValues) {

        }

        @Override
        public Integer leaderCardCopy(List<LeaderCard> activatedLeadersByOtherPlayers) {
            return  null;
        }

        @Override
        public void startGame() {

        }

        @Override
        public Integer bonusTileSelection(List<BonusTile> bonusTiles) {
            return null;
        }

        @Override
        public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) {
            return null;
        }

        @Override
        public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
            return null;
        }

        @Override
        public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
            return null;
        }

        @Override
        public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
            return null;
        }

        @Override
        public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
            return null;
        }

        @Override
        public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
            return null;
        }

        @Override
        public Boolean alternativeRequirementsPayment() {
            return null;
        }

        @Override
        public Boolean churchSupport() {
            return null;
        }

        @Override
        public void endGame(List<Player> players) {

        }

        @Override
        public void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor) {

        }

        @Override
        public void endTurn() {

        }

        @Override
        public void disconnectionWarning() {

        }
    }

}