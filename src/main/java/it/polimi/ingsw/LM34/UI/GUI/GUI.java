package it.polimi.ingsw.LM34.UI.GUI;


import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.*;
import it.polimi.ingsw.LM34.UI.UIInterface;
import it.polimi.ingsw.LM34.Utils.Configurator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.ingsw.LM34.Enums.Model.DiceColor.*;

/**
 * Created by vladc on 6/6/2017.
 */
public class GUI extends Application implements UIInterface {
    private AbstractClient networkClient;
    private ClientNetworkController networkController;
    private PersonalBoardView personalBoardView;

    private Parent root;
    private Stage primaryStage;
    private LoginDialog loginDialog;
    private PopupSlotBonus popupSlotBonus;
    private Scene guiScene;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private RadioButton rmiChoice;
    @FXML
    private RadioButton socketChoice;
    @FXML
    private AnchorPane login;
    @FXML
    private Group towers;
    @FXML
    private Group slots;

    private List<Player> players;
    private List<ExcommunicationCard> excommunicationCards;
    private List<Tower> towersSpaces;
    private WorkingArea productionArea;
    private WorkingArea harvestArea;
    private Market market;
    private CouncilPalace palace;


    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/gui.fxml"));

        this.primaryStage = new Stage();
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        prepareWindow();
        /*----------GAME SETUPS----------*/
        //loadFaithPath(); //TODO
        //leaderCardAction(); //TODO

        /*----------TURN SETUPS--------*/
        //updatePlayersInfo();
        //updateDiceValues();

        /*----------ROUND SETUPS--------*/
        //sweepSlots();

        /*----------DIALOGS--------*/
        //servantsSelection(5,1);
        //churchReportDecision(4,2);
        //leaderCardAction(); //TODO
        //addPlayersInfo(root); //TODO
        //endGame();
        //familyMemberSelection();*/

        /*List<Resources> resList = new ArrayList<>();
        resList.add(tempRes);
        resList.add(new Resources(3,2,1,0));
        new UseCouncilPrivilegeDialog().interactWithPlayer(resList);*/

        /*List<Pair<Resources, ResourcesBonus>> listPairExchange = new ArrayList<>();
        Resources tempRes = new Resources(1,2,9,0);
        ResourcesBonus tempResBon = new ResourcesBonus(new Resources(), 3);
        Resources tempRes2 = new Resources(0,0,72,5);
        ResourcesBonus tempResBon2 = new ResourcesBonus(new Resources(3,2,1,4), 1);
        listPairExchange.add(new ImmutablePair<>(tempRes, tempResBon));
        listPairExchange.add(new ImmutablePair<>(tempRes2, tempResBon2));
        Integer result = new ResourceExchangeDialog().interactWithPlayer(listPairExchange);*/

        /*List<Resources> resList = new ArrayList<>();
        resList.add(tempRes);
        resList.add(new Resources(3,2,1,0));
        new UseCouncilPrivilegeDialog().interactWithPlayer(resList);*/

        /*List<LeaderCard> leaders = new ArrayList<>();
        leaders.add(new LeaderCard("Sisto IV", null,null, true));
        leaders.add(new LeaderCard("Pico Della Mirandola", null,null, true));
        leaders.add(new LeaderCard("Giovanni Dalle Bande Nere", null,null, true));
        leaders.add(new LeaderCard("Lucrezia Borgia", null,null, true));
        leaders.add(new LeaderCard("Sandro Botticelli", null,null, true));
        leaderCardSelection(leaders);*/


        Dice orange = new Dice(ORANGE); orange.rollDice();
        Dice black = new Dice(BLACK); orange.rollDice();
        Dice white = new Dice(WHITE); orange.rollDice();
        List<Dice> dices = new ArrayList<>();
        dices.add(orange); dices.add(black); dices.add(white);
        updateDiceValues(dices);


