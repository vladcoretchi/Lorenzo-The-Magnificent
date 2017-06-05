package it.polimi.ingsw.LM34.Network.Client.RMI;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by vladc on 5/30/2017.
 */
public interface RMIClientInterface extends Remote {

    Integer contextSelection(List<PlayerSelectableContexts> contexts) throws RemoteException;

    Integer marketSlotSelection(Market market) throws RemoteException;
}
