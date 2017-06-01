package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectionableContexts;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.UI.CLI.CLI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this abstract class represent all prototype of cli methods, and will be implemented in {@link CLI}
 */
public abstract class AbstractUI {
    protected static final String SERVER_IP = "localhost";
    protected static final Integer SOCKET_PORT = 20001;
    protected static final Integer RMI_PORT = 20002;

    protected AbstractClient networkClient;
    protected ClientNetworkController networkController;

    public abstract void show();
    public abstract void showSplashScreen();
    public abstract void printDivider();
    public abstract void loginMenu();
    public abstract void loginResult(Boolean result);
    public abstract String connectionTypeSelection();
    public abstract Integer contextSelection(List<PlayerSelectionableContexts> allContext);
    public abstract String towerSlotSelection(Integer towerNumber, Integer towerFloor);
    public abstract Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);
    public abstract ArrayList<String> playerLeaderCardsAction(List<String> playerLeaderCards, String action);
    public abstract HashMap<Integer, Integer> market(Market market, Player player);
    public abstract void workingArea(String workingAreaChoice, Player player);
    public abstract Integer councilPalace(Player player);
    public abstract void printTowers(ArrayList<Tower> towers);
    public abstract void printGameBoard();
}