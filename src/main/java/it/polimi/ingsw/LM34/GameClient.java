package it.polimi.ingsw.LM34;

import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;

/**
 * Created by vladc on 5/25/2017.
 */
public class GameClient {
    private static final String SERVER_IP = "localhost";
    private static final Integer SOCKET_PORT = 20001;
    private static final Integer RMI_PORT = 20002;

    private static AbstractClient networkClient;

    public static void main(String[] args) {
        //networkClient = new SocketClient(SERVER_IP, SOCKET_PORT);
        networkClient = new RMIClient(SERVER_IP, RMI_PORT);

        networkClient.trySend();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}
    }
}
