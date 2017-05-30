package it.polimi.ingsw.LM34.Network.Client.RMI;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.Network.Server.RMI.RMIServerInterface;
import it.polimi.ingsw.LM34.UI.AbstractUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by vladc on 5/25/2017.
 */
public class RMIClient extends AbstractClient implements RMIClientInterface {
    private RMIServerInterface server;

    public RMIClient(String serverIP, Integer port, AbstractUI ui) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            server = (RMIServerInterface) registry.lookup("RMIServer");
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
            Boolean loginResult = server.login(username, password, this);
            this.networkController.loginResult(loginResult);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void loginResult(Boolean result) {
        this.networkController.loginResult(result);
    }

    @Override
    public Integer contextSelection(List<ContextType> contexts) {
        return networkController.contextSelection(contexts);
    }

}
