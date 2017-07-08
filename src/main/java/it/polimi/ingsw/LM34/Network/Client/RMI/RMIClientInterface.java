package it.polimi.ingsw.LM34.Network.Client.RMI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
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
import org.apache.commons.lang3.tuple.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RMIClientInterface extends Remote {

    void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints) throws RemoteException;
    void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapMilitaryPointsForTerritories) throws RemoteException;
    void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints) throws RemoteException;
    void loadFaithPath(Map<Integer, Integer> faithPath) throws RemoteException;
    void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) throws RemoteException;
    void updateTowers(List<Tower> towers) throws RemoteException;
    void updateCouncilPalace(CouncilPalace councilPalace) throws RemoteException;
    void updateMarket(Market market) throws RemoteException;
    void updateProductionArea(WorkingArea productionArea) throws RemoteException;
    void updateHarvestArea(WorkingArea harvestArea) throws RemoteException;
    void updatePlayersData(List<Player> players) throws RemoteException;
    void updateDiceValues(List<Dice> diceValues) throws RemoteException;

    void startGame() throws RemoteException;

    PlayerAction turnMainAction(Exception lastActionValid) throws RemoteException;

    PlayerAction turnSecondaryAction(Exception lastActionValid) throws RemoteException;

    Integer familyMemberSelection(List<FamilyMember> familyMembers) throws RemoteException;

    Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) throws RemoteException;

    Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) throws RemoteException;

    Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) throws RemoteException;

    Boolean churchSupport() throws RemoteException;

    Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) throws RemoteException;

    Integer bonusTileSelection(List<BonusTile> availableBonusTiles) throws RemoteException;

    Integer leaderCardSelectionPhase(List<LeaderCard> availableLeaderCards) throws RemoteException;

    Boolean alternativeRequirementsPayment() throws RemoteException;

    void endGame(List<Player> players) throws RemoteException;

    void endTurn() throws RemoteException;

    void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor) throws RemoteException;

    PlayerAction freeAction(PlayerAction availableAction, Exception lastActionValid) throws RemoteException;

    Integer leaderCardCopy(List<LeaderCard> leaderCards) throws RemoteException;
}
