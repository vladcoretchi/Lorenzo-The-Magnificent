package it.polimi.ingsw.LM34.Network.Client.RMI;

import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Server.RMI.RMIServer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by vladc on 5/25/2017.
 */
public class RMIClient extends AbstractClient implements Remote {
    private static RMIInterface server;

    public RMIClient(String serverIP, Integer port) {
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            server = (RMIInterface) registry.lookup("RMIServer");
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void trySend() {
        try {
            server.tryReceive("testRMI");
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
