package it.polimi.ingsw.LM34.Network.Server.Socket;

import it.polimi.ingsw.LM34.Utils.Utilities;

import java.io.*;
import java.net.Socket;

/**
 * Created by vladc on 5/23/2017.
 */
public class SocketConnection implements Runnable {
    private Boolean run;

    private static Socket connectionSocket;
    private static ObjectInputStream inStream;
    private static ObjectOutputStream outStream;

    public SocketConnection(Socket socket) throws IOException {
        connectionSocket = socket;
        outStream = new ObjectOutputStream(new BufferedOutputStream(connectionSocket.getOutputStream()));
        outStream.flush();
        inStream = new ObjectInputStream(new BufferedInputStream(connectionSocket.getInputStream()));
        run = true;
    }

    public void terminate() {
        this.run = false;
    }

    @Override
    public void run() {
        System.out.println("run method started");
        while (run) {
            try {
                String message = (String) inStream.readObject();

                System.out.println(message);
            }
            catch (IOException e) {
                //e.printStackTrace();
                //this.terminate();
            }
            catch (ClassNotFoundException e) {
                //e.printStackTrace();
                //this.terminate();
            }
        }

        closeConnection(inStream);
        closeConnection(outStream);
        closeConnection(connectionSocket);
    }

    private void closeConnection(Closeable obj) {
        try {
            obj.close();
        }
        catch (IOException e) { }
    }
}
