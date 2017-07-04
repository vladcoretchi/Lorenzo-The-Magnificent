package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.GameServer;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.Server.Server;
import it.polimi.ingsw.LM34.Network.Server.ServerNetworkController;
import it.polimi.ingsw.LM34.Network.Server.Socket.SocketConnection;
import it.polimi.ingsw.LM34.Utils.Configurator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameManagerTest {

    @BeforeClass
    public static void setUp() throws Exception {
        Configurator.loadConfigs();
        Configurator.getTerritoryCards();
        GameServer gameServer = new GameServer();
        new Thread(new Runnable() { public void run() { Server.getInstance(); gameServer.main(null);}}).start();

    }

    @Test
    public void startGame() throws Exception {
        List<String> players = new ArrayList<>();
        players.add("aldo");
        players.add("giovanni");
        Socket socket = new Socket("localhost", 20001);
        SocketConnection socketConnection = new SocketConnection(socket);
        ServerNetworkController serverNetworkController = new ServerNetworkController(socketConnection);
        Server.addPlayerToGameRoom("aldo", serverNetworkController);
        Server.startWaitingGame();
        Server.login("aldo", "ciccio");
        GameRoom gameRoom = new GameRoom();
        gameRoom.addPlayer("aldo", serverNetworkController);
        System.out.println(gameRoom.getPlayerNetworkController("aldo"));

        GameManager gameManager = new GameManager(gameRoom, players);
        gameManager.startGame();
    }

    @Test
    public void endGame() throws Exception {
        List<String> players = new ArrayList<>();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        GameManager gameManager = new GameManager(new GameRoom(), players);
        gameManager.endGame();
    }

    @Test
    public void nextTurn() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        gameManager.nextTurn();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.nextTurn();
    }

    @Test
    public void nextPhase() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        gameManager.nextPhase();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.nextPhase();
    }

    @Test
    public void nextRound() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.nextRound();
    }

    @Test
    public void nextPeriod() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.nextPeriod();
    }

    @Test
    public void replaceCards() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.replaceCards();
    }

    @Test
    public void sweepActionSlots() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.sweepActionSlots();
    }

    @Test
    public void setupPlayersResources() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.setupPlayersResources();
    }

    @Test
    public void setupGameContexts() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.setupGameContexts();
    }

    @Test
    public void bonusTileSelectionPhase() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.bonusTileSelectionPhase();
    }

    @Test
    public void leaderSelectionPhase() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.leaderSelectionPhase();
    }
}