        //new LeaderCardsView(leaders).start(primaryStage);

    }

    @Override
    public void endGame(List<Player> players) {
        try {
            new EndGameDialog(players).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*TODO: public void leaderCardAction() {
        try {
            Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("views/leaderCardAction.fxml"));
            primaryStage.setScene(new Scene(
                    root2, 500, 400));
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void prepareWindow() {

        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        primaryStage.setTitle("Lorenzo il Magnifico by CranioCreations");
        guiScene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(guiScene);
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void loginResult(Boolean result) {
        if (result) {
            FutureTask<Void> uiTask = new FutureTask<>(() -> {
                this.username.getScene().getWindow().hide();
                this.start(new Stage());
                return null;
            });
            Platform.runLater(uiTask);
        }
    }

    @Override
    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        this.excommunicationCards = excommunicationCards;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            ImageView imageView;
            for (Integer index = 1; index <= Configurator.TOTAL_PERIODS; index++) {
                imageView = ((ImageView) root.lookup("#excommunicationCard" + this.excommunicationCards.get(index).getPeriod()));
                imageView.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/excommunicationTiles/excomm_" + index + "_" + this.excommunicationCards.get(index).getNumber() + ".png")
                        .toExternalForm()));
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    @Override
    public void updateTowers(List<Tower> towers) {
        this.towersSpaces = towers;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            for (Tower tower : this.towersSpaces) {
                /***Load cards***/
                Integer indexCard = 0;
                for (AbstractDevelopmentCard card : tower.getCardsStored()) {
                    ImageView imageView = ((ImageView) root.lookup("#tower" + tower.getCardColor().toString() + "_level" + indexCard));
                    if(card != null) {
                        String devType = tower.getCardColor().getDevType();
                        imageView.setImage(new Image(Thread.currentThread()
                                .getContextClassLoader().getResource("images/developmentCards/" + devType + card.getName() + ".png")
                                .toExternalForm()));
                    }
                    else {
                        imageView.setImage(new Image(Thread.currentThread()
                                .getContextClassLoader().getResource("images/transparent.png")
                                .toExternalForm()));
                    }
                    indexCard++;
                }
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    @Override
    public void updateCouncilPalace(CouncilPalace councilPalace) {
        this.palace = councilPalace;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            StackPane palacePane = (StackPane) root.lookup("#councilPalace");
            ImageView imageView;
            if(this.palace.getOccupyingPawns().size() <= 0)
                palacePane.getChildren().removeAll(palacePane.getChildren());
            else
                for(FamilyMember pawn : this.palace.getOccupyingPawns()) {
                    imageView = new ImageView(new Image(Thread.currentThread().getContextClassLoader().getResource("images/pawns" + pawn.getFamilyMemberColor() + ".png").toExternalForm()));
                    imageView.setTranslateX(20);
                    palacePane.getChildren().add(imageView);
                }
            return null;
        });
        Platform.runLater(uiTask);
    }

    @Override
    public void updateMarket(Market market) {
        this.market = market;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            PawnColor pawnColor;
            Integer index;
            List<ActionSlot> marketSlots = this.market.getMarketSlots();
            for (index = 0; index < marketSlots.size(); index++) {
                ImageView imageView = ((ImageView) root.lookup("#marketActionSlot" + index));
                if (marketSlots.get(index).getFamilyMember().getFamilyMemberColor() != null) {
                    pawnColor = marketSlots.get(index).getFamilyMember().getFamilyMemberColor();
                    imageView.setImage(new Image(Thread.currentThread()
                            .getContextClassLoader().getResource("images/pawns/" + pawnColor.toString() + ".png").toExternalForm()));
                } else
                    imageView.setImage(new Image(Thread.currentThread().getContextClassLoader().getResource("images/transparentSlot.png").toExternalForm()));
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    @Override
    public void updateProductionArea(WorkingArea productionArea) {
        this.productionArea = productionArea;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            FamilyMember pawnInSingleSlot = this.productionArea.getSingleSlot().getFamilyMember();
            ImageView imageSingle = ((ImageView) root.lookup("#productionArea" + 0));
            if(pawnInSingleSlot != null) {
                imageSingle.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/pawns/" + pawnInSingleSlot.getFamilyMemberColor() + ".png")
                        .toExternalForm()));
            } else
                imageSingle.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/transparent.png")
                        .toExternalForm()));

            List<FamilyMember> pawnsInAdvancedSlot = new ArrayList<>();
            this.productionArea.getAdvancedSlots().forEach(s -> pawnsInAdvancedSlot.add(s.getFamilyMember()));
            StackPane advancedSlot = (StackPane) root.lookup("#productionArea" +1);
            ImageView imageAdvanced;
            if(pawnsInAdvancedSlot.size() <= 0)
                advancedSlot.getChildren().removeAll(advancedSlot.getChildren());
            else {
                for (Integer i = 0; i < pawnsInAdvancedSlot.size(); i++) {
                    imageAdvanced = new ImageView();
                    imageAdvanced.setImage(new Image(Thread.currentThread()
                            .getContextClassLoader().getResource("images/pawns/" + pawnsInAdvancedSlot.get(i).getFamilyMemberColor() + ".png")
                            .toExternalForm()));
                    imageAdvanced.setTranslateX(20);

                    advancedSlot.getChildren().add(imageAdvanced);
                }
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    @Override
    public void updateHarvestArea(WorkingArea harvestArea) {
        this.harvestArea = harvestArea;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            FamilyMember pawnInSingleSlot = this.harvestArea.getSingleSlot().getFamilyMember();
            ImageView imageSingle = ((ImageView) root.lookup("#harvestArea" + 0));
            if(pawnInSingleSlot != null) {
                imageSingle.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/pawns/" + pawnInSingleSlot.getFamilyMemberColor() + ".png")
                        .toExternalForm()));
            } else
                imageSingle.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/transparentSlot.png")
                        .toExternalForm()));

            List<FamilyMember> pawnsInAdvancedSlot = new ArrayList<>();
            this.harvestArea.getAdvancedSlots().forEach(s -> pawnsInAdvancedSlot.add(s.getFamilyMember()));
            StackPane advancedSlot = (StackPane) root.lookup("#harvestArea" + 1);
            ImageView imageAdvanced;

            if(pawnsInAdvancedSlot.size() <= 0)
                advancedSlot.getChildren().removeAll(advancedSlot.getChildren());
            else {
                for (Integer i = 0; i < pawnsInAdvancedSlot.size(); i++) {
                    imageAdvanced = new ImageView();
                    imageAdvanced.setImage(new Image(Thread.currentThread()
                            .getContextClassLoader().getResource("images/pawns/" + pawnsInAdvancedSlot.get(i).getFamilyMemberColor() + ".png")
                            .toExternalForm()));
                    imageAdvanced.setTranslateX(20);

                    advancedSlot.getChildren().add(imageAdvanced);
                }
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    @Override
    public void updatePlayersData(List<Player> players) {
        this.players = players;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            Pane playerInfo;
            Label playerName;
            Text value;
            DropShadow borderGlow;
            Integer numPlayer = 1;

            for (Player player : this.players) {
                playerInfo = (Pane) root.lookup("#playerInfo" + numPlayer);
                playerInfo.setBackground(new Background(new BackgroundFill(Color.valueOf(player.getPawnColor().toString()), CornerRadii.EMPTY, Insets.EMPTY)));
                playerInfo.setVisible(true);

                playerInfo.setOnMouseClicked(new PlayerClickEvent(player));

                playerName = (Label) root.lookup("#player" + numPlayer);
                playerName.setText(player.getPlayerName());
                for (ResourceType resType : ResourceType.values()) {
                    value = (Text) root.lookup("#" + resType.toString() + "_player" + numPlayer + "_value");
                    value.setText(player.getResources().getResourceByType(resType).toString());

                    borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setSpread(0.4);
                    borderGlow.setRadius(25.0);
                    borderGlow.setColor(Color.WHITE);
                    borderGlow.setWidth(35);
                    borderGlow.setHeight(35);
                    value.setEffect(borderGlow);
                }
                numPlayer++;
            }

            for(; numPlayer < 5; numPlayer++) {
                playerInfo = (Pane) root.lookup("#playerInfo" + numPlayer);
                playerInfo.setVisible(false);
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    @Override
    public void updateDiceValues(List<Dice> dicesValues) {
        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            Text diceSlot;
            for(Dice dice : dicesValues) {
                diceSlot = (Text) root.lookup("#diceSlot" + dice.getColor());
                diceSlot.setText(dice.getValue().toString());
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    @Override
    public PlayerAction turnMainAction(Optional<Boolean> lastActionValid) {
        FutureTask<PlayerAction> uiTask = new FutureTask<>(() -> null);
        return RunLaterTask(uiTask);
    }

    @Override
    public PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid) {
        FutureTask<PlayerAction> uiTask = new FutureTask<>(() -> null);
        return RunLaterTask(uiTask);
    }

    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> new FamilyMemberSelectDialog().interactWithPlayer(familyMembers));
        return RunLaterTask(uiTask);
    }

    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> new UseServantsDialog().interactWithPlayer(servantsAvailable, minimumServantsRequested));
        return RunLaterTask(uiTask);
    }

    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> new ResourceExchangeDialog().interactWithPlayer(choices));
        return RunLaterTask(uiTask);
    }

    @Override
    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
        FutureTask<Pair<String, LeaderCardsAction>> uiTask = new FutureTask<>(() -> new LeaderCardsView().interactWithPlayer(leaderCards));
        return RunLaterTask(uiTask);
    }

    @Override
    public Boolean churchSupport() {
        FutureTask<Boolean> uiTask = new FutureTask<>(() -> new ChurchReportDialog().interactWithPlayer());
        return RunLaterTask(uiTask);
    }

    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> new UseCouncilPrivilegeDialog().interactWithPlayer(availableBonuses));
        return RunLaterTask(uiTask);
    }

    @Override
    public void show() {
        Platform.setImplicitExit(true);
        loginMenu();
    }

    public void doLogin() {
        if(rmiChoice.isSelected())
            this.networkClient = new RMIClient(SERVER_IP, RMI_PORT, this);
        else
            this.networkClient = new SocketClient(SERVER_IP, SOCKET_PORT, this);

        this.networkController = this.networkClient.getNetworkController();
        this.networkClient.login(username.getText(), password.getText());
    }

    @Override
    public void loginMenu() {
        this.loginDialog = new LoginDialog();
        this.loginDialog.show();
    }

    //TODO
    @FXML
    public void placePawn(MouseEvent event) {
        Image image;

        ArrayList<FamilyMember> membersAvailable = new ArrayList<>();

        //TODO: get the list from the model instantiated in the client or sent by the controller

        String source = event.getPickResult().getIntersectedNode().getId();
        FamilyMemberSelectDialog dialog = new FamilyMemberSelectDialog();

        //TODO: the server confirm that the move is valid before removing the card from the towers

        List<Node> nodes = towers.getChildren();
        for (Node node : nodes)
            if (node.getId() == source) {
            //TODO: set image of the pawn getting it from Resource/images/..
                //((ImageView) node).setImage(....getResource("images/pawns/"+ choosedPawn +".png");
            }
    }

    @FXML
    public void popupTowerSlotBonus(MouseEvent event) {
        TowerSlot towerSlot = null;
        List<TowerSlot> slotsInTower = new ArrayList<>();
        String color = new String();
        Integer level = 0;
        /*Extract tower info*/
        String id = ((ImageView) event.getSource()).getId();
        Pattern patternColor = Pattern.compile("[A-Z]+");
        Matcher matchedColor = patternColor.matcher(id);
        while (matchedColor.find()) {
            color = matchedColor.group();
        }
        /*Extract level info*/
        Pattern patternLevel = Pattern.compile("\\d");
        Matcher matchedLevel = patternLevel.matcher(id);
        while (matchedLevel.find()) {
            level = Integer.parseInt(matchedLevel.group());
        }
        /*Get the towerSlot that generated the event*/
        try {
            for(Tower tower : towersSpaces)
            if(tower.getCardColor().toString().equalsIgnoreCase(color)) {
                slotsInTower = (ArrayList<TowerSlot>) tower.getTowerSlots();
                for (TowerSlot slot : slotsInTower)
                    if (slot.getLevel() == level)
                        towerSlot = slot;
            }

            new PopupSlotBonus(event, towerSlot.getResourcesReward()).start(primaryStage);
        } catch(Exception e) { System.out.println("Finchè il server non passa le torri riempite, il PopupSlot non andrà"); }
    }

    @FXML
    public void popupPalaceBonus(MouseEvent event) {
        try {
            new PopupSlotBonus(event, palace.getReward()).start(primaryStage);
        } catch(Exception e) { System.out.println("Finchè il server non passa le torri riempite, il PopupSlot non andrà"); }
    }

    @FXML
    public void popupMarketBonus(MouseEvent event) {
        ActionSlot actionSlot = null;
        String color = new String();
        Integer marketSlotID = 0;
        String id = ((ImageView) event.getSource()).getId();
        /*Extract slot info*/
        Pattern patternLevel = Pattern.compile("\\d");
        Matcher matchedLevel = patternLevel.matcher(id);
        while (matchedLevel.find()) {
            marketSlotID = Integer.parseInt(matchedLevel.group());
        }
        /*Get the actionSlot that generated the event*/
        actionSlot = market.getMarketSlots().get(marketSlotID);
        try {
            new PopupSlotBonus(event, actionSlot.getResourcesReward()).start(primaryStage);
        } catch (Exception e) {
            System.out.println("Finchè il server non passa le torri riempite, il PopupSlot non andrà");
        }
    }

    public void loadFaithPath() {
        //TODO
    }

    //TODO: keep this?
    @FXML
    public void buyCard(MouseEvent event) {
        Image image;
            Object source = event.getSource();
           ImageView imageView = (ImageView) source;
        imageView.setImage(new Image(Thread.currentThread()
                .getContextClassLoader().getResource("images/transparent.png").toExternalForm()));
    }

    //TODO: remove just after tests
    public static void main(String [] args) {
        GUI gui = new GUI();
        gui.show();
    }

    private class PlayerClickEvent implements EventHandler<Event> {
        private Player player;

        public PlayerClickEvent(Player player) {
            this.player = player;
        }

        @Override
        public void handle(Event event) {
            try {
                PersonalBoardView personalBoardView = new PersonalBoardView(player);
                personalBoardView.start(primaryStage);
            } catch(Exception e) {e.printStackTrace();}
        }
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