package it.polimi.ingsw.LM34.Network.Client.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by vladc on 5/25/2017.
 */
public interface RMIInterface extends Remote {

    void tryReceive(String message) throws RemoteException;

}
