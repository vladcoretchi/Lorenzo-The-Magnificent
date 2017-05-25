package it.polimi.ingsw.LM34.Network.Server.Socket;

import it.polimi.ingsw.LM34.Network.Server.AbstractServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vladc on 5/23/2017.
 */
public class SocketServer extends AbstractServer {

    private static ServerSocket serverSocket;

    private static ConnectionHandler connectionsHandler;

    public SocketServer(Integer port) {
        try {
            serverSocket = new ServerSocket(port);
            connectionsHandler = new ConnectionHandler();
            connectionsHandler.start();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void terminate() {
        connectionsHandler.terminate();
    }

    private class ConnectionHandler extends Thread {
        private Boolean run = true;

        private List<SocketConnection> connections;

        @Override
        public void run() {
            //TODO: consider using NIO to improve performance with many clients connected
            connections = new ArrayList<>();
            while(this.run) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketConnection connection = new SocketConnection(socket);
                    new Thread(connection).start();
                    connections.add(connection);
                } catch (IOException e) {
                    this.terminate();
                }
            }
        }

        public void terminate() {
            for (SocketConnection sc : connections) {
                try {
                    sc.terminate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            this.run = false;
        }
    }

}
