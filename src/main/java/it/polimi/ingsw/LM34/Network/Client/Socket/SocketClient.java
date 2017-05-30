package it.polimi.ingsw.LM34.Network.Client.Socket;

import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.Network.Server.Socket.RequestToServer;
import it.polimi.ingsw.LM34.UI.AbstractUI;
import it.polimi.ingsw.LM34.Utils.Utilities;
import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by vladc on 5/25/2017.
 */
public class SocketClient extends AbstractClient {
    private static ClientInputListener inputListener;

    private static Socket socket;
    private static ObjectInputStream inStream;
    private static ObjectOutputStream outStream;

    public SocketClient(String serverIP, Integer port, AbstractUI ui) {
        try {
            socket = new Socket(serverIP, port);
            outStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outStream.flush();
            inStream = new ObjectInputStream(new DataInputStream(new BufferedInputStream(socket.getInputStream())));

            this.networkController = new ClientNetworkController(this);
            this.ui = ui;

            inputListener = new ClientInputListener();
            inputListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getInputStream() {
        return inStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outStream;
    }

    private class ClientInputListener extends Thread {
        private boolean run = true;

        @Override
        public void run() {
            while(this.run) {
                try {
                    String request = inStream.readUTF();

                    RequestToClient.valueOf(request).readAndHandle(SocketClient.this);
                } catch (IOException e) {
                    this.terminate();
                }
            }
        }

        public void terminate() {
            this.run = false;
        }
    }

    @Override
    public void login(String username, String password) {
        try {
            this.outStream.writeUTF(RequestToServer.LOGIN.name());
            this.outStream.writeUTF(username);
            this.outStream.writeUTF(password);
            this.outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer contextSelection(List<String> contexts) {
        return networkController.contextSelection(contexts);
    }
}
