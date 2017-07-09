package it.polimi.ingsw.LM34.Network;

import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Network.Server.Server;
import it.polimi.ingsw.LM34.Network.Server.ServerNetworkController;
import it.polimi.ingsw.LM34.Utils.Configurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class GameRoom {
    private GameManager gameManager;
    private Map<String, ServerNetworkController> players;

    private WaitingRoomTimeout timeoutRunnable;
    private Thread timeoutThread;

    private Configurator configurator;

    public GameRoom() {
        this.gameManager = null;
        this.players = new HashMap<>();

        this.timeoutRunnable = null;
        this.timeoutThread = null;

        this.configurator = new Configurator();
    }

    public String[] getPlayers() {
        return this.players.keySet().toArray(new String[]{});
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public Configurator getConfigurator() {
        return this.configurator;
    }

    public void addPlayer(String username, ServerNetworkController networkController) {

        if (this.timeoutThread != null) {
            this.timeoutThread.interrupt();
            while (this.timeoutThread.isInterrupted());
        }

        this.players.put(username, networkController);

        if (this.players.size() >= this.configurator.getWaitingRoomPlayersThreshold()) {
            this.timeoutRunnable = new WaitingRoomTimeout(this.configurator.getWaitingRoomTimeout());
            timeoutThread = new Thread(this.timeoutRunnable);
            timeoutThread.start();
        }
        else if (this.players.size() == Configurator.MAX_PLAYERS) {
            this.timeoutRunnable = new WaitingRoomTimeout(0);
            timeoutThread = new Thread(this.timeoutRunnable);
            timeoutThread.start();
        }
    }

    public ServerNetworkController getPlayerNetworkController(String player) {
        return players.get(player);
    }

    public void start() {
        Server.startWaitingGame();

        List<String> playerNames = new ArrayList<>(this.players.keySet());

        this.gameManager = new GameManager(this, playerNames);
        this.gameManager.startGame();
    }

    public void end() {
        this.players.forEach((name, nc) -> nc.removeConnection());
        Server.removeGameRoom(this);
    }

    private class WaitingRoomTimeout implements Runnable {
        private final Integer timeout;

        public WaitingRoomTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        @Override
        public void run() {
            try {
                if (this.timeout > 0)
                    Thread.sleep(this.timeout);

                if (!Thread.currentThread().isInterrupted())
                    GameRoom.this.start();
            } catch (InterruptedException e) {
                LOGGER.log(Level.INFO, "Timeout");
                Thread.currentThread().interrupt();
            }
        }
    }
}


//TODO: handle disconnections, timer of turn, ecc