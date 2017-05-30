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
        players.put(username, networkController);

        new Thread(new start()).start();
    }

    public void trySend() {
        List<String> c = new ArrayList<>();
        c.add("Context1");
        c.add("Context2");
        c.add("Context3");
        players.get("username").contextSelection(c);
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
            players.forEach((n, c) -> playerNames.add(n));

            gameManager = new GameManager(playerNames);
            gameManager.run();

            trySend();
        }
    }
}


//TODO: handle disconnections, timer of turn, ecc