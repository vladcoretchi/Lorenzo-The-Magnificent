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
public interface UIInterface {
    String SERVER_IP = "localhost";
    Integer SOCKET_PORT = 20001;
    Integer RMI_PORT = 20002;

    void show();
    void loginMenu();
    void loginResult(Boolean result);
    NetworkType connectionTypeSelection();
    Integer contextSelection(List<PlayerSelectableContexts> allContext);
    String towerSlotSelection(Integer towerNumber, Integer towerFloor);
    Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);
    Integer leaderCardAction(List<String> playerLeaderCards, LeaderCardsAction action);
    Integer marketSlotSelection(Market market);
    void workingArea(String workingAreaChoice, Player player);
    void printTowers(ArrayList<Tower> towers);
    void printGameBoard();
}