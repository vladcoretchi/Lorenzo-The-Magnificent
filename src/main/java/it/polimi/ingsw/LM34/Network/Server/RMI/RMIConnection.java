package it.polimi.ingsw.LM34.Network.Server.RMI;

import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Server.AbstractConnection;
import java.util.List;

/**
 * Created by vladc on 5/29/2017.
 */
public class RMIConnection extends AbstractConnection {
    private RMIClient clientRMI;

    public RMIConnection(RMIClient rmiClient) {
        this.clientRMI = rmiClient;
    }

    @Override
    public void contextSelection(List<String> contexts) {
        clientRMI.contextSelection(contexts);
    }

    public void loginResult(Boolean result) {
        clientRMI.loginResult(result);
    }
}
