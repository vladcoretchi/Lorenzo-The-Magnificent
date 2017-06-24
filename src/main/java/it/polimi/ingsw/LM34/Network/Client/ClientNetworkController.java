package it.polimi.ingsw.LM34.Network.Client;

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
import javafx.application.Platform;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by vladc on 5/29/2017.
 */
public class ClientNetworkController {
    private AbstractClient clientConnection;

    public ClientNetworkController(AbstractClient clientConnection) {
        this.clientConnection = clientConnection;
    }

    public void login(String username, String password) {
        this.clientConnection.login(username, password);
    }

    public void loginResult(Boolean result) {
        FutureTask uiTask = new FutureTask(() -> {
            this.clientConnection.getUI().loginResult(result);
            return null;
        });
        Platform.runLater(uiTask);
    }

    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        Platform.runLater(() -> this.clientConnection.getUI().setExcommunicationCards(excommunicationCards));
    }

    public void updateTowers(List<Tower> towers) {
        Platform.runLater(() -> this.clientConnection.getUI().updateTowers(towers));
    }

    public void updateCouncilPalace(CouncilPalace councilPalace){
        Platform.runLater(() -> this.clientConnection.getUI().updateCouncilPalace(councilPalace));
    }

    public void updateMarket(Market market) {
        Platform.runLater(() -> this.clientConnection.getUI().updateMarket(market));
    }

    public void updateProductionArea(WorkingArea productionArea) {
        Platform.runLater(() -> this.clientConnection.getUI().updateProductionArea(productionArea));
    }

    public void updateHarvestArea(WorkingArea harvestArea) {
        Platform.runLater(() -> this.clientConnection.getUI().updateHarvestArea(harvestArea));
    }

    public void updatePlayersData(List<Player> players) {
        Platform.runLater(() -> this.clientConnection.getUI().updatePlayersData(players));
    }

    public void updateDiceValues(List<Dice> diceValues) {
        Platform.runLater(() -> this.clientConnection.getUI().updateDiceValues(diceValues));
    }

    public PlayerAction turnMainAction(Optional<Boolean> lastActionValid) {
        FutureTask<PlayerAction> uiTask = new FutureTask<>(() -> this.clientConnection.getUI().turnMainAction(lastActionValid));
        return RunLaterTask(uiTask);
    }

    public PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid) {
        FutureTask<PlayerAction> uiTask = new FutureTask<>(() -> this.clientConnection.getUI().turnSecondaryAction(lastActionValid));
        return RunLaterTask(uiTask);
    }


    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> this.clientConnection.getUI().familyMemberSelection(familyMembers));
        return RunLaterTask(uiTask);
    }

    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> this.clientConnection.getUI().servantsSelection(servantsAvailable, minimumServantsRequested));
        return RunLaterTask(uiTask);
    }

    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> this.clientConnection.getUI().resourceExchangeSelection(choices));
        return RunLaterTask(uiTask);
    }

    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
        FutureTask<Pair<String, LeaderCardsAction>> uiTask = new FutureTask<>(() -> this.clientConnection.getUI().leaderCardSelection(leaderCards));
        return RunLaterTask(uiTask);
    }

    public Boolean churchSupport() {
        FutureTask<Boolean> uiTask = new FutureTask<>(() -> this.clientConnection.getUI().churchSupport());
        return RunLaterTask(uiTask);
    }

    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> this.clientConnection.getUI().selectCouncilPrivilegeBonus(availableBonuses));
        return RunLaterTask(uiTask);
    }

    private <T> T RunLaterTask(FutureTask<T> uiTask) {
        Platform.runLater(uiTask);
        try{
            return uiTask.get();
        } catch(ExecutionException | InterruptedException e) {
            return null;
        }
    }
}
