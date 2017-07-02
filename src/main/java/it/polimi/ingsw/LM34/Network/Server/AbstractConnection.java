package it.polimi.ingsw.LM34.Network.Server;

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

public abstract class AbstractConnection {
    protected ServerNetworkController networkController;
    protected String username;

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

    public abstract void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards);
    public abstract void updateTowers(List<Tower> towers);
    public abstract void updateCouncilPalace(CouncilPalace councilPalace);
    public abstract void updateMarket(Market market);
    public abstract void updateProductionArea(WorkingArea productionArea);
    public abstract void updateHarvestArea(WorkingArea harvestArea);
    public abstract void updatePlayersData(List<Player> players);
    public abstract void updateDiceValues(List<Dice> diceValues);

    public abstract PlayerAction turnMainAction(Optional<Exception> lastActionValid);

    public abstract PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid);

    public abstract Integer familyMemberSelection(List<FamilyMember> familyMembers);

    public abstract Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);

    public abstract Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices);

    public abstract Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards);

    public abstract Boolean churchSupport();

    public abstract Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses);
}
