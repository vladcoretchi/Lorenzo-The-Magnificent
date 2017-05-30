package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Network.GameRoom;

import java.util.List;

/**
 * Created by vladc on 5/28/2017.
 */
public class ServerNetworkController {
    private AbstractConnection serverConnection;
    private GameRoom gameRoom;

    public ServerNetworkController(AbstractConnection connection) {
        this.serverConnection = connection;
        this.gameRoom = Server.addPlayerToGameRoom(connection.getUsername(), this);
    }

    public Integer contextSelection(List<String> contexts) {
        return serverConnection.contextSelection(contexts);
    }
}
