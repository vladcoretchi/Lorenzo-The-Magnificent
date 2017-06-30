package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.UI.CLI.CLI;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

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

    PlayerAction turnMainAction(Optional<Boolean> lastActionValid);
    PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid);

    void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards);
    void updateTowers(List<Tower> towers);
    void updateCouncilPalace(CouncilPalace councilPalace);
    void updateMarket(Market market);
    void updateProductionArea(WorkingArea productionArea);
    void updateHarvestArea(WorkingArea harvestArea);
    void updatePlayersData(List<Player> players);
    void updateDiceValues(List<Dice> dicesValues);

    Integer bonusTileSelection(List<BonusTile> bonusTiles);
    Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards);
    Integer familyMemberSelection(List<FamilyMember> familyMembers);
    Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);
    Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices);
    Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards);
    Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses);
    Boolean churchSupport();
    void churchSupportReport(String churchResult, PawnColor playerColor);
    void endGame(List<Player> players);

    void endTurn();
    void disconnectionWarning();
    void infoNewPlayerTurn(String playerName, PawnColor playerColor);
    void infoDisconnectedPlayer(String playerName, PawnColor playerColor);
    void infoReconnectedPlayer(String playerName, PawnColor playerColor);
}