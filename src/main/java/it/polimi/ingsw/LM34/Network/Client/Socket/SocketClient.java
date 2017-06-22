package it.polimi.ingsw.LM34.Network.Client.Socket;

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
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.Network.Server.Socket.RequestToServer;
import it.polimi.ingsw.LM34.UI.UIInterface;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by vladc on 5/25/2017.
 */
public class SocketClient extends AbstractClient {
    private static ClientInputListener inputListener;

    private static Socket socket;
    private static ObjectInputStream inStream;
    private static ObjectOutputStream outStream;

    public SocketClient(String serverIP, Integer port, UIInterface ui) {
        try {
            socket = new Socket(serverIP, port);
            outStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outStream.flush();
            inStream = new ObjectInputStream(new DataInputStream(new BufferedInputStream(socket.getInputStream())));

            this.networkController = new ClientNetworkController(this);
            this.ui = ui;

            inputListener = new ClientInputListener();
            inputListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectInputStream getInputStream() {
        return inStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outStream;
    }

    private class ClientInputListener extends Thread {
        private boolean run = true;

        @Override
        public void run() {
            while(this.run) {
                try {
                    String request = inStream.readUTF();

                    RequestToClient.valueOf(request).readAndHandle(SocketClient.this);
                } catch (IOException e) {
                    this.terminate();
                }
            }
        }

        public void terminate() {
            this.run = false;
        }
    }

    @Override
    public void login(String username, String password) {
        try {
            this.outStream.writeUTF(RequestToServer.LOGIN.name());
            this.outStream.writeUTF(username);
            this.outStream.writeUTF(password);
            this.outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        this.networkController.setExcommunicationCards(excommunicationCards);
    }

    @Override
    public void updateTowers(List<Tower> towers) {
        this.networkController.updateTowers(towers);
    }

    @Override
    public void updateCouncilPalace(CouncilPalace councilPalace) {
        this.networkController.updateCouncilPalace(councilPalace);
    }

    @Override
    public void updateMarket(Market market) {
        this.networkController.updateMarket(market);
    }

    @Override
    public void updateProductionArea(WorkingArea productionArea) {
        this.networkController.updateProductionArea(productionArea);
    }

    @Override
    public void updateHarvestArea(WorkingArea harvestArea) {
        this.networkController.updateHarvestArea(harvestArea);
    }

    @Override
    public void updatePlayersData(List<Player> players) {
        this.networkController.updatePlayersData(players);
    }

    @Override
    public void updateDiceValues(List<Dice> diceValues) {
        this.networkController.updateDiceValues(diceValues);
    }

    @Override
    public PlayerAction turnMainAction(Boolean lastActionValid) {
        return this.networkController.turnMainAction(Optional.ofNullable(lastActionValid));
    }

    @Override
    public PlayerAction turnSecondaryAction(Boolean lastActionValid) {
        return this.networkController.turnSecondaryAction(Optional.ofNullable(lastActionValid));
    }

    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        return this.networkController.familyMemberSelection(familyMembers);
    }

    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        return this.networkController.servantsSelection(servantsAvailable, minimumServantsRequested);
    }

    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        return this.networkController.resourceExchangeSelection(choices);
    }

    @Override
    public Integer leaderCardSelection(List<LeaderCard> leaderCards, LeaderCardsAction action) {
        return this.networkController.leaderCardSelection(leaderCards, action);
    }

    @Override
    public Boolean churchSupport() {
        return this.networkController.churchSupport();
    }

    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        return this.networkController.selectCouncilPrivilegeBonus(availableBonuses);
    }
}
