package it.polimi.ingsw.LM34.UI.GUI;


import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.UI.UIInterface;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladc on 6/6/2017.
 */
public class MainForm extends Application implements UIInterface {

    @Override
    public void show() {
        launch(null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/mainForm.fxml"));
        prepareWindow(primaryStage, root);
        addPlayersInfo(root);





        primaryStage.show();



        //primaryStage.show();
    }

    private void addPlayersInfo(Parent root) {
        ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPanePaneResource");
        VBox content = new VBox();
        scrollPane.setContent(content);
        // Just to see that the lines are actually added
        scrollPane.setPrefWidth(30);

        for (Integer i = 1; i < 5; i++) {
            ImageView imageView = new ImageView();
            imageView.maxWidth(300);
            imageView.maxHeight(200);
            imageView.setImage(new Image(Thread.currentThread().getContextClassLoader().getResource("images/personalBoards/Personal_Board_Cut.png").toExternalForm()));
            //imageView.minHeight(100);
            //imageView.minWidth(200);
            imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                   // imageView.setImage(new Image("images/personalBoards/Personal_Board_Cut.png"));
                }
            });
            //scrollPane.getRowConstraints().add(new RowConstraints(30));
            content.getChildren().add(imageView);
        }

    }

    private void prepareWindow(Stage primaryStage, Parent root) {
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        primaryStage.setTitle("Lorenzo il Magnifico by CranioCreations");
        primaryStage.setScene(new Scene(root, 2700, 2565));
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void loginMenu() {

    }

    @Override
    public void loginResult(Boolean result) {

    }

    @Override
    public NetworkType connectionTypeSelection() {
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
        return null;
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

    @Override
    public void printGameBoard() {

    }
}
