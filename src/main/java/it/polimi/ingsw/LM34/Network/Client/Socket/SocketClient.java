package it.polimi.ingsw.LM34.Network.Client.Socket;

import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Utils.Utilities;
import java.io.*;
import java.net.Socket;

/**
 * Created by vladc on 5/25/2017.
 */
public class SocketClient extends AbstractClient {

    private static Socket socket;
    private static ObjectInputStream inStream;
    private static ObjectOutputStream outStream;

    public SocketClient(String serverIP, Integer port) {
        try {
            socket = new Socket(serverIP, port);
            outStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outStream.flush();
            inStream = new ObjectInputStream(new DataInputStream(new BufferedInputStream(socket.getInputStream())));
        }
        catch (IOException e) { }
    }

    public void trySend() {
        String message = "testSocket";
        this.sendObject(message);
    }

    private void sendObject(Object o) {
        try {
            outStream.writeObject(o);
            outStream.flush();
        }
        catch (IOException e) { }
    }
}
