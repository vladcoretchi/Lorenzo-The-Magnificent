package it.polimi.ingsw.LM34.Network.Client.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by vladc on 5/30/2017.
 */
public interface RMIClientInterface extends Remote {

    Integer contextSelection(List<String> contexts) throws RemoteException;
}
