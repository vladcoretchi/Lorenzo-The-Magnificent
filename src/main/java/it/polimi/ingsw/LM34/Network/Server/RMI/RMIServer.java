package it.polimi.ingsw.LM34.Network.Server.RMI;

import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer implements RMIServerInterface {
    private static Registry registry;
    private List<RMIConnection> rmiConnections;

    public RMIServer(Integer port) {
        registry = null;
        try {
            registry = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            try {
                registry = LocateRegistry.getRegistry(port);
            } catch (RemoteException ee) {
                ee.printStackTrace();
            }
        }

        if (registry != null) {
            try {
                registry.rebind("RMIServer", this);
                UnicastRemoteObject.exportObject(this, port);

                rmiConnections = new ArrayList<>();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean login(String username, String password, RMIClientInterface clientRMI) {
        RMIConnection connection = new RMIConnection(clientRMI);
        if (connection.login(username, password)) {
            rmiConnections.add(connection);
            return true;
        } else
            return false;
    }

}
