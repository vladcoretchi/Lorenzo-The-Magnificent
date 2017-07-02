package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
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
import java.util.Map;
import java.util.Optional;

/**
 * this abstract class represent all prototype of cli methods, and will be implemented in {@link CLI}
 */
public interface UIInterface {

    String SERVER_IP = "localhost";
    Integer SOCKET_PORT = 20001;
    Integer RMI_PORT = 20002;

    /**
     * Start the UI
     */
    void show();

    /**
     * Login Menu shown before starting to visualize the gameboard
     */
    void loginMenu();

    /**
     * @param result true or false if the credentials are considered valid by the server
     */
    void loginResult(Boolean result);

    PlayerAction turnMainAction(Optional<Exception> lastActionValid);
    PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid);


    /**
     * Update UI informations about the gameboard state at start or after each player turn
     * @param game states
     */
    void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints);
    void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapVictoryPointsForTerritories);
    void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints);
    void loadExcommunicationCards(List<ExcommunicationCard> excommunicationCards);
    void loadFaithPath(Map<Integer, Integer> faithPath);
    void updateTowers(List<Tower> towers);
    void updateCouncilPalace(CouncilPalace councilPalace);
    void updateMarket(Market market);
    void updateProductionArea(WorkingArea productionArea);
    void updateHarvestArea(WorkingArea harvestArea);
    void updatePlayersData(List<Player> players);
    void updateDiceValues(List<Dice> dicesValues);


    /**
     * The following methods show to the player the association between number of cards/position
     * and the victory points provided
     */
    void showMapCharactersToVictoryPoints();
    void showMapTerritoriesToVictoryPoints();
    void showFaithPath();
    void showMilitaryPointsForTerritories();


    /**
     * Propose to the player what he could do in a specific context and retrieve his choice
     * @param choices available to players and related to specific game contexts
     * @return the choice of the player
     */
    Integer bonusTileSelection(List<BonusTile> bonusTiles);
    Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards);
    Integer familyMemberSelection(List<FamilyMember> familyMembers);
    Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);
    Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices);
    Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards);
    Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses);


    /**
     * Ask each player about their decision at Church Reports
     * @return the decision of the player
     */
    Boolean churchSupport();


    /**
     * Inform about the final victory points scored by all players and declare the winner
     * @param players in game
     */
    void endGame(List<Player> players);


    /**
     * Shows multiple kind of info about players
     * @param infoType information about a player {@link GameInformationType}
     * @param sentence the associated phrase to show to player
     * @param playerColor the color associated to the player to whom the info concerns
     */
    void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor);


    /**
     *Inform the player that his turn has ended (for timeout or his choice)
     */
    void endTurn();


    /**
     * Inform the player that the server is not more reachable
     */
    void disconnectionWarning();
}