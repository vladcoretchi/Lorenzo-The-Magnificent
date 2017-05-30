package it.polimi.ingsw.LM34.Network;

import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Network.Server.ServerNetworkController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GiulioComi on 07/05/2017.
 */
public class GameRoom {
    private GameManager gameManager;
    private Map<String, ServerNetworkController> players;

    public GameRoom() {
        this.players = new HashMap<>();
    }

    public void addPlayer(String username, ServerNetworkController networkController) {
        this.players.put(username, networkController);

        new Thread(new start()).start();
    }

    public ServerNetworkController getPlayerNetworkController(String player) {
        return players.get(player);
    }

    private class start implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<String> playerNames = new ArrayList<>();
            GameRoom.this.players.forEach((n, c) -> playerNames.add(n));

            GameRoom.this.gameManager = new GameManager(GameRoom.this, playerNames);
            GameRoom.this.gameManager.run();
        }
    }
}


//TODO: handle disconnections, timer of turn, ecc