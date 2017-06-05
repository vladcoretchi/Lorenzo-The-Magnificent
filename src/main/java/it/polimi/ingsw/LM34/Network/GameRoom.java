package it.polimi.ingsw.LM34.Network;

import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Network.Server.Server;
import it.polimi.ingsw.LM34.Network.Server.ServerNetworkController;
import it.polimi.ingsw.LM34.Utils.Configurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by GiulioComi on 07/05/2017.
 */
public class GameRoom {
    private GameManager gameManager;
    private Map<String, ServerNetworkController> players;

    private WaitingRoomTimeout timeoutRunnable;
    private Thread timeoutThread;

    public GameRoom() {
        this.gameManager = null;
        this.players = new HashMap<>();

        this.timeoutRunnable = null;
        this.timeoutThread = null;
    }

    public void addPlayer(String username, ServerNetworkController networkController) {

        if (this.timeoutThread != null) {
            this.timeoutThread.interrupt();
            while (this.timeoutThread.isInterrupted());
        }

        this.players.put(username, networkController);

        if (this.players.size() >= Configurator.WAITING_ROOM_PLAYERS_THRESHOLD) {
            this.timeoutRunnable = new WaitingRoomTimeout(Configurator.WAITING_ROOM_TIMEOUT);
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

        List<String> playerNames = new ArrayList<>();
        this.players.forEach((n, c) -> playerNames.add(n));

        this.gameManager = new GameManager(this, playerNames);
        this.gameManager.run();
    }

    private class WaitingRoomTimeout implements Runnable {
        //private final boolean run;
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
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        /*public void terminate() {

        }*/
    }
}


//TODO: handle disconnections, timer of turn, ecc