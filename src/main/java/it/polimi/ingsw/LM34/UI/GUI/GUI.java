package it.polimi.ingsw.LM34.UI.GUI;


import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.CurchReportDialog;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.EndGameDialog;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.FamilyMemberSelectDialog;
import it.polimi.ingsw.LM34.UI.GUI.GuiViews.LoginDialog;
import it.polimi.ingsw.LM34.UI.UIInterface;
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
    public void start(Stage stage) throws Exception {
        this.primaryStage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/gui.fxml"));

        prepareWindow();

        /*----------ROUND SETUPS--------*/
        loadCardsOnTowers();
        placeExcommunicationCards();
        sweepMarketSlots();
        sweepWorkingAreas();
        sweepCouncilPalace();

        /*----------DIALOGS--------*/
        //servantsSelection(5,1);
        //curchReportDecision(4,2);
        //leaderCardAction(); //TODO
        //addPlayersInfo(root);
        //connectionTypeSelection();
        //endGame(primaryStage);
        //familyMemberSelection();

    }

    private void sweepCouncilPalace() {
        ImageView imageView = ((ImageView) root.lookup("#councilPalace"));
        imageView.setImage(null);
    }

    public void endGame() {
        try {
            new EndGameDialog().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void leaderCardAction() {
        try {
            Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("gui/leaderCardAction.fxml"));
            primaryStage.setScene(new Scene(
                    root2, 500, 400));
            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

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
        primaryStage.setScene(new Scene(root, primaryStage.getMaxWidth(), primaryStage.getMaxHeight()));
        primaryStage.setFullScreen(true);
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
    public NetworkType connectionTypeSelection() {/*VOID*/ return null;}

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

    }

    //TODO
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
        Group slots = ((Group)root.lookup("#slots"));
        List<Node> nodes = slots.getChildren();
        for (Node node : nodes)
                ((ImageView) node).setImage(null);

    }

    public void loadCardsOnTowers() {

        //prepareWindow();
        ArrayList<AbstractDevelopmentCard> territoryCards;
        territoryCards = new ArrayList<>();
        territoryCards.add(new TerritoryCard("Castle", 2, 1, null, null));
        territoryCards.add(new TerritoryCard("Hermitage", 2, 1, null, null));
        territoryCards.add(new TerritoryCard("Estate", 2, 1, null, null));
        territoryCards.add(new TerritoryCard("Forest", 2, 1, null, null));

        //new PersonalBoardController().loadCardOnPersonalBoard(territoryCards);
        Integer index = 0;


        for (AbstractDevelopmentCard card : territoryCards) {
            System.out.println("#tower" + card.getColor().toString() + "_level" + index);
            System.out.println("images/developmentCards/territories/" + card.getName() + ".png");
            //ImageView imageView = ((ImageView)root.lookup("#tower" + card.getColor().toString() + "_level" + index));
            //imageView.setImage(new Image(Thread.currentThread()
            ImageView imageView = ((ImageView)root.lookup("#tower" + card.getColor().toString() + "_level" + index));
            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/developmentCards/territories/" + card.getName() + ".png").toExternalForm()));
            index++;
        }

    }

    public void placeExcommunicationCards() {
        Integer index = 1;
            ImageView imageView = ((ImageView)root.lookup("#excommunicationCard"+ index));
            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/excommunicationTiles/excomm_" +  index +"_5.png").toExternalForm()));
            index++;
        }

    public void sweepMarketSlots() {
        Integer index = 0;
        for(index= 0; index<4; index++) {
            ImageView imageView = ((ImageView) root.lookup("#marketActionSlot" + index));
            imageView.setImage(null);
        }
    }

    public void sweepWorkingAreas() {
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


    @FXML
    public void buyCard(MouseEvent event) {
        Image image;
        String source2 = event.getPickResult().getIntersectedNode().getId();

        List<Node> nodes = towers.getChildren();
        for (Node node : nodes)
            if (node.getId() == source2) {
                ((ImageView) node).setImage(null);
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