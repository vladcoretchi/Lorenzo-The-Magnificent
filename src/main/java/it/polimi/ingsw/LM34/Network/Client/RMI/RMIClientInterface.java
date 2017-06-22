package it.polimi.ingsw.LM34.Network.Client.RMI;

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
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by vladc on 5/30/2017.
 */
public interface RMIClientInterface extends Remote {

    void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) throws  RemoteException;
    void updateTowers(List<Tower> towers) throws  RemoteException;
    void updateCouncilPalace(CouncilPalace councilPalace) throws  RemoteException;
    void updateMarket(Market market) throws  RemoteException;
    void updateProductionArea(WorkingArea productionArea) throws  RemoteException;
    void updateHarvestArea(WorkingArea harvestArea) throws  RemoteException;
    void updatePlayersData(List<Player> players) throws  RemoteException;
    void updateDiceValues(List<Dice> diceValues) throws  RemoteException;

    PlayerAction turnMainAction(Boolean lastActionValid) throws  RemoteException;

    PlayerAction turnSecondaryAction(Boolean lastActionValid) throws  RemoteException;


    Integer familyMemberSelection(List<FamilyMember> familyMembers) throws  RemoteException;

    Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) throws  RemoteException;

    Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) throws  RemoteException;

    Integer leaderCardSelection(List<LeaderCard> leaderCards, LeaderCardsAction action) throws  RemoteException;

    Boolean churchSupport() throws  RemoteException;

    Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) throws  RemoteException;
}
