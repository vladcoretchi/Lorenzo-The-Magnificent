package it.polimi.ingsw.LM34.Network.Server.RMI;

import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClientInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by vladc on 5/25/2017.
 */
public interface RMIServerInterface extends Remote {

    boolean login(String username, String password, RMIClientInterface clientRMI) throws RemoteException;

}
