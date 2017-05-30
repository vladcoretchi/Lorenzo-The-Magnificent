package it.polimi.ingsw.LM34.Network.Client.RMI;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by vladc on 5/30/2017.
 */
public interface RMIClientInterface extends Remote {

    Integer contextSelection(List<ContextType> contexts) throws RemoteException;
}
