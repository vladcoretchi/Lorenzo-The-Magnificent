package it.polimi.ingsw.LM34.UI.GUI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.PathType;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
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
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts.*;
import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.*;
import static it.polimi.ingsw.LM34.UI.UIConnectionInfo.*;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * this class implements in graphic mode all method specified by {@link UIInterface}
 */
public class GUI extends Application implements UIInterface {
    private AbstractClient networkClient;
    private ClientNetworkController networkController;
    private PersonalBoardView personalBoardView;

    /**
     * Javafx Component Variables
     */
    private Parent root;
    private Stage primaryStage;
    private LoginDialog loginDialog;
    private Stage loginStage;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Label playerLoginError;
    @FXML private RadioButton rmiChoice;
    @FXML private RadioButton socketChoice;
    @FXML private AnchorPane login;
    @FXML private Group towers;
    @FXML private Group slots;
    @FXML private ToggleButton leaderCardActions;

    /**Game Objects**/
    private List<Player> players;
    private List<ExcommunicationCard> excommunicationCards;
    private List<Tower> towersSpaces;
    private WorkingArea productionArea;
    private WorkingArea harvestArea;
    private Market market;
    private CouncilPalace palace;
    private Map<Integer, Integer> faithPath;
    private Map<Integer, Integer> mapMilitaryPointsForTerritories;
    private Map<Integer, Integer> mapCharactersToVictoryPoints;
    private Map<Integer, Integer> mapTerritoriesToVictoryPoints;

    private PlayerAction playerAction;
    private CountDownLatch actionLatch;

    private static final String IMAGE_CARD_TRANSPARENT = "images/transparent.png";
    private static final String IMAGE_SLOT_TRANSPARENT = "images/transparentSlot.png";
    private static final String IMAGE_PAWNS_PATH = "images/pawns/";

    /**
     * Start the application
     * @param stage
     * @throws Exception if the JavaFx application cannot be instantiated
     */
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/gui.fxml"));
        this.primaryStage = new Stage();
        prepareWindow();

