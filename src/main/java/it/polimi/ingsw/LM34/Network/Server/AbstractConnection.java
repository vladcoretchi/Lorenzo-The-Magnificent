package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import java.util.List;

/**
 * Created by vladc on 5/23/2017.
 */
public abstract class AbstractConnection {
    protected ServerNetworkController networkController;
    protected String username;

    public final String getUsername() {
        return this.username;
    }

    public final ServerNetworkController getNetworkController() {
        return this.networkController;
    }

    public boolean login(String username, String password) {
        boolean loginResult = Server.login(username, password);
        if (loginResult) {
            this.username = username;
            this.networkController = new ServerNetworkController(this);
        }
        return loginResult;
    }

    public abstract Integer contextSelection(List<PlayerSelectableContexts> contexts);

    public abstract Integer marketSlotSelection(Market market);
}
