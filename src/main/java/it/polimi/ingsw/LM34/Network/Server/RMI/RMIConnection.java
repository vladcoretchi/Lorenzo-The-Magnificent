package it.polimi.ingsw.LM34.Network.Server.RMI;

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
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClientInterface;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Network.Server.AbstractConnection;
import org.apache.commons.lang3.tuple.Pair;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class RMIConnection extends AbstractConnection {
    private RMIClientInterface clientRMI;

    public RMIConnection(RMIClientInterface rmiClient) {
        this.clientRMI = rmiClient;
    }

    @Override
    public void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints) throws NetworkConnectionException {
        try {
            this.clientRMI.loadMapTerritoriesToVictoryPoints(mapTerritoriesToVictoryPoints);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapVictoryPointsForTerritories) throws NetworkConnectionException {
        try {
            this.clientRMI.loadMapMilitaryPointsForTerritories(mapVictoryPointsForTerritories);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints) throws NetworkConnectionException {
        try {
            this.clientRMI.loadMapCharactersToVictoryPoints(mapCharactersToVictoryPoints);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void loadFaithPath(Map<Integer, Integer> faithPath) throws NetworkConnectionException {
        try {
            this.clientRMI.loadFaithPath(faithPath);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) throws NetworkConnectionException {
        try {
            this.clientRMI.setExcommunicationCards(excommunicationCards);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateTowers(List<Tower> towers) throws NetworkConnectionException {
        try {
            this.clientRMI.updateTowers(towers);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateCouncilPalace(CouncilPalace councilPalace) throws NetworkConnectionException {
        try {
            this.clientRMI.updateCouncilPalace(councilPalace);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateMarket(Market market) throws NetworkConnectionException {
        try {
            this.clientRMI.updateMarket(market);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateProductionArea(WorkingArea productionArea) throws NetworkConnectionException {
        try {
            this.clientRMI.updateProductionArea(productionArea);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateHarvestArea(WorkingArea harvestArea) throws NetworkConnectionException {
        try {
            this.clientRMI.updateHarvestArea(harvestArea);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updatePlayersData(List<Player> players) throws NetworkConnectionException {
        try {
            this.clientRMI.updatePlayersData(players);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void updateDiceValues(List<Dice> diceValues) throws NetworkConnectionException {
        try {
            this.clientRMI.updateDiceValues(diceValues);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void startGame() throws NetworkConnectionException {
        try {
            this.clientRMI.startGame();
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public PlayerAction turnMainAction(Optional<Exception> lastActionValid) throws NetworkConnectionException {
        try {
            return this.clientRMI.turnMainAction(lastActionValid.orElse(null));
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) throws NetworkConnectionException {
        try {
            return this.clientRMI.turnMainAction(lastActionValid.orElse(null));
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) throws NetworkConnectionException {
        try {
            return this.clientRMI.familyMemberSelection(familyMembers);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(),e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) throws NetworkConnectionException {
        try {
            return this.clientRMI.servantsSelection(servantsAvailable, minimumServantsRequested);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) throws NetworkConnectionException {
        try {
            return this.clientRMI.resourceExchangeSelection(choices);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        try {
            return this.clientRMI.leaderCardSelection(leaderCards);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Boolean churchSupport() throws NetworkConnectionException {
        try {
            return this.clientRMI.churchSupport();
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) throws NetworkConnectionException {
        try {
            return this.clientRMI.selectCouncilPrivilegeBonus(availableBonuses);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer bonusTileSelection(List<BonusTile> bonusTiles) throws NetworkConnectionException {
        try {
            return this.clientRMI.bonusTileSelection(bonusTiles);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        try {
            return this.clientRMI.leaderCardSelectionPhase(leaderCards);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Boolean alternativeRequirementsPayment() throws NetworkConnectionException {
        try {
            return this.clientRMI.alternativeRequirementsPayment();
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void endGame(List<Player> players) throws NetworkConnectionException {
        try {
            this.clientRMI.endGame(players);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    @Override
    public void endTurn() throws NetworkConnectionException {
        try {
            this.clientRMI.endTurn();
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor) throws NetworkConnectionException {
        try {
            this.clientRMI.informInGamePlayers(infoType, playerName, playerColor);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public PlayerAction freeAction(PlayerAction availableAction, Optional<Exception> lastActionValid) throws NetworkConnectionException {
        try {
            return this.clientRMI.freeAction(availableAction, lastActionValid.orElse(null));
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }

    @Override
    public Integer leaderCardCopy(List<LeaderCard> leaderCards) throws NetworkConnectionException {
        try {
            return this.clientRMI.leaderCardCopy(leaderCards);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw new NetworkConnectionException();
        }
    }
}
