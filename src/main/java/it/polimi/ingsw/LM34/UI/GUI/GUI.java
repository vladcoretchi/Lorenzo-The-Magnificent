package it.polimi.ingsw.LM34.UI.GUI;


import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.*;
import it.polimi.ingsw.LM34.UI.UIInterface;
import it.polimi.ingsw.LM34.Utils.Configurator;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladc on 6/6/2017.
 */
public class GUI extends Application implements UIInterface {
    private AbstractClient networkClient;
    private ClientNetworkController networkController;
    private Parent root;
    private Stage primaryStage;
    private LoginDialog loginDialog;
    private PopupSlotBonus popupSlotBonus;
    private Scene guiScene;

    @FXML
    private javafx.scene.control.TextField username;
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


    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/gui.fxml"));

        this.primaryStage = new Stage();
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        prepareWindow();
        endGame();
        endGame();
        new PopupSlotBonus(9.0,10.0,new ResourcesBonus(new Resources(9,4,7,3,4,4,5),1)).start(primaryStage);
        /*----------GAME SETUPS----------*/
        //placeExcommunicationCards();
        loadTowersBonuses(); //TODO
        loadCouncilPrivilegesBonuses(); //TODO
        loadMarketBonuses(); //TODO
        loadFaithPath(); //TODO

        /*----------ROUND SETUPS--------*/
        loadCardsOnTowers();
        //sweepSlots();


