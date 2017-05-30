package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.Server.RMI.RMIServer;
import it.polimi.ingsw.LM34.Network.Server.Socket.SocketServer;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladc on 5/23/2017.
 */
public class Server {
    private static final Integer SOCKET_PORT = 20001;
    private static final Integer RMI_PORT = 20002;

    private static Server serverInstance;

    private static SocketServer socketServer;
    private static RMIServer rmiServer;

    private static List<GameRoom> gameRooms;
    private static GameRoom waitingRoom;

    private Server() {
        Configurator.loadConfigs();

        waitingRoom = new GameRoom();
        gameRooms = new ArrayList<>();

        socketServer = new SocketServer(SOCKET_PORT);
        rmiServer = new RMIServer(RMI_PORT);
    }

    public static Server getInstance() {
        if(serverInstance == null)
            serverInstance = new Server();

        return serverInstance;
    }

    public static void terminate() {
        socketServer.terminate();
    }

    public static boolean login(String username, String password) {
        //TODO: actual login / registration
        return true;
    }

    public static GameRoom addPlayerToGameRoom(String username, ServerNetworkController networkController) {
        //TODO: handle game rooms creation, game start timer, etc
        waitingRoom.addPlayer(username, networkController);
        return waitingRoom;
    }

    public static void startWaitingGame() {
        gameRooms.add(waitingRoom);
        waitingRoom = new GameRoom();
    }
}
