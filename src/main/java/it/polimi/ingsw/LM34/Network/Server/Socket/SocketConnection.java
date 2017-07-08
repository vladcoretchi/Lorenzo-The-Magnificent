package it.polimi.ingsw.LM34.Network.Server.Socket;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
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
import it.polimi.ingsw.LM34.Network.Client.Socket.RequestToClient;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Network.Server.AbstractConnection;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import sun.nio.ch.Net;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class SocketConnection extends AbstractConnection implements Runnable {
    private boolean runListener;
    private SocketServer socketServer;

    private final Socket connectionSocket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;

    public SocketConnection(Socket socket, SocketServer socketServer) throws IOException {
        this.connectionSocket = socket;
        this.outStream = new ObjectOutputStream(new BufferedOutputStream(connectionSocket.getOutputStream()));
        this.outStream.flush();
        this.inStream = new ObjectInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
        this.runListener = true;
        this.socketServer = socketServer;
    }

    public Socket getSocket() {
        return connectionSocket;
    }

    public ObjectInputStream getInputStream() {
        return inStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outStream;
    }

    @Override
    public void run() {
        while (runListener) {
            try {
                String request = this.inStream.readUTF();

                RequestToServer.valueOf(request).readAndHandle(this);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
                this.terminateListener();
            }
        }
    }

    public void terminate() {
        this.runListener = false;
        closeConnections();
    }

    private void terminateListener() {
        this.runListener = false;
    }

    private void closeConnections() {
        try {
            this.inStream.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        try {
            this.outStream.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        try {
            this.connectionSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void connectionExceptionHandler(Exception ex) {
        LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        this.terminate();
        this.socketServer.removeClosedConnection(this);
    }

    @Override
    public boolean login(String username, String password) {
        boolean result = super.login(username, password);
        if (result)
            this.terminateListener();
        return result;
    }

    @Override
    public void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.LOAD_MAP_TERRITORIES_TO_VICTORY_POINTS.name());
            this.outStream.writeObject(mapTerritoriesToVictoryPoints);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapMilitaryPointsForTerritories) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.LOAD_MAP_MILITARY_POINTS_FOR_TERRITORIES.name());
            this.outStream.writeObject(mapMilitaryPointsForTerritories);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.LOAD_MAP_CHARACTERS_TO_VICTORY_POINTS.name());
            this.outStream.writeObject(mapCharactersToVictoryPoints);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void loadFaithPath(Map<Integer, Integer> faithPath) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.LOAD_FAITH_PATH.name());
            this.outStream.writeObject(faithPath);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.SET_EXCOMMUNICATION_CARDS.name());
            this.outStream.writeObject(excommunicationCards);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateTowers(List<Tower> towers) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.UPDATE_TOWERS.name());
            this.outStream.writeObject(towers);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateCouncilPalace(CouncilPalace councilPalace) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.UPDATE_COUNCIL_PALACE.name());
            this.outStream.writeObject(councilPalace);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateMarket(Market market) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.UPDATE_MARKET.name());
            this.outStream.writeObject(market);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateProductionArea(WorkingArea productionArea) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.UPDATE_PRODUCTION_AREA.name());
            this.outStream.writeObject(productionArea);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateHarvestArea(WorkingArea harvestArea) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.UPDATE_HARVEST_AREA.name());
            this.outStream.writeObject(harvestArea);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updatePlayersData(List<Player> players) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.UPDATE_PLAYERS_DATA.name());
            this.outStream.writeObject(players);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateDiceValues(List<Dice> diceValues) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.UPDATE_DICE_VALUES.name());
            this.outStream.writeObject(diceValues);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void startGame() throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.START_GAME.name());
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public PlayerAction turnMainAction(Optional<Exception> lastActionValid) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.TURN_MAIN_ACTION.name());
            this.outStream.writeObject(lastActionValid.orElse(null));
            this.outStream.flush();
            return (PlayerAction) this.inStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.TURN_SECONDARY_ACTION.name());
            this.outStream.writeObject(lastActionValid.orElse(null));
            this.outStream.flush();
            return (PlayerAction) this.inStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.FAMILY_MEMBER_SELECTION.name());
            this.outStream.writeObject(familyMembers);
            this.outStream.flush();
            return this.inStream.readInt();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer bonusTileSelection(List<BonusTile> bonusTiles) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.BONUS_TILE_SELECTION.name());
            this.outStream.writeObject(bonusTiles);
            this.outStream.flush();
            return this.inStream.readInt();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.SERVANTS_SELECTION.name());
            this.outStream.writeInt(servantsAvailable);
            this.outStream.writeInt(minimumServantsRequested);
            this.outStream.flush();
            return this.inStream.readInt();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) throws NetworkConnectionException {
        try {
            this.outStream.writeUTF(RequestToClient.RESOURCE_EXCHANGE_SELECTION.name());
            this.outStream.writeObject(choices);
            this.outStream.flush();
            return this.inStream.readInt();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.LEADER_CARD_SELECTION.name());
            this.outStream.writeObject(leaderCards);
            this.outStream.flush();
            Pair<?, ?> pairObject = (Pair<?, ?>) this.inStream.readObject();
            return new ImmutablePair<>((String) pairObject.getLeft(), (LeaderCardsAction) pairObject.getRight());
        } catch (IOException | ClassNotFoundException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Boolean churchSupport() throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.CHURCH_SUPPORT.name());
            this.outStream.flush();
            return this.inStream.readBoolean();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.COUNCIL_PRIVILEGE_BONUS_SELECTION.name());
            this.outStream.writeObject(availableBonuses);
            this.outStream.flush();
            return this.inStream.readInt();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.LEADER_CARD_SELECTION_PHASE.name());
            this.outStream.writeObject(leaderCards);
            this.outStream.flush();
            return this.inStream.readInt();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Boolean alternativeRequirementsPayment() throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.ALTERNATIVE_REQUIREMENTS_PAYMENT.name());
            this.outStream.flush();
            return this.inStream.readBoolean();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void endGame(List<Player> players) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.END_GAME.name());
            this.outStream.writeObject(players);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void endTurn() throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.END_TURN.name());
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.INFORM_IN_GAME_PLAYERS.name());
            this.outStream.writeObject(infoType);
            this.outStream.writeUTF(playerName);
            this.outStream.writeObject(playerColor);
            this.outStream.flush();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public PlayerAction freeAction(PlayerAction availableAction, Optional<Exception> lastActionValid) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.FREE_ACTION.name());
            this.outStream.writeObject(availableAction);
            this.outStream.writeObject(lastActionValid.orElse(null));
            this.outStream.flush();
            return (PlayerAction) this.inStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer leaderCardCopy(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        try {
            this.outStream.reset();

            this.outStream.writeUTF(RequestToClient.LEADER_CARD_COPY.name());
            this.outStream.writeObject(leaderCards);
            this.outStream.flush();
            return this.inStream.readInt();
        } catch (IOException e) {
            connectionExceptionHandler(e);
            throw new NetworkConnectionException();
        }
    }
}
