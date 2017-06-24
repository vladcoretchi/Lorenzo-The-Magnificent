package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
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

    void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards);
    void updateTowers(List<Tower> towers);
    void updateCouncilPalace(CouncilPalace councilPalace);
    void updateMarket(Market market);
    void updateProductionArea(WorkingArea productionArea);
    void updateHarvestArea(WorkingArea harvestArea);
    void updatePlayersData(List<Player> players);
    void updateDiceValues(List<Dice> dicesValues);

    PlayerAction turnMainAction(Optional<Boolean> lastActionValid);

    PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid);

    Integer familyMemberSelection(List<FamilyMember> familyMembers);

    Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);

    Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices);

    Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards);

    Boolean churchSupport();

    Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses);

    void endGame(List<Player> players);
}