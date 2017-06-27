package it.polimi.ingsw.LM34.Network.Server.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public final class SocketServer {
    private static ServerSocket serverSocket;
    private static Thread connectionsHandler;
    private static List<SocketConnection> socketCnnections;

    public SocketServer(Integer port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.connectionsHandler = new Thread(new ConnectionHandler());
            this.socketCnnections = new ArrayList<>();
            this.connectionsHandler.start();
        } catch(IOException ex) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), ex);
            this.terminate();
        }
    }

    public void terminate() {
        this.connectionsHandler.interrupt();
        this.socketCnnections.forEach(connection -> connection.terminate());
    }

    private class ConnectionHandler implements Runnable {
        private boolean run = true;

        @Override
        public void run() {
            //TODO: consider using NIO to improve performance with many clients connected
            while(this.run) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketConnection connection = new SocketConnection(socket);
                    new Thread(connection).start();
                } catch(IOException e) {
                    LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
                    this.terminate();
                }
            }
        }

        public void terminate() {
            this.run = false;
        }
    }

}
