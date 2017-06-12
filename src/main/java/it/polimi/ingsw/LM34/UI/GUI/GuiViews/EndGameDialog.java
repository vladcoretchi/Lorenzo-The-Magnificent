package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

/**
 * Created by GiulioComi on 07/06/2017.
 */

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Show end game results of players
 */
public class EndGameDialog implements DialogInterface {

    @FXML
    private BarChart<String, Number> chart;

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/endGameResult.fxml"));

        Player player1 = new Player("player 1", PawnColor.BLUE, new PersonalBoard());
        Player player2 = new Player("player 2", PawnColor.RED, new PersonalBoard());
        Player player3 = new Player("player 3", PawnColor.GREEN, new PersonalBoard());

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);

        xAxis.setLabel("Players");
        yAxis.setLabel("VictoryPoints");

        XYChart.Series period1 = new XYChart.Series();
        period1.setName("Period 1");
        period1.getData().add(new XYChart.Data(player1.getPlayerName(), player1.getResources().getResourceByType(ResourceType.VICTORY_POINTS)));
        period1.getData().add(new XYChart.Data(player2.getPlayerName(), 20148.82));
        period1.getData().add(new XYChart.Data(player3.getPlayerName(), 10000));

        XYChart.Series period2 = new XYChart.Series();
        period2.setName("Period 2");
        period2.getData().add(new XYChart.Data(player1.getPlayerName(), 57401.85));
        period2.getData().add(new XYChart.Data(player2.getPlayerName(), 41941.19));
        period2.getData().add(new XYChart.Data(player3.getPlayerName(), 45263.37));

        XYChart.Series period3 = new XYChart.Series();
        period3.setName("Period 3");
        period3.getData().add(new XYChart.Data(player1.getPlayerName(), 45000.65));
        period3.getData().add(new XYChart.Data(player2.getPlayerName(), 44835.76));
        period3.getData().add(new XYChart.Data(player3.getPlayerName(), 18722.18));

        // Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(period1, period2, period3);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(primaryStage);
        stage.setFullScreen(false);
        stage.setScene(new Scene(bc));
        stage.show();
        /*popup.getContent().add(bc);
        popup.show(primaryStage);*/
    }

    @Override
    public void setStyle(Dialog dialog) {

    }
}
