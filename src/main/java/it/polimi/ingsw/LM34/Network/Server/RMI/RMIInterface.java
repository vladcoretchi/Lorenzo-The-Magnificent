package it.polimi.ingsw.LM34.Network.Server.RMI;

import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by vladc on 5/25/2017.
 */
public interface RMIInterface extends Remote {

    boolean login(String username, String password, RMIClient clientRMI) throws RemoteException;

}
