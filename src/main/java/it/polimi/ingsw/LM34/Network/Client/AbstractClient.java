package it.polimi.ingsw.LM34.Network.Client;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by vladc on 5/23/2017.
 */
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

    public abstract void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards);
    public abstract void updateTowers(List<Tower> towers);
    public abstract void updateCouncilPalace(CouncilPalace councilPalace);
    public abstract void updateMarket(Market market);
    public abstract void updateProductionArea(WorkingArea productionArea);
    public abstract void updateHarvestArea(WorkingArea harvestArea);
    public abstract void updatePlayersData(List<Player> players);
    public abstract void updateDiceValues(List<Dice> diceValues);

    public abstract PlayerAction turnMainAction(Boolean lastActionValid);

    public abstract PlayerAction turnSecondaryAction(Boolean lastActionValid);


    public abstract Integer familyMemberSelection(List<FamilyMember> familyMembers);

    public abstract Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested);

    public abstract Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices);

    public abstract Integer leaderCardSelection(List<LeaderCard> leaderCards, LeaderCardsAction action);

    public abstract Boolean churchSupport();

    public abstract Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses);
}
