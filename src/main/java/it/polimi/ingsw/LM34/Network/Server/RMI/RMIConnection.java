package it.polimi.ingsw.LM34.Network.Server.RMI;

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
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClientInterface;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Network.Server.AbstractConnection;
import org.apache.commons.lang3.tuple.Pair;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class RMIConnection extends AbstractConnection {
    private RMIClientInterface clientRMI;

    public RMIConnection(RMIClientInterface rmiClient) {
        this.clientRMI = rmiClient;
    }

    @Override
    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        try {
            this.clientRMI.setExcommunicationCards(excommunicationCards);
        } catch(RemoteException e) {
            return;
        }
    }

    @Override
    public void updateTowers(List<Tower> towers) {

        try {
            this.clientRMI.updateTowers(towers);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
            return;
        }
    }

    @Override
    public void updateCouncilPalace(CouncilPalace councilPalace) {

        try {
            this.clientRMI.updateCouncilPalace(councilPalace);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
        }
    }

    @Override
    public void updateMarket(Market market) {

        try {
            this.clientRMI.updateMarket(market);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
        }
    }

    @Override
    public void updateProductionArea(WorkingArea productionArea) {
        try {
            this.clientRMI.updateProductionArea(productionArea);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);

        }
    }

    @Override
    public void updateHarvestArea(WorkingArea harvestArea) {

        try {
            this.clientRMI.updateHarvestArea(harvestArea);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
        }
    }

    @Override
    public void updatePlayersData(List<Player> players) {

        try {
            this.clientRMI.updatePlayersData(players);
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
        }
    }

    @Override
    public void updateDiceValues(List<Dice> diceValues) {
        try {
            this.clientRMI.updateDiceValues(diceValues);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
        }
    }

    @Override
    public PlayerAction turnMainAction(Optional<Exception> lastActionValid) {
        try {
            return this.clientRMI.turnMainAction(lastActionValid.orElse(null));
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
            return null;
        }
    }

    @Override
    public PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) {
        try {
            return this.clientRMI.turnMainAction(lastActionValid.orElse(null));
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
            return null;
        }
    }

    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        try {
            return this.clientRMI.familyMemberSelection(familyMembers);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(),e);
            return -1;
        }
    }

    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        try {
            return this.clientRMI.servantsSelection(servantsAvailable, minimumServantsRequested);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
            return -1;
        }
    }

    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        try {
            return this.clientRMI.resourceExchangeSelection(choices);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
            return -1;
        }
    }

    @Override
    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
        try {
            return this.clientRMI.leaderCardSelection(leaderCards);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
            return null;
        }
    }

    @Override
    public Boolean churchSupport() {

        try {
            return this.clientRMI.churchSupport();
        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
            return false;
        }
    }

    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        try {
            return this.clientRMI.selectCouncilPrivilegeBonus(availableBonuses);
        } catch(RemoteException e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName(), e);
            return -1;
        }
    }
}
