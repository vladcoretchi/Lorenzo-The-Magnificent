package it.polimi.ingsw.LM34.Network.Client;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

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

    public Integer contextSelection(List<ContextType> contexts) {
        return clientConnection.getUI().contextSelection(contexts);
    }
}
