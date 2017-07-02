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
import it.polimi.ingsw.LM34.UI.UIInterface;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public abstract class AbstractClient {
    protected ClientNetworkController networkController;
    protected UIInterface ui;

    public final ClientNetworkController getNetworkController() {
        return this.networkController;
    }

    public final UIInterface getUI() {
        return this.ui;
    }

    public abstract void login(String username, String password);

    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        this.networkController.setExcommunicationCards(excommunicationCards);
    }

    public void updateTowers(List<Tower> towers) {
        this.networkController.updateTowers(towers);
    }

    public void updateCouncilPalace(CouncilPalace councilPalace) {
        this.networkController.updateCouncilPalace(councilPalace);
    }

    public void updateMarket(Market market) {
        this.networkController.updateMarket(market);
    }

    public void updateProductionArea(WorkingArea productionArea) {
        this.networkController.updateProductionArea(productionArea);
    }

    public void updateHarvestArea(WorkingArea harvestArea) {
        this.networkController.updateHarvestArea(harvestArea);
    }

    public void updatePlayersData(List<Player> players) {
        this.networkController.updatePlayersData(players);
    }

    public void updateDiceValues(List<Dice> diceValues) {
        this.networkController.updateDiceValues(diceValues);
    }

    public PlayerAction turnMainAction(Exception lastActionValid) {
        return this.networkController.turnMainAction(Optional.ofNullable(lastActionValid));
    }

    public PlayerAction turnSecondaryAction(Exception lastActionValid) {
        return this.networkController.turnSecondaryAction(Optional.ofNullable(lastActionValid));
    }

    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        return this.networkController.familyMemberSelection(familyMembers);
    }

    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        return this.networkController.servantsSelection(servantsAvailable, minimumServantsRequested);
    }

    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        return this.networkController.resourceExchangeSelection(choices);
    }

    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
        return this.networkController.leaderCardSelection(leaderCards);
    }

    public Boolean churchSupport() {
        return this.networkController.churchSupport();
    }

    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        return this.networkController.selectCouncilPrivilegeBonus(availableBonuses);
    }
}
