package it.polimi.ingsw.LM34;

import it.polimi.ingsw.LM34.Network.Server.Server;

/**
 * Created by vladc on 5/23/2017.
 */
public class GameServer {

    private static Server server;

    public static void main(String[] args) {
        server = Server.getInstance();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }

    private static final class ShutdownHook extends Thread {

        @Override
        public void run() {
            server.terminate();
        }
    }

}
