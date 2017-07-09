package it.polimi.ingsw.LM34.Network.Socket;

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
    private static List<SocketConnection> socketConnections;

    public SocketServer(Integer port) {
        try {
            serverSocket = new ServerSocket(port);
            connectionsHandler = new Thread(new ConnectionHandler());
            socketConnections = new ArrayList<>();
            connectionsHandler.start();
        } catch(IOException ex) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), ex);
            this.terminate();
        }
    }

    public void terminate() {
        connectionsHandler.interrupt();
        socketConnections.forEach(SocketConnection::terminate);
    }

    private class ConnectionHandler implements Runnable {
        private boolean run = true;

        @Override
        public void run() {
            while(this.run) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketConnection connection = new SocketConnection(socket, SocketServer.this);
                    new Thread(connection).start();
                    SocketServer.socketConnections.add(connection);
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

    public void removeClosedConnection(SocketConnection connection) {
        //directly remove the object is seen as a smell as it might take too long for large collections
        Integer index = socketConnections.indexOf(connection);
        if(index >= 0)
            socketConnections.remove(index.intValue());
    }

}
