package it.polimi.ingsw.LM34;

import it.polimi.ingsw.LM34.Network.Server.Server;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class GameServer {

    private static Server server;

    public static void main(String[] args) {
        Handler handlerObj = new ConsoleHandler();
        handlerObj.setLevel(Level.OFF);
        LOGGER.addHandler(handlerObj);
        LOGGER.setLevel(Level.OFF);
        LOGGER.setUseParentHandlers(false);

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
