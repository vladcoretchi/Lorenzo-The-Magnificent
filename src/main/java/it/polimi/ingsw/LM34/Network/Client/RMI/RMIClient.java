package it.polimi.ingsw.LM34.Network.Client.RMI;

import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.Network.Server.RMI.RMIInterface;
import it.polimi.ingsw.LM34.UI.AbstractUI;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by vladc on 5/25/2017.
 */
public class RMIClient extends AbstractClient implements Remote {
    private RMIInterface server;

    public RMIClient(String serverIP, Integer port, AbstractUI ui) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            server = (RMIInterface) registry.lookup("RMIServer");
            UnicastRemoteObject.exportObject(this, 0);

            this.networkController = new ClientNetworkController(this);
            this.ui = ui;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(String username, String password) {
        try {
            this.networkController.loginResult(server.login(username, password, this));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void loginResult(Boolean result) {
        this.networkController.loginResult(result);
    }

    @Override
    public Integer contextSelection(List<String> contexts) {
        return 0;
    }

}
