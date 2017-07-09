package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

/**
 * Created by GiulioComi on 07/06/2017.
 */

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Player;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Show end game results of players
 */
public class EndGameDialog {
    List<Player> players;
    @FXML
    private BarChart<String, Number> chart;

    public EndGameDialog(List<Player> players) {
        this.players = players;
    }

    public void start() throws Exception {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> pointsChart =
                new BarChart<>(xAxis, yAxis);

        xAxis.setLabel("Players");
        yAxis.setLabel("VictoryPoints");
        xAxis.setTickLabelsVisible(true);
        yAxis.setTickLabelsVisible(true);
        yAxis.setAutoRanging(true);
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(5);
        yAxis.setMinorTickVisible(false);

        XYChart.Series period3 = new XYChart.Series();
        period3.setName("Period 3");
        for(Player player : players)
            period3.getData().add(new XYChart.Data(player.getPlayerName(), player.getResources().getResourceByType(ResourceType.VICTORY_POINTS)));

        pointsChart.getData().addAll(period3);

        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        stage.setFullScreen(false);
        Scene scene = new Scene(pointsChart);
        scene.getStylesheets().add(EndGameDialog.class.getResource("/css/endGameDialog.css").toExternalForm());
        stage.setScene(scene);

        pointsChart.setOnKeyPressed(new EventHandler<KeyEvent>()  {

            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            }
        });

        stage.show();
    }

}
