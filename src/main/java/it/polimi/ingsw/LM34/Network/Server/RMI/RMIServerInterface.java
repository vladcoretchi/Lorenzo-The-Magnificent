package it.polimi.ingsw.LM34.Network.Server.RMI;

import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {

    boolean login(String username, String password, RMIClientInterface clientRMI) throws RemoteException;

}
