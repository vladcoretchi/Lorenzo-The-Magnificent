package it.polimi.ingsw.LM34.Network.Server.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by vladc on 5/23/2017.
 */
public final class SocketServer {

    private static ServerSocket serverSocket;

    private static ConnectionHandler connectionsHandler;

    public SocketServer(Integer port) {
        try {
            serverSocket = new ServerSocket(port);
            connectionsHandler = new ConnectionHandler();
            connectionsHandler.start();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void terminate() {
        connectionsHandler.terminate();
    }

    private class ConnectionHandler extends Thread {
        private boolean run = true;

        @Override
        public void run() {
            //TODO: consider using NIO to improve performance with many clients connected
            while(this.run) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketConnection connection = new SocketConnection(socket);
                    new Thread(connection).start();
                } catch (IOException e) {
                    this.terminate();
                }
            }
        }

        public void terminate() {
            this.run = false;
            SocketConnection.terminate();
        }
    }

}
