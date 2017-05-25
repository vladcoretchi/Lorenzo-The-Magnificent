package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Network.Server.RMI.RMIServer;
import it.polimi.ingsw.LM34.Network.Server.Socket.SocketServer;

/**
 * Created by vladc on 5/23/2017.
 */
public class Server implements ServerInterface {
    private static final Integer SOCKET_PORT = 20001;
    private static final Integer RMI_PORT = 20002;

    private static Server serverInstance;

    private static SocketServer socketServer;
    private static RMIServer rmiServer;

    private Server() {
        socketServer = new SocketServer(SOCKET_PORT);
        rmiServer = new RMIServer(RMI_PORT);
    }

    public static Server getInstance() {
        if(serverInstance == null)
            serverInstance = new Server();

        return serverInstance;
    }

    public void terminate() {
        socketServer.terminate();
    }

}
