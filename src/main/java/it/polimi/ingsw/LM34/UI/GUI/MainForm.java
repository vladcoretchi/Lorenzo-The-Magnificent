package it.polimi.ingsw.LM34.UI.GUI;


import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.UI.UIInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1500, 1400));
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);
        primaryStage.show();
        //TODO

        GridPane grid = (GridPane) root.lookup("#gridPaneResource");


        // Just to see that the lines are actually added
        grid.setGridLinesVisible(true);
        grid.setPrefWidth(200);


        for (Integer i = 1; i < 5; i++) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(Thread.currentThread().getContextClassLoader().getResource("images/developmentCards/Commercial_Hub.png").toExternalForm()));
            imageView.minHeight(200);
            imageView.minWidth(250);
            //grid.getRowConstraints().add(new RowConstraints(30));
            grid.getRowConstraints().add(new RowConstraints(30));
            grid.addRow(i, imageView);
        }


        primaryStage.show();



        //primaryStage.show();
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
