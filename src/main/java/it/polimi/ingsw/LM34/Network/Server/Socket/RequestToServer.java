package it.polimi.ingsw.LM34.Network.Server.Socket;

import it.polimi.ingsw.LM34.Network.Client.Socket.RequestToClient;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by vladc on 5/28/2017.
 * inspired by https://stackoverflow.com/questions/12935709/call-a-specific-method-based-on-enum-type
 */
public enum RequestToServer {
    LOGIN {
        @Override
        void readAndHandle(SocketConnection connection) {
            try {
                String username = connection.getInputStream().readUTF();
                String password = connection.getInputStream().readUTF();

                connection.getOutputStream().writeUTF(RequestToClient.LOGIN.name());
                connection.getOutputStream().writeBoolean(connection.login(username, password));
                connection.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },
    CONTEXT_SELECTION {
        @Override
        void readAndHandle(SocketConnection connection) {
            try {
                Integer selection = connection.getInputStream().readInt();

                System.out.println(selection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    abstract void readAndHandle(SocketConnection connection);
}