        this.playerAction = null;
    }

    /**
     * @param bonusTiles that the players has the opportunity to choose one from
     * @return the bonus tile the player wants to have during the game
     */
    @Override
    public Integer bonusTileSelection(List<BonusTile> bonusTiles) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> {

            this.loginStage.hide();
            this.loginStage.close();
            this.start(new Stage());
            return new BonusTileDialog().interactWithPlayer(bonusTiles);
        });
        return RunLaterTask(uiTask);
    }

    /**
     * @param leaderCards that the players has the opportunity to choose one from
     * @return the leader the player wants to have as one of his 4 leaders
     */
    @Override
    public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) {
        FutureTask<String> uiTask = new FutureTask<>(() -> new LeaderCardsView().leaderCardSelection(leaderCards));
        String leaderCardName = RunLaterTask(uiTask);
        for(int i = 0; i < leaderCards.size(); i++)
            if(leaderCards.get(i).getName() == leaderCardName)
                return i;
        return -1;
    }

    /**
     * Setups the application stage and scene before showing it
     */
    private void prepareWindow() {
        this.primaryStage = new Stage();
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        primaryStage.setTitle("Lorenzo il Magnifico by CranioCreations");
        primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest(event -> stop(event));
        primaryStage.show();
    }

    /**
     * Receives the login operation result
     * @param result login result
     */
    @Override
    public void loginResult(Boolean result) {
        if (result) {
            FutureTask<Void> uiTask = new FutureTask<>(() -> {
                WaitingAnimation wa = new WaitingAnimation();
                Scene scene = new Scene(wa, 422, 368);
                this.loginStage = (Stage) this.username.getScene().getWindow();
                this.loginStage.setTitle("Waiting Room");
                this.loginStage.setScene(scene);
                return null;
            });
            Platform.runLater(uiTask);
        }
        else {
            FutureTask<Void> uiTask = new FutureTask<>(() -> {
                playerLoginError.setText("invalid username or password");
                playerLoginError.setVisible(true);
                return null;
            });

            Platform.runLater(uiTask);
        }
    }

    @Override
    public void loadExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        this.excommunicationCards = excommunicationCards;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            ImageView imageView;
            for (ExcommunicationCard ex : excommunicationCards) {
                imageView = (ImageView) root.lookup("#excommunicationCard" + ex.getPeriod());
                imageView.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/excommunicationTiles/excomm_" + ex.getPeriod() + "_" + ex.getNumber() + ".png")
                        .toExternalForm()));
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    /**
     * Show the {@link TowerSlot} rewards and cards stored
     * @param towers updated from the server
     */
    @Override
    public void updateTowers(List<Tower> towers) {
        this.towersSpaces = new ArrayList<>();
        this.towersSpaces = towers;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            ImageView cardView;
            /**
             * Register the slots to {@link MouseEvent} so that they will show the
             * {@link Resources} provided in a {@link PopupSlotBonus}
             */
            List<TowerSlot> slotsInTower;
            ImageView slotView;
            for (Tower towerSpace : this.towersSpaces) {
                String cardColor = towerSpace.getCardColor().toString();
                for (Integer level = 0; level < Configurator.MAX_TOWER_LEVELS; level++) {
                    TowerSlot towerSlot = towerSpace.getTowerSlots().get(level);

                    /***Load cards***/
                    cardView = (ImageView) root.lookup("#tower" + towerSpace.getCardColor().toString() + "_level" + level);
                    if (towerSlot.getCardStored() != null) {
                        String devType = towerSlot.getCardStored().getColor().getDevType();
                        cardView.setImage(new Image(Thread.currentThread()
                                .getContextClassLoader().getResource("images/developmentCards/" + devType + "/" + towerSlot.getCardStored().getName() + ".png")
                                .toExternalForm()));
                    } else {
                        cardView.setImage(new Image(Thread.currentThread()
                                .getContextClassLoader().getResource(IMAGE_CARD_TRANSPARENT)
                                .toExternalForm()));
                    }

                    /***Load bonuses***/
                    slotView = (ImageView) root.lookup("#tower" + cardColor + "bonus" + level);
                    slotView.setOnMouseEntered(new SlotMouseEvent(towerSpace.getTowerSlots().get(level).getResourcesReward()));
                    if (!towerSlot.getFamilyMembers().isEmpty()) {
                        PawnColor pawnColor = towerSlot.getFamilyMembers().get(0).getFamilyMemberColor();
                        slotView.setImage(new Image(Thread.currentThread()
                                .getContextClassLoader().getResource("images/pawns/" + pawnColor.toString() + ".png").toExternalForm()));
                    } else
                        slotView.setImage(new Image(Thread.currentThread().getContextClassLoader().getResource("images/transparentSlot.png").toExternalForm()));
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
            /**
             * Fill the advanced {@link ActionSlot} with the pawns placed inside
             */
            StackPane palacePane = (StackPane) root.lookup("#councilPalace");
            HBox hSlots = new HBox();
            hSlots.setSpacing(10);
            ImageView image;
            List<FamilyMember> pawnsInPalace = new ArrayList<>();
            ActionSlot palaceSlot = palace.getActionSlot();
            palacePane.setOnMouseEntered(new SlotMouseEvent(palaceSlot.getResourcesReward()));
            pawnsInPalace = palaceSlot.getFamilyMembers();

            if (pawnsInPalace.isEmpty())
                palacePane.getChildren().removeAll(palacePane.getChildren());
            else {
                for (Integer index = 0; index < pawnsInPalace.size(); index++) {
                    image = new ImageView();
                    image = setImage(IMAGE_PAWNS_PATH + pawnsInPalace.get(index).getFamilyMemberColor() + ".png");
                    hSlots.getChildren().add(image);
                    hSlots.setHgrow(image, Priority.ALWAYS);
                }
                palacePane.getChildren().add(hSlots);
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    /**
     * Shows the rewards that each MarketSlots provides and the {@link FamilyMember} placed in them
     * @param market updated from the server
     */
    @Override
    public void updateMarket(Market market) {
        this.market = market;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            PawnColor pawnColor;

            List<ActionSlot> marketSlots = this.market.getActionSlots();
            for (Integer index = 0; index < marketSlots.size(); index++) {
                ImageView slotView = (ImageView) root.lookup("#marketActionSlot" + index);
                slotView.setOnMouseEntered(new SlotMouseEvent(marketSlots.get(index).getResourcesReward()));

                if (!marketSlots.get(index).getFamilyMembers().isEmpty()) {
                    pawnColor = marketSlots.get(index).getFamilyMembers().get(0).getFamilyMemberColor();
                    slotView.setImage(new Image(Thread.currentThread()
                            .getContextClassLoader().getResource("images/pawns/" + pawnColor.toString() + ".png").toExternalForm()));
                } else
                    slotView.setImage(new Image(Thread.currentThread().getContextClassLoader().getResource("images/transparentSlot.png").toExternalForm()));
                }

            return null;
        });
        Platform.runLater(uiTask);
    }

    /**
     * Shows the {@link FamilyMember} placed in it
     * @param productionArea updated from the server
     */
    @Override
    public void updateProductionArea(WorkingArea productionArea) {
        this.productionArea = productionArea;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
        /**
         * Fill the single {@link ActionSlot} with the pawn placed inside
         */
        FamilyMember pawnInSingleSlot = null;
        if(!this.productionArea.getSingleSlot().getFamilyMembers().isEmpty())
            pawnInSingleSlot = this.productionArea.getSingleSlot().getFamilyMembers().get(0);

        ImageView imageSingle = (ImageView) root.lookup("#productionArea" + 0);

            if (pawnInSingleSlot != null) {
            imageSingle.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource(IMAGE_PAWNS_PATH + pawnInSingleSlot.getFamilyMemberColor() + ".png")
                    .toExternalForm()));
        } else
            imageSingle = setImage(IMAGE_SLOT_TRANSPARENT);

        /**
         * Fill the advanced {@link ActionSlot} with the pawns placed inside
         */
        StackPane advancedSlot = (StackPane) root.lookup("#productionArea" + 1);
        HBox hSlots = new HBox();
        hSlots.setSpacing(10);
        ImageView image;
        List<FamilyMember> pawnsInAdvancedSlot = new ArrayList<>();
        pawnsInAdvancedSlot = this.productionArea.getAdvancedSlot().getFamilyMembers();

        if (pawnsInAdvancedSlot.isEmpty())
            advancedSlot.getChildren().removeAll(advancedSlot.getChildren());
        else {
            for (Integer index = 0; index < pawnsInAdvancedSlot.size(); index++) {
                image = new ImageView();
                image = setImage(IMAGE_PAWNS_PATH + pawnsInAdvancedSlot.get(index).getFamilyMemberColor() + ".png");
                hSlots.getChildren().add(image);
                hSlots.setHgrow(image, Priority.ALWAYS);
            }
            advancedSlot.getChildren().add(hSlots);
        }

        return null;
        });
        Platform.runLater(uiTask);
    }

    /**
     * Shows the {@link FamilyMember} placed in it
     * @param harvestArea updated from the server
     */
    @Override
    public void updateHarvestArea(WorkingArea harvestArea) {
        this.harvestArea = harvestArea;

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            /**
             * Fill the single {@link ActionSlot} with the pawn placed inside
             */
            FamilyMember pawnInSingleSlot = this.harvestArea.getSingleSlot().getFamilyMembers().get(0);
            ImageView imageSingle = (ImageView) root.lookup("#harvestArea" + 0);
            if (pawnInSingleSlot != null) {
                imageSingle.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource(IMAGE_PAWNS_PATH + pawnInSingleSlot.getFamilyMemberColor() + ".png")
                        .toExternalForm()));
            } else
                imageSingle = setImage(IMAGE_SLOT_TRANSPARENT);

            /**
             * Fill the advanced {@link ActionSlot} with the pawns placed inside
             */
            StackPane advancedSlot = (StackPane) root.lookup("#harvestArea" + 1);
            HBox hSlots = new HBox();
            hSlots.setSpacing(10);
            ImageView image;
            List<FamilyMember> pawnsInAdvancedSlot = new ArrayList<>();
            pawnsInAdvancedSlot = this.harvestArea.getAdvancedSlot().getFamilyMembers();

            if (pawnsInAdvancedSlot.isEmpty())
                advancedSlot.getChildren().removeAll(advancedSlot.getChildren());
            else {
                for (Integer index = 0; index < pawnsInAdvancedSlot.size(); index++) {
                    image = new ImageView();
                    image = setImage(IMAGE_PAWNS_PATH + pawnsInAdvancedSlot.get(index).getFamilyMemberColor() + ".png");
                    hSlots.getChildren().add(image);
                    hSlots.setHgrow(image, Priority.ALWAYS);
                }
                advancedSlot.getChildren().add(hSlots);
            }
            return null;
        });
        Platform.runLater(uiTask);
    }

    /**
     *Shows the info about each player in game including name, color, cards, resources
     * @param players informations updated from the server
     */
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
                playerInfo.setManaged(true);

                playerInfo.setOnMouseClicked(new PlayerInfoClickEvent(player));

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

            return null;
        });
        Platform.runLater(uiTask);
    }

    /**
     * Show the values of the 3 dices in the current round
     * @param dicesValues updated from the server
     */
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

    /**
     * @param familyMembers available to the player
     * @return familyMember selected from the player
     */
    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> new FamilyMemberSelectDialog().interactWithPlayer(primaryStage, familyMembers));
        return RunLaterTask(uiTask);
    }

    /**
     * this method will be called when user need to use some servants to improve his pawn's value
     * @param servantsAvailable user's available servants
     * @param minimumServantsRequested minimum number of servants to complete action
     * @return how many servants user has decided to use
     */
    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> {
            try {
                return new UseServantsDialog().interactWithPlayer(servantsAvailable, minimumServantsRequested);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
                return 1;
            }
        });
        return RunLaterTask(uiTask);
    }

    /**
     * @param choices available about resource exchange bonuses
     * @return the reward opted by the player
     */
    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> new ResourceExchangeDialog().interactWithPlayer(choices));
        return RunLaterTask(uiTask);
    }

    /**
     * @param leaderCards that the game consider the player to have available
     * @return the leader selected and the action to perform on him
     */
    @Override
    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leaderCards) {
        FutureTask<Pair<String, LeaderCardsAction>> uiTask = new FutureTask<>(() -> new LeaderCardsView().interactWithPlayer(leaderCards));
        return RunLaterTask(uiTask);
    }

    /**
     * The Curch Report decision asked from the game to the player
     * @return the decision of the player
     */
    @Override
    public Boolean churchSupport() {
        FutureTask<Boolean> uiTask = new FutureTask<>(() -> new ChurchReportDialog().interactWithPlayer());
        return RunLaterTask(uiTask);
    }

    /**
     * @param availableBonuses, set from the game
     * @return the choice made by the player among the options in input
     */
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

    /**
     * Try to login to the server
     * if things goes wrong show a message inside the {@link LoginDialog}
     */
    public void doLogin() {
        playerLoginError.setVisible(false);

        String playerUsername = username.getText();
        String playerPassword = password.getText();

        if("".equals(playerUsername) || "".equals(playerPassword)) {

            playerLoginError.setText("username and password fields\ncannot be left blank");

            playerLoginError.setVisible(true);
        }

        else {

            if (rmiChoice.isSelected())
                this.networkClient = new RMIClient(SERVER_IP, RMI_PORT, this);
            else
                this.networkClient = new SocketClient(SERVER_IP, SOCKET_PORT, this);

            this.networkController = this.networkClient.getNetworkController();

            this.networkClient.login(playerUsername, playerPassword);
        }
    }

    /**
     * Login Menu shown before starting to visualize the gameboard
     */
    @Override
    public void loginMenu() {
        this.loginDialog = new LoginDialog();
        this.loginDialog.show();
    }
    @Override
    public void showMapCharactersToVictoryPoints() {
        new PathTypesVisualizationDialog().interactWithPlayer(PathType.ENDING_GAME_CHARACTERS_VICTORY_POINTS, mapCharactersToVictoryPoints);
    }

    @Override
    public void showMapTerritoriesToVictoryPoints() {
        new PathTypesVisualizationDialog().interactWithPlayer(PathType.ENDING_GAME_TERRITORIES_VICTORY_POINTS, mapTerritoriesToVictoryPoints);
    }

    @Override
    public void showFaithPath() {
        new PathTypesVisualizationDialog().interactWithPlayer(PathType.FAITH_PATH, faithPath);
    }

    @Override
    public void showMilitaryPointsForTerritories() {
        new PathTypesVisualizationDialog().interactWithPlayer(PathType.MILITARY_POINTS_FOR_TERRITORIES, mapMilitaryPointsForTerritories);
    }

    /**
     * Main action of a turn performed by the player
     * @param lastActionValid if the server has invalidated the action of the player, this object
     * contains the detailed information to display to the player in an informative popup
     * @return the {@link PlayerAction} to send to the server
     */
    @Override
    public PlayerAction turnMainAction(Optional<Exception> lastActionValid) {
        FutureTask<CountDownLatch> uiTaskActionInit = new FutureTask<>(() -> {
            this.playerAction = new PlayerAction();
            this.actionLatch = new CountDownLatch(1);

        /*Show the player a popup indicating that the last action he did was not valid
        and what went wrong with it*/
        lastActionValid.ifPresent( invalidAction ->
                new LastActionInvalid().interactWithPlayer(invalidAction.getMessage())
        );

        try {
            /*Set the listener on the passTurn button*/
            Button leaderButton = (Button) root.lookup("#leaderCardAction");
            leaderButton.setOnMouseClicked(new LeaderButtonClickEvent(this.actionLatch, this.playerAction));

            /*Set the listener on the leader button*/
            Button endTurn = (Button) root.lookup("#endTurn");
            endTurn.setOnMouseClicked(new endTurnClick(this.actionLatch, this.playerAction));

            /*Set the listener on tower slots*/
            for (Tower towerSpace : this.towersSpaces) {
                String cardColor = towerSpace.getCardColor().toString();
                for (Integer level = 0; level < Configurator.MAX_TOWER_LEVELS; level++) {
                    ImageView slotView = (ImageView) root.lookup("#tower" + cardColor + "bonus" + level);
                    slotView.setOnMouseClicked(new PlacePawn(towerSpace.getTowerSlots().get(level), actionLatch, playerAction));
                }
            }

            /*Set the listener on palace*/
            StackPane palacePane = (StackPane) root.lookup("#councilPalace");
            palacePane.setOnMouseClicked(new PlacePawn(palace.getActionSlot(), actionLatch, playerAction));

            /*Set the listener on the market*/
            List<ActionSlot> marketSlots = this.market.getActionSlots();
            for (Integer index = 0; index < marketSlots.size(); index++) {
                ImageView slotView = (ImageView) root.lookup("#marketActionSlot" + index);
                slotView.setOnMouseClicked(new PlacePawn(marketSlots.get(index), actionLatch, playerAction));
            }

            /*Set the listener on harvest area*/
            ImageView harvestSingle = (ImageView) root.lookup("#harvestArea" + 0);
            harvestSingle.setOnMouseClicked(new PlacePawn(this.harvestArea.getSingleSlot(), actionLatch, playerAction));

            StackPane harvestAdvanced = (StackPane) root.lookup("#harvestArea" + 1);
            harvestAdvanced.setOnMouseClicked(new PlacePawn(this.harvestArea.getAdvancedSlot(), actionLatch, playerAction));

            /*Set the listener on production area*/
            ImageView productionSingle = (ImageView) root.lookup("#productionArea" + 0);
            productionSingle.setOnMouseClicked(new PlacePawn(this.productionArea.getSingleSlot(), actionLatch, playerAction));

            StackPane productionAdvanced = (StackPane) root.lookup("#productionArea" + 1);
            productionAdvanced.setOnMouseClicked(new PlacePawn(this.productionArea.getAdvancedSlot(), actionLatch, playerAction));
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
        }
        return this.actionLatch;

        });

        try {
            CountDownLatch waitLatch = RunLaterTask(uiTaskActionInit);
            if(waitLatch != null)
                waitLatch.await();
        } catch (InterruptedException e) {
            LOGGER.log(Level.INFO, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        FutureTask<PlayerAction> uiTaskActionResult = new FutureTask<>(() -> this.playerAction);
        return RunLaterTask(uiTaskActionResult);
    }

    @Override
    public PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) {
        FutureTask<PlayerAction> uiTask = new FutureTask<>(() -> null);
        return RunLaterTask(uiTask);
    }

    private class endTurnClick implements  EventHandler<Event> {
        private CountDownLatch waitLatch;
        private PlayerAction action;

        public endTurnClick(CountDownLatch latch, PlayerAction action) {
            this.waitLatch = latch;
            this.action = action;
        }

        @Override
        public void handle(Event event) {
            try {
                this.action.setValues(null, null);
                this.waitLatch.countDown();
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    /**
     * This class is called for the {@link it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard}
     * that have as an alternative requirement option the payment of MILITARY POINTS
     */
    @Override
    public Boolean alternativeRequirementsPayment() {
    FutureTask<Boolean> uiTask = new FutureTask<>(() -> new AlternativeRequirementPaymentDialog().interactWithPlayer());
        return RunLaterTask(uiTask);
    }

    @Override
    public void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints) {
        this.mapTerritoriesToVictoryPoints = mapTerritoriesToVictoryPoints;
    }

    @Override
    public void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapVictoryPointsForTerritories) {
        this.mapMilitaryPointsForTerritories = mapVictoryPointsForTerritories;
    }

    @Override
    public void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints) {
        this.mapCharactersToVictoryPoints = mapCharactersToVictoryPoints;
    }

    @Override
    public void loadFaithPath(Map<Integer, Integer> faithPath) {
        this.faithPath = faithPath;
    }

    /**
     * Receive the action performed by the player and send the infos about the move to the server
     */
    private class PlacePawn implements EventHandler<Event> {
        private ActionSlot slotClicked;
        private CountDownLatch waitLatch;
        private PlayerAction action;

        public PlacePawn(ActionSlot slotClicked, CountDownLatch latch, PlayerAction action) {
            this.slotClicked = slotClicked;
            this.action = action;
            this.waitLatch = latch;
        }

        /**
         * Construct the {@link PlayerAction} in order to send it to the server
         * @param event from which to extract the slot clicked
         */
        @Override
        public void handle(Event event) {
            PlayerAction playerAction = new PlayerAction();
            /**
             * Identify the {@link ImageView} or {@link StackPane} that generated the {@link MouseEvent}
             */
            Node source = (Node) event.getSource();
            String slotId = source.getId();

            /**
             * Extract info about the {@link ActionSlot} or {@link TowerSlot}'s level
             */
            Integer level = 0;
            Pattern patternLevel = Pattern.compile("\\d");
            Matcher matchedLevel = patternLevel.matcher(slotId);
            while (matchedLevel.find()) {
                level = Integer.parseInt(matchedLevel.group());
            }
            if(slotId.contains("productionArea"))
                playerAction = new PlayerAction(PRODUCTION_AREA_CONTEXT, level);
            else if(slotId.contains("harvestArea"))
                playerAction = new PlayerAction(HARVEST_AREA_CONTEXT, level);
            else if(slotId.contains("councilPalace"))
                playerAction = new PlayerAction(COUNCIL_PALACE_CONTEXT, null);
            else if(slotId.contains("marketActionSlot"))
                playerAction = new PlayerAction(MARKET_AREA_CONTEXT, level);
            else if(slotId.contains("tower")) {
                /**
                 *Extract info about the {@link it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardDeck}
                 */
                Pattern patternColor = Pattern.compile("[A-Z]+");
                Matcher matchedColor = patternColor.matcher(slotId);
                String color ="";

                while (matchedColor.find()) {
                    color = matchedColor.group();
                }

                DevelopmentCardColor towerType = null;
                switch(color) {
                    case "GREEN": towerType = GREEN;
                        break;
                    case "BLUE": towerType = BLUE;
                        break;
                    case "PURPLE": towerType = PURPLE;
                        break;
                    case "YELLOW": towerType = YELLOW;
                        break;
                    default:
                        break;
                }
                /**
                 * And now fill the {@link PlayerAction} with the info of the {@link TowerSlot} selected
                 */
                playerAction = new PlayerAction(TOWERS_CONTEXT,
                                    new ImmutablePair<DevelopmentCardColor, Integer>(towerType, level));
            }

            this.action.setValues(playerAction.getContext(), playerAction.getAction());
            this.waitLatch.countDown();
        }
    }

    /**
     * Show games results
     * @param players in game
     */
    @Override
    public void endGame(List<Player> players) {
        try {
            new EndGameDialog(players).start(new Stage());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    /**
     * Inform the player that is turn has ended
     */
    @Override
    public void endTurn() {
        new EndTurnPopup().interactWithPlayer();
    }

    /**
     * Inform the player that he is no more connected to the game
     */
    @Override
    public void disconnectionWarning() {
        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            new DisconnectionWarning().interactWithPlayer();
            return null;
        });
        Platform.runLater(uiTask);
    }

    /**
     * Shows multiple kind of info about players
     * @param infoType information about a player {@link GameInformationType}
     * @param playerName the associated player
     * @param playerColor the color associated to the player to whom the info concerns
     */
    @Override
    public void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor) {
        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            new GameInformationDialog().interactWithPlayer(infoType, playerName, playerColor);
            return null;
        });
        Platform.runLater(uiTask);
    }

    /**
     * WebView for showing to the user an animation while waiting for other players
     */
    private class WaitingAnimation extends Region {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        /**
         * Constructor
         */
        public WaitingAnimation(){
            webEngine.load(Thread.currentThread().getContextClassLoader().getResource("LM34Rain.html").toExternalForm());
            webView.setZoom(0.25);
            getChildren().add(webView);
        }
    }

    /**
     * Shows the {@link it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard}
     * of the {@link Player} {@link Label} clicked
     */
    private class PlayerInfoClickEvent implements EventHandler<Event> {
        private Player player;

        public PlayerInfoClickEvent(Player player) {
            this.player = player;
        }

        @Override
        public void handle(Event event) {
            try {
                personalBoardView = new PersonalBoardView(player);
                personalBoardView.start(primaryStage);
                if(player.getPlayerName().equalsIgnoreCase(username.getText())) {
                    showFaithPath();
                    showMapCharactersToVictoryPoints();
                    showMapTerritoriesToVictoryPoints();
                    showMilitaryPointsForTerritories();
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    /**
     * Each slot shows the reward it provides to the player when the MouseEvent is triggered
     */
    private class SlotMouseEvent implements EventHandler<Event> {
        private ResourcesBonus reward;

        public SlotMouseEvent(ResourcesBonus reward) {
            this.reward = reward;
        }

        @Override
        public void handle(Event event) {
            try {
                new PopupSlotBonus((MouseEvent) event, reward).start(primaryStage);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    /**
     * Notify the server that the player wants to access the leaders available and the action
     */
    private class LeaderButtonClickEvent implements EventHandler<Event> {
        private CountDownLatch waitLatch;
        private PlayerAction action;

        public LeaderButtonClickEvent(CountDownLatch latch, PlayerAction action) {
            this.waitLatch = latch;
            this.action = action;
        }

        @Override
        public void handle(Event event) {
            try {
                this.action.setValues(PlayerSelectableContexts.LEADER_CARDS_CONTEXT, null);
                this.waitLatch.countDown();
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }

    /**
     * @param uiTask to perform in a JavaFx thread
     */
        private <T> T RunLaterTask(FutureTask<T> uiTask) {
            Platform.runLater(uiTask);
            try {
                return uiTask.get();
            } catch (ExecutionException | InterruptedException e) {
                LOGGER.log(Level.FINEST, e.getMessage(), e);
                return null;
            }
        }

    /**
     * this function will interrupt all Threads and will close the application
     * @param event this event will be managed when the exit button will be clicked
     */
    public void stop(WindowEvent event) {
        primaryStage.setOnCloseRequest(event1 -> Platform.exit());
        primaryStage.close();
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            thread.interrupt();
        }
    }

    /**
     * A useful method to save lines of code and increase readability and maintainability
     * @param path to the image to load the rapresentation of the game compoment
     * @return the {@link ImageView} that shows the image associated to the game component
     */
    private ImageView setImage(String path) {
        ImageView image = new ImageView();
        image.setImage(new Image(Thread.currentThread()
                .getContextClassLoader().getResource(path)
                .toExternalForm()));
        return image;
    }
}
