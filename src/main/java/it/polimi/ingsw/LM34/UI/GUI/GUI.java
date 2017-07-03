package it.polimi.ingsw.LM34.UI.GUI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.*;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
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
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
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
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Label playerLoginError;
    @FXML private RadioButton rmiChoice;
    @FXML private RadioButton socketChoice;
    @FXML private AnchorPane login;
    @FXML private Group towers;
    @FXML private Group slots;

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
    private Optional<PlayerSelectableContexts> selectedContext;

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



        FamilyMember pawn = new FamilyMember(PawnColor.BLUE, DiceColor.ORANGE);
        FamilyMember pawn2 = new FamilyMember(PawnColor.RED, DiceColor.ORANGE);
        FamilyMember pawn3 = new FamilyMember(PawnColor.BLUE, DiceColor.ORANGE);
        FamilyMember pawn4 = new FamilyMember(PawnColor.RED, DiceColor.ORANGE);
        FamilyMember pawn5 = new FamilyMember(PawnColor.GREEN, DiceColor.ORANGE);
        Market market = Configurator.getMarket();
        market.insertFamilyMember(1, pawn);
        market.insertFamilyMember(3, pawn2);
        WorkingArea productionArea = Configurator.getProductionArea();
        WorkingArea harvestArea = Configurator.getHarvestArea();

        productionArea.getSingleSlot().insertFamilyMember(pawn);
        productionArea.getAdvancedSlots().get(0).insertFamilyMember(pawn2);
        productionArea.getAdvancedSlots().get(0).insertFamilyMember(pawn);
        harvestArea.getSingleSlot().insertFamilyMember(pawn);
        harvestArea.getAdvancedSlots().get(0).insertFamilyMember(pawn3);
        harvestArea.getAdvancedSlots().get(0).insertFamilyMember(pawn4);
        harvestArea.getAdvancedSlots().get(0).insertFamilyMember(pawn5);
        updateMarket(market);
        updateProductionArea(productionArea);
        updateHarvestArea(harvestArea);
        towersSpaces = Configurator.getTowers();
        updateTowers(towersSpaces);


        CouncilPalace councilPalace = Configurator.getPalace();
        councilPalace.getActionSlot().insertFamilyMember(pawn);
        councilPalace.getActionSlot().insertFamilyMember(pawn2);
        councilPalace.getActionSlot().insertFamilyMember(pawn3);

        updateCouncilPalace(councilPalace);


        this.selectedContext = Optional.empty();
    }

    /**
     * @param bonusTiles that the players has the opportunity to choose one from
     * @return the bonus tile the player wants to have during the game
     */
    @Override
    public Integer bonusTileSelection(List<BonusTile> bonusTiles) {
        FutureTask<Integer> uiTask = new FutureTask<>(() ->  new BonusTileDialog().interactWithPlayer(bonusTiles));
        return RunLaterTask(uiTask);
    }

    /**
     * @param leaderCards that the players has the opportunity to choose one from
     * @return the leader the player wants to have as one of his 4 leaders
     */
    @Override
    public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) {
        FutureTask<Integer> uiTask = new FutureTask<>(() ->  new LeaderSelectionPhaseDialog().interactWithPlayer(leaderCards));
        return RunLaterTask(uiTask);
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
                this.username.getScene().getWindow().hide();
                this.start(new Stage());
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
            for (Integer index = 1; index <= Configurator.TOTAL_PERIODS; index++) {
                imageView = (ImageView) root.lookup("#excommunicationCard" + this.excommunicationCards.get(index).getPeriod());
                imageView = setImage("images/excommunicationTiles/excomm_" + index + "_" + this.excommunicationCards.get(index).getNumber() + ".png");
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
            for (Tower tower : this.towersSpaces) {
                /***Load cards***/
                Integer indexCard = 0;
                for (AbstractDevelopmentCard card : tower.getCardsStored()) {
                    cardView = (ImageView) root.lookup("#tower" + tower.getCardColor().toString() + "_level" + indexCard);
                    if (card != null) {
                        String devType = tower.getCardColor().getDevType();
                        cardView = setImage("images/developmentCards/" + devType + "/" + card.getName() + ".png");
                    } else {
                        cardView = setImage(IMAGE_CARD_TRANSPARENT);
                    }
                    indexCard++;
                }
                /**
                 * Register the slots to {@link MouseEvent} so that they will show the
                 * {@link Resources} provided in a {@link PopupSlotBonus}
                 */
                    List<TowerSlot> slotsInTower;
                    Integer level;
                    ImageView slotView;
                    for (Tower towerSpace : this.towersSpaces) {
                        level = 0;
                        String cardColor = towerSpace.getCardColor().toString();
                        /***Load bonuses***/
                        for (; level < Configurator.MAX_TOWER_LEVELS; level++) {
                            slotView = (ImageView) root.lookup("#tower" + cardColor + "bonus" + level);
                            slotView.setOnMouseEntered(new SlotMouseEvent(towerSpace.getTowerSlots().get(level).getResourcesReward()));
                            slotView.setOnMouseClicked(new PlacePawn(towerSpace.getTowerSlots().get(level)));
                        }
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
            palacePane.setOnMouseClicked(new PlacePawn(palace.getActionSlot()));
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
                slotView.setOnMouseClicked(new PlacePawn(marketSlots.get(index)));

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
        FamilyMember pawnInSingleSlot = this.productionArea.getSingleSlot().getFamilyMembers().get(0);
        ImageView imageSingle = (ImageView) root.lookup("#productionArea" + 0);
        imageSingle.setOnMouseClicked(new PlacePawn(this.productionArea.getSingleSlot()));

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
        advancedSlot.setOnMouseClicked(new PlacePawn(this.productionArea.getAdvancedSlots().get(0)));

        HBox hSlots = new HBox();
        hSlots.setSpacing(10);
        ImageView image;
        List<FamilyMember> pawnsInAdvancedSlot = new ArrayList<>();
        pawnsInAdvancedSlot = this.productionArea.getAdvancedSlots().get(0).getFamilyMembers();

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
            imageSingle.setOnMouseClicked(new PlacePawn(this.harvestArea.getSingleSlot()));
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
            advancedSlot.setOnMouseClicked(new PlacePawn(this.harvestArea.getAdvancedSlots().get(0)));
            HBox hSlots = new HBox();
            hSlots.setSpacing(10);
            ImageView image;
            List<FamilyMember> pawnsInAdvancedSlot = new ArrayList<>();
            pawnsInAdvancedSlot = this.harvestArea.getAdvancedSlots().get(0).getFamilyMembers();

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
     * @return familyMember choosed from the player
     */
    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        FutureTask<Integer> uiTask = new FutureTask<>(() -> new FamilyMemberSelectDialog().interactWithPlayer(familyMembers));
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
        FutureTask<Integer> uiTask = new FutureTask<>(() -> new UseServantsDialog().interactWithPlayer(servantsAvailable, minimumServantsRequested));
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
     * @param leadersOwned that the game consider the player to have available
     * @return the leader choosed and the action to perform on him
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

    @Override
    public PlayerAction turnMainAction(Optional<Exception> lastActionValid) {
        FutureTask<PlayerAction> uiTask = new FutureTask<>(() -> null);
        return RunLaterTask(uiTask);
    }

    @Override
    public PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) {
        FutureTask<PlayerAction> uiTask = new FutureTask<>(() -> null);
        return RunLaterTask(uiTask);
    }

    /**
     * This class is called for the {@link it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard}
     * that have as an alternative requirement option the payment of {@link ResourceType.MILITARY_POINTS}
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
        public PlacePawn(ActionSlot slotClicked) {
            this.slotClicked = slotClicked;
        }

        /**
         * Construct the {@link PlayerAction} in order to send it to the server
         * @param event from which to extract the slot clicked
         */
        @Override
        public void handle(Event event) {
            PlayerAction playerAction;
            /**
             * Identify the {@link ImageView} or {@link StackPane} that generated the {@link MouseEvent}
             */
            Node source = (Node) event.getSource();
            String slotId = source.getId();
            System.out.println(slotId);

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
            else if(slotId.contains("marketSlot"))
                playerAction = new PlayerAction(MARKET_AREA_CONTEXT, level);
            else if(slotId.contains("tower")) {
                /**
                 *Extract info about the {@link it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardDeck}
                 */
                Pattern patternColor = Pattern.compile("[A-Z]+");
                Matcher matchedColor = patternColor.matcher(slotId);
                String color = new String();

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
            //TODO: send to server the choice
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
     * Notify the server that the player wants to access the leaders available and the action
     */
    @FXML
    public void showLeaderCardsActions() {
        //TODO: will this call mainaction?
        this.selectedContext = Optional.of(PlayerSelectableContexts.LEADER_ACTIVATE_OR_DISCARD_CONTEXT);

        LeaderCardsView leaderCardsView;
        List<LeaderCard> leaderCards = new ArrayList<>();
        LeaderCard leaderCard = new LeaderCard("Lorenzo de Medici", null, null, true);
        LeaderCard leaderCard1 = new LeaderCard("Cesare Borgia", null, null, true);
        leaderCards.add(leaderCard);
        leaderCards.add(leaderCard1);
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

    public static void main (String[] args) {
        GUI gui = new GUI();
        //gui.show();
        Configurator.loadConfigs();
        gui.launch();
    }
}
