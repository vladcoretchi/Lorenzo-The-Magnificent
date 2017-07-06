package it.polimi.ingsw.LM34.Network.Server.RMI;

import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class RMIServer implements RMIServerInterface {
    private static Registry registry;
    private List<RMIConnection> rmiConnections;

    public RMIServer(Integer port) {
        registry = null;
        try {
            registry = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            try {
                registry = LocateRegistry.getRegistry(port);
            } catch (RemoteException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        if (registry != null) {
            try {
                registry.rebind("RMIServer", this);
                UnicastRemoteObject.exportObject(this, port);

                rmiConnections = new ArrayList<>();
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
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
