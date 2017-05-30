package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.UI.CLI.CLI;

import java.util.List;

/**
 * this abstract class represent all prototype of cli methods, and will be implemented in {@link CLI}
 */
public abstract class AbstractUI {
    protected static final String SERVER_IP = "localhost";
    protected static final Integer SOCKET_PORT = 20001;
    protected static final Integer RMI_PORT = 20002;

    protected AbstractClient networkClient;

    public abstract void show();
    public abstract void showSplashScreen();
    public abstract void printDivider();
    public abstract void loginMenu();
    public abstract String connectionTypeSelection();
    public abstract Integer contextSelection(List<String> allContext);
    public abstract String towerSlotSelection(Integer towerNumber, Integer towerFloor);
    public abstract Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);
    public abstract void printGameBoard();
}