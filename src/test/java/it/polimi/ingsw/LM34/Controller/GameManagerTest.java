package it.polimi.ingsw.LM34.Controller;

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
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Network.Server.AbstractConnection;
import it.polimi.ingsw.LM34.Network.Server.Server;
import it.polimi.ingsw.LM34.Network.Server.ServerNetworkController;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class GameManagerTest {

    @Test
    public void drawExcommunicationCard() {
        Server server = Server.getInstance();

    }
    @Test
    public void setUpGameSpaces() throws Exception {
    }

    @Test
    public void endGame() throws Exception {
    }

    @Test
    public void setUpDecks() throws Exception {
    }

    @Test
    public void nextRound() throws Exception {
    }

    @Test
    public void nextTurn() throws Exception {
    }

    @Test
    public void nextPeriod() throws Exception {
    }

    @Test
    public void nextPhase() throws Exception {
    }

    @Test
    public void replaceCards() throws Exception {
    }

    @Test
    public void sweepActionSlots() throws Exception {
    }

    @Test
    public void rollDices() throws Exception {
    }

    @Test
    public void setupPlayersResources() throws Exception {
    }

    @Test
    public void setupGameContexts() throws Exception {
    }

    @Test
    public void changeCards() throws Exception {
    }

    @Test
    public void bonusTileSelectionPhase() throws Exception {
    }

    @Test
    public void leaderSelectionPhase() throws Exception {
    }

    private class TestAbstractConnection extends AbstractConnection {
        public void TestAbstractConnecton() {
            this.username = "aldo";
            this.networkController = new ServerNetworkController(this);
        }

        @Override
        public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {

        }

        @Override
        public void updateTowers(List<Tower> towers) {

        }

        @Override
        public void updateCouncilPalace(CouncilPalace councilPalace) {

        }

        @Override
        public void updateMarket(Market market) {

        }

        @Override
        public void updateProductionArea(WorkingArea productionArea) {

        }

        @Override
        public void updateHarvestArea(WorkingArea harvestArea) {

        }

        @Override
        public void updatePlayersData(List<Player> players) {

        }

        @Override
        public void updateDiceValues(List<Dice> diceValues) {

        }

        @Override
        public PlayerAction turnMainAction(Optional<Boolean> lastActionValid) {
            return null;
        }

        @Override
        public PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid) {
            return null;
        }

        @Override
        public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
            return null;
        }

        @Override
        public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
            return null;
        }

        @Override
        public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
            return null;
        }

        @Override
        public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
            return null;
        }

        @Override
        public Boolean churchSupport() {
            return null;
        }

        @Override
        public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
            return null;
        }
    }

}