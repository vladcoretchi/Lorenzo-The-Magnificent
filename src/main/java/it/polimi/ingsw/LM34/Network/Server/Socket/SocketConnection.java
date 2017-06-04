package it.polimi.ingsw.LM34.Network.Server.Socket;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Network.Client.Socket.RequestToClient;
import it.polimi.ingsw.LM34.Network.Server.AbstractConnection;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by vladc on 5/23/2017.
 */
public class SocketConnection extends AbstractConnection implements Runnable {
    private static boolean serverStatus;
    private boolean runListener;

    private final Socket connectionSocket;
    private final ObjectInputStream inStream;
    private final ObjectOutputStream outStream;

    public SocketConnection(Socket socket) throws IOException {
        this.connectionSocket = socket;
        this.outStream = new ObjectOutputStream(new BufferedOutputStream(connectionSocket.getOutputStream()));
        this.outStream.flush();
        this.inStream = new ObjectInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
        this.runListener = true;

        serverStatus = true;
    }

    public Socket getSocket() {
        return connectionSocket;
    }

    public ObjectInputStream getInputStream() {
        return inStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outStream;
    }

    @Override
    public void run() {
        while (serverStatus) {
            if (runListener) {
                try {
                    String request = this.inStream.readUTF();

                    RequestToServer.valueOf(request).readAndHandle(this);
                } catch (IOException e) {
                    //e.printStackTrace();
                    //this.terminateListener();
                }
            }
        }

        closeConnections();
    }

    public static void terminate() {
        serverStatus = false;
    }

    public void terminateListener() {
        this.runListener = false;
    }

    private void closeConnections() {
        try {
            this.inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean login(String username, String password) {
        boolean result = super.login(username, password);
        if (result)
            this.terminateListener();
        return result;
    }

    @Override
    public Integer contextSelection(List<PlayerSelectableContexts> contexts) {
        try {
            this.outStream.writeUTF(RequestToClient.CONTEXT_SELECTION.name());
            this.outStream.writeObject(contexts);
            this.outStream.flush();
            return this.inStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
