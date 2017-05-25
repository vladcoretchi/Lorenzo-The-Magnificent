package it.polimi.ingsw.LM34.Network.Server.RMI;

import it.polimi.ingsw.LM34.Network.Client.RMI.RMIInterface;
import it.polimi.ingsw.LM34.Network.Server.AbstractServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by vladc on 5/23/2017.
 */
public class RMIServer extends AbstractServer implements RMIInterface {
    private static Registry registry;

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
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void tryReceive(String message) throws RemoteException {
        System.out.println(message);
    }

}
