package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.UI.CLI.CLI;
import java.util.ArrayList;
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
    protected abstract void loginMenu();
    public abstract void loginResult(Boolean result);
    public abstract NetworkType connectionTypeSelection();
    public abstract Integer contextSelection(List<PlayerSelectableContexts> allContext);
    public abstract String towerSlotSelection(Integer towerNumber, Integer towerFloor);
    public abstract Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);
    public abstract Integer leaderCardAction(List<String> playerLeaderCards, LeaderCardsAction action);
    public abstract Integer marketSlotSelection(Market market);
    public abstract void workingArea(String workingAreaChoice, Player player);
    public abstract Integer councilPalace(Player player); //TODO: maybe void
    public abstract void printTowers(ArrayList<Tower> towers);
    public abstract void printGameBoard();
}