        /*----------DIALOGS--------*/
        //servantsSelection(5,1);
        //curchReportDecision(4,2);
        //leaderCardAction(); //TODO
        //addPlayersInfo(root); //TODO
        //endGame();
        //familyMemberSelection();
        //resourchExchangeDialog//TODO
        //useCouncilPrivilegeDialog;

    }


    public void endGame() {
        try {
            new EndGameDialog().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void leaderCardAction() {
        try {
            Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("views/leaderCardAction.fxml"));
            primaryStage.setScene(new Scene(
                    root2, 500, 400));
            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO
    private void addPlayersInfo() {
        ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPanePaneResource");
        VBox content = new VBox();
        scrollPane.setContent(content);
        scrollPane.setPrefWidth(200);
    }

    private void prepareWindow() {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = gd.getDisplayMode().getWidth();
        double height = gd.getDisplayMode().getHeight();
        primaryStage.setMaxWidth(width);
        primaryStage.setMaxHeight(height);
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        primaryStage.setTitle("Lorenzo il Magnifico by CranioCreations");
        guiScene = new Scene(root, primaryStage.getMaxWidth(), primaryStage.getMaxHeight());
        primaryStage.setScene(guiScene);
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void loginResult(Boolean result) {
        if (result) {
            try {
                username.getScene().getWindow().hide();
                this.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public NetworkType connectionTypeSelection() {
        //TODO: not used
        return null;
    }

    @Override
    public Integer contextSelection(List<PlayerSelectableContexts> allContext) {
        return null;
    }

    @Override
    public String towerSlotSelection(Integer towerNumber, Integer towerFloor) {
        return null;
    }

    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        //networkClient.servantsSelection(servantsAvailable, minimumServantsRequested);
        return 0; //TODO

    }

    public Integer curchReportDecision() {
        CurchReportDialog dialog = new CurchReportDialog();
        return 0; //TODO
    }

    //TODO
    @Override
    public void show() {
        loginMenu();
    }

    public void doLogin() {
        if(rmiChoice.isSelected() && !socketChoice.isSelected()) //TODO
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

    @Override
    public Integer leaderCardAction(List<String> playerLeaderCards, LeaderCardsAction action) {
        return null;
    }

    @Override
    public Integer marketSlotSelection(Market market) {
        return null;
    }

    @Override
    public void workingArea(String workingAreaChoice, Player player) {
    }

    @Override
    public void printTowers(ArrayList<Tower> towers) {
        loadCardsOnTowers();
    }

    //TODO: remove this in UI interface
    @Override
    public void printGameBoard() {
        printTowers(null);
    }

    public String familyMemberSelection() {
        FamilyMemberSelectDialog dialog = new FamilyMemberSelectDialog();
        ArrayList<FamilyMember> members = new ArrayList<>();
        members.add(new FamilyMember(PawnColor.BLUE, DiceColor.ORANGE));
        members.add(new FamilyMember(PawnColor.BLUE, DiceColor.WHITE));
        members.add(new FamilyMember(PawnColor.BLUE, DiceColor.BLACK));
        members.add(new FamilyMember(PawnColor.BLUE, DiceColor.NEUTRAL));
        String selected = dialog.interactWithPlayer(members);
        System.out.println(selected);

        return null;
    }

    public void sweepSlots() {
        sweepMarketSlots();
        sweepCouncilPalace();
        sweepWorkingAreas();
        sweepTowersSlots();
    }

    public void loadCardsOnTowers() {

        //prepareWindow();
        ArrayList<AbstractDevelopmentCard> territoryCards;
        territoryCards = new ArrayList<>();
        territoryCards.add(new TerritoryCard("Castle", 2, 1, null, null));
        territoryCards.add(new TerritoryCard("Hermitage", 2, 1, null, null));
        territoryCards.add(new TerritoryCard("Estate", 2, 1, null, null));
        territoryCards.add(new TerritoryCard("Forest", 2, 1, null, null));

        Integer index = 0;
        for (AbstractDevelopmentCard card : territoryCards) {
            ImageView imageView = ((ImageView) root.lookup("#tower" + card.getColor().toString() + "_level" + index));
            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/developmentCards/territories/" + card.getName() + ".png").toExternalForm()));
            index++;
        }
    }

    public void placeExcommunicationCards() {
        ImageView imageView;
        for(Integer index = 1; index <= Configurator.TOTAL_PERIODS; index++) {
            imageView = ((ImageView) root.lookup("#excommunicationCard" + index));
            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/excommunicationTiles/excomm_" + index + "_5.png").toExternalForm()));
        }
    }

    @FXML
    public void placePawn(MouseEvent event) {
        Image image;
        String choosedPawn;

        ArrayList<FamilyMember> membersAvailable = new ArrayList<>();

        //TODO: get the list from the model instantiated in the client or sent by the controller

        String source = event.getPickResult().getIntersectedNode().getId();
        FamilyMemberSelectDialog dialog = new FamilyMemberSelectDialog();
        choosedPawn = dialog.interactWithPlayer(membersAvailable);

        //TODO: the server confirm that the move is valid before removing the card from the towers

        List<Node> nodes = towers.getChildren();
        for (Node node : nodes)
            if (node.getId() == source) {
            //TODO: set image of the pawn getting it from Resource/images/..
                //((ImageView) node).setImage(....getResource("images/pawns/"+ choosedPawn +".png");
            }
    }

    public void loadTowersBonuses() {
        List<TowerSlot> slots = new ArrayList<>();
        Resources resources = new Resources();
        //TODO: load bonuses from model available to the view
        Integer index = 0;
        for (TowerSlot slot : slots) {

            ImageView imageView = ((ImageView) root.lookup("#tower" +
                    slot.getCardStored().getColor().toString() + "Bonus" + index));
            resources = slot.getResourcesReward().getResources();
            for(ResourceType resType : ResourceType.values())
                if(resources.getResourceByType(resType) != 0)
                    imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/resources/" + resType + ".png").toExternalForm()));
            index++;

        }
    }

    @FXML
    public void popupBonus(MouseEvent event) {
        ActionSlot slot = new ActionSlot(true, 3, new ResourcesBonus(new Resources(3,4,4,3),1));
        String source = event.getPickResult().getIntersectedNode().getId();
        //TODO: get reward from source String
        Double coordinateX = event.getPickResult().getIntersectedPoint().getX();
        Double coordinateY = event.getPickResult().getIntersectedPoint().getY();
        System.out.println("Entrati alle coordinate: " + coordinateX.toString() + " " + coordinateY);
        popupSlotBonus =  new PopupSlotBonus(12.0,230.0,new ResourcesBonus(new Resources(3,4,4,3),1));


        try {
            popupSlotBonus.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadMarketBonuses() {
        //TODO
    }

    public void loadCouncilPrivilegesBonuses() {
        //TODO
    }

    public void loadFaithPath() {
        //TODO
    }

    @FXML
    public void buyCard(MouseEvent event) {
        Image image;
        String source = event.getPickResult().getIntersectedNode().getId();

        //TODO: called after place pawn after the server has confirmed the validity

        List<Node> nodes = towers.getChildren();
        for (Node node : nodes)
            if (node.getId() == source) {
                ((ImageView) node).setImage(null);
            }
    }

    public void sweepMarketSlots() {
        Integer index = 0;
        for(index= 0; index<4; index++) {
            ImageView imageView = ((ImageView) root.lookup("#marketActionSlot" + index));
            imageView.setImage(null);
        }
    }
    private void sweepCouncilPalace() {
        ImageView imageView = ((ImageView) root.lookup("#councilPalace"));
        imageView.setImage(null);
    }
    private void sweepTowersSlots() {
        for(Integer index = 0; index < Configurator.MAX_TOWER_LEVELS; index++)
            for(DevelopmentCardColor color : DevelopmentCardColor.values())
                if(color.toString() != "MULTICOLOR") {
                    ImageView imageView = ((ImageView) root.lookup("#tower" + color.toString() + "Bonus" + index));
                    imageView.setImage(null);
                }
    }
    private void sweepWorkingAreas() {
        Integer index = 0;
        for(index= 0; index<2; index++) {
            ImageView imageView = ((ImageView) root.lookup("#harvestArea" + index));
            imageView.setImage(null);
        }
        index = 0;
        for(index= 0; index<2; index++) {
            ImageView imageView = ((ImageView) root.lookup("#productionArea" + index));
            imageView.setImage(null);
        }
    }

    //TODO: remove just after tests
    public static void main(String [] args) {
        GUI gui = new GUI();
        gui.show();
    }


       /* for (Integer i = 1; i < 5; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
            imageView.minHeight(200);
            imageView.minWidth(250);
            imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    System.out.println("Mouse entered");
                }
            });
            //scrollPane.getRowConstraints().add(new RowConstraints(30));
            content.getChildren().add(imageView);
        }*/

}