package it.polimi.ingsw.LM34.Network.Server;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractConnection {
    protected ServerNetworkController networkController;
    protected String username;

    public abstract void remove();

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

    public abstract void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints) throws NetworkConnectionException;
    public abstract void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapMilitaryPointsForTerritories) throws NetworkConnectionException;
    public abstract void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints) throws NetworkConnectionException;
    public abstract void loadFaithPath(Map<Integer, Integer> faithPath) throws NetworkConnectionException;
    public abstract void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) throws NetworkConnectionException;
    public abstract void updateTowers(List<Tower> towers) throws NetworkConnectionException;
    public abstract void updateCouncilPalace(CouncilPalace councilPalace) throws NetworkConnectionException;
    public abstract void updateMarket(Market market) throws NetworkConnectionException;
    public abstract void updateProductionArea(WorkingArea productionArea) throws NetworkConnectionException;
    public abstract void updateHarvestArea(WorkingArea harvestArea) throws NetworkConnectionException;
    public abstract void updatePlayersData(List<Player> players) throws NetworkConnectionException;
    public abstract void updateDiceValues(List<Dice> diceValues) throws NetworkConnectionException;

    public abstract void startGame() throws NetworkConnectionException;

    public abstract PlayerAction turnMainAction(Optional<Exception> lastActionValid) throws NetworkConnectionException;

    public abstract PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) throws NetworkConnectionException;

    public abstract Integer familyMemberSelection(List<FamilyMember> familyMembers) throws NetworkConnectionException;

    public abstract Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) throws NetworkConnectionException;

    public abstract Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) throws NetworkConnectionException;

    public abstract Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) throws NetworkConnectionException;

    public abstract Boolean churchSupport() throws NetworkConnectionException;

    public abstract Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) throws NetworkConnectionException;

    public abstract Integer bonusTileSelection(List<BonusTile> bonusTiles) throws NetworkConnectionException;

    public abstract Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) throws NetworkConnectionException;

    public abstract Boolean alternativeRequirementsPayment() throws NetworkConnectionException;

    public abstract void endGame(List<Player> players) throws NetworkConnectionException;

    public abstract void endTurn() throws NetworkConnectionException;

    public abstract void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor) throws NetworkConnectionException;

    public abstract PlayerAction freeAction(PlayerAction availableAction, Optional<Exception> lastActionValid) throws NetworkConnectionException;

    public abstract Integer leaderCardCopy(List<LeaderCard> leaderCards) throws NetworkConnectionException;
}
