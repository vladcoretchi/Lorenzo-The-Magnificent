package it.polimi.ingsw.LM34.Network.Client;

import java.io.IOException;
import java.util.List;

/**
 * Created by vladc on 5/29/2017.
 */
public class ClientNetworkController {
    private AbstractClient clientConnection;

    public ClientNetworkController(AbstractClient clientConnection) {
        this.clientConnection = clientConnection;
    }

    public void login(String username, String password) {
        this.clientConnection.login(username, password);
    }

    public void loginResult(Boolean result) {
        clientConnection.getUI().loginResult(result);
    }

    public Integer contextSelection(List<String> contexts) {
        return clientConnection.getUI().contextSelection(contexts);
    }
}
