package it.polimi.ingsw.LM34.Network.Client;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.UI.AbstractUI;

import java.util.List;

/**
 * Created by vladc on 5/23/2017.
 */
public abstract class AbstractClient {
    protected ClientNetworkController networkController;
    protected AbstractUI ui;

    public final ClientNetworkController getNetworkController() {
        return this.networkController;
    }

    public final AbstractUI getUI() {
        return this.ui;
    }

    public abstract void login(String username, String password);

    public abstract Integer contextSelection(List<ContextType> contexts);

}
