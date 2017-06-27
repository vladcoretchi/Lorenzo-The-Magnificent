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
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class ClientNetworkController {
    private AbstractClient clientConnection;

    public ClientNetworkController(AbstractClient clientConnection) {
        this.clientConnection = clientConnection;
    }

    public void login(String username, String password) {
        this.clientConnection.login(username, password);
    }

    public void loginResult(Boolean result) {
        /*FutureTask uiTask = new FutureTask(() -> {
            this.clientConnection.getUI().loginResult(result);
            return null;
        });
        Platform.runLater(uiTask);*/
        this.clientConnection.getUI().loginResult(result);
    }

    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        this.clientConnection.getUI().setExcommunicationCards(excommunicationCards);
    }

    public void updateTowers(List<Tower> towers) {
        this.clientConnection.getUI().updateTowers(towers);
    }

    public void updateCouncilPalace(CouncilPalace councilPalace){
        this.clientConnection.getUI().updateCouncilPalace(councilPalace);
    }

    public void updateMarket(Market market) {
        this.clientConnection.getUI().updateMarket(market);
    }

    public void updateProductionArea(WorkingArea productionArea) {
        this.clientConnection.getUI().updateProductionArea(productionArea);
    }

    public void updateHarvestArea(WorkingArea harvestArea) {
        this.clientConnection.getUI().updateHarvestArea(harvestArea);
    }

    public void updatePlayersData(List<Player> players) {
        this.clientConnection.getUI().updatePlayersData(players);
    }

    public void updateDiceValues(List<Dice> diceValues) {
        this.clientConnection.getUI().updateDiceValues(diceValues);
    }

    public PlayerAction turnMainAction(Optional<Boolean> lastActionValid) {
        return this.clientConnection.getUI().turnMainAction(lastActionValid);
    }

    public PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid) {
        return this.clientConnection.getUI().turnSecondaryAction(lastActionValid);
    }


    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        return this.clientConnection.getUI().familyMemberSelection(familyMembers);
    }

    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        return this.clientConnection.getUI().servantsSelection(servantsAvailable, minimumServantsRequested);
    }

    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        return this.clientConnection.getUI().resourceExchangeSelection(choices);
    }

    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
        return this.clientConnection.getUI().leaderCardSelection(leaderCards);
    }

    public Boolean churchSupport() {
        return this.clientConnection.getUI().churchSupport();
    }

    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        return this.clientConnection.getUI().selectCouncilPrivilegeBonus(availableBonuses);
    }
}
