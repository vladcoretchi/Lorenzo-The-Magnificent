package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.MULTICOLOR;

public class PersonalBoardView extends Application {
    private Parent root;
    private Map<Integer, Integer> mapCharactersToVictoryPoints;
    private Map<Integer, Integer> mapTerritoriesToVictoryPoints;
    private Map<Integer, Integer> mapMilitaryPointsForTerritories;
    private Player player;
    private Text militaryPointsValue;

    public PersonalBoardView(Player playerReceived, Map<Integer, Integer> mapTerritoriesToVictoryPoints, Map<Integer, Integer> mapMilitaryPointsForTerritories, Map<Integer, Integer> mapCharactersToVictoryPoints) {
        this.player = playerReceived;
        this.mapCharactersToVictoryPoints = mapCharactersToVictoryPoints;
        this.mapTerritoriesToVictoryPoints = mapTerritoriesToVictoryPoints;
        this.mapMilitaryPointsForTerritories = mapMilitaryPointsForTerritories;
    }

    public PersonalBoardView() {/**Intentionally left void**/}

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/personalBoard.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.initOwner(primaryStage);
        stage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        stage.setTitle("PersonalBoard - " + this.player.getPlayerName());
        stage.setResizable(false);
        stage.show();
        updatePersonalBoard();
        updateMilitaryAndVictoryPoints();
        stage.setOnHidden(e -> stage.close());
    }

    /**
     * Show all the {@link AbstractDevelopmentCard} && {@link LeaderCard} that the specified {@link Player} has
     * in his {@link PersonalBoard}
     */
    public void updatePersonalBoard() {
        List<AbstractDevelopmentCard> devDecks;
        ImageView imageView;

        for(DevelopmentCardColor type : DevelopmentCardColor.values())
            if(type != MULTICOLOR && player.getPersonalBoard().getDevelopmentCardsByType(type).isPresent()) {
                devDecks = player.getPersonalBoard().getDevelopmentCardsByType(type).get();
                for (int i = 0; i < devDecks.size(); i++) {
                    imageView = (ImageView) root.lookup(String.format("#personalBoard_%1$sCard%2$d",
                            devDecks.get(i).getColor().toString(), i));

                    imageView.setImage(new Image(Thread.currentThread()
                            .getContextClassLoader().getResource(String.format("images/developmentCards/%1$s/%2$s.png",
                                    devDecks.get(i).getColor().getDevType(), devDecks.get(i).getName()))
                            .toExternalForm()));

                    imageView.setVisible(true);
                }
            }

        List<LeaderCard> leaderCards = player.getActivatedLeaderCards();
        for (Integer i = 0; i < leaderCards.size(); i++) {
            imageView = (ImageView) root.lookup("#personalBoard_LeaderCard" + i.toString());

            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource(String.format("images/leaderCards/%1$s.png", leaderCards.get(i).getName()))
                    .toExternalForm()));

            imageView.setVisible(true);
        }
    }

    //TODO
    private void showMapCharactersToVictoryPoints() {

    }

    private void showMapTerritoriesToVIctoryPoints() {

    }

    public void updateMilitaryAndVictoryPoints() {

        FutureTask<Void> uiTask = new FutureTask<>(() -> {
            StackPane territoriesMilitaryPoints;
            Text territoriesMilitaryPointsValues;

            Iterator<Map.Entry<Integer, Integer>> militaryPointsIterator = this.mapMilitaryPointsForTerritories.entrySet().iterator();
            Integer i = 0;
            while(militaryPointsIterator.hasNext()) {
                territoriesMilitaryPoints = (StackPane) root.lookup("#military_points_for_territories"+ i.toString());
                territoriesMilitaryPointsValues = new Text(militaryPointsIterator.next().getValue().toString());
                territoriesMilitaryPointsValues.setStyle("-fx-effect: dropshadow(gaussian, white, 3, 0.7, 0, 0); -fx-font-size: 16;");
                territoriesMilitaryPoints.getChildren().add(territoriesMilitaryPointsValues);
                i++;
            }

            return null;
        });

        FutureTask<Void> uiTask1 = new FutureTask<>(() -> {
            StackPane territoriesVictoryPoints;
            Text territoriesVictoryPointsValue;

            System.out.println("mappa territori --> punti vittoria: "+this.mapTerritoriesToVictoryPoints.toString());

            Iterator<Map.Entry<Integer, Integer>> victoryPointsIterator = this.mapTerritoriesToVictoryPoints.entrySet().iterator();
            Integer i = 0;
            while(victoryPointsIterator.hasNext()) {
                territoriesVictoryPoints = (StackPane) root.lookup("#territories_to_victory_points"+ i.toString());
                territoriesVictoryPointsValue = new Text(victoryPointsIterator.next().getValue().toString());
                territoriesVictoryPointsValue.setStyle("-fx-effect: dropshadow(gaussian, white, 3, 0.7, 0, 0); -fx-font-size: 16;");
                territoriesVictoryPoints.getChildren().add(territoriesVictoryPointsValue);
                i++;
            }

            return null;
        });

        FutureTask<Void> uiTask2 = new FutureTask<>(() -> {
            StackPane charactersVictoryPoints;
            Text charactersVictoryPointsValue;

            Iterator<Map.Entry<Integer, Integer>> victoryPointsIterator = this.mapCharactersToVictoryPoints.entrySet().iterator();
            Integer i = 0;
            while(victoryPointsIterator.hasNext()) {
                charactersVictoryPoints = (StackPane) root.lookup("#characters_to_victory_points"+ i.toString());
                charactersVictoryPointsValue = new Text(victoryPointsIterator.next().getValue().toString());
                charactersVictoryPointsValue.setStyle("-fx-effect: dropshadow(gaussian, white, 3, 0.7, 0, 0); -fx-font-size: 16;");
                charactersVictoryPoints.getChildren().add(charactersVictoryPointsValue);
                i++;
            }

            return null;
        });

        Platform.runLater(uiTask);
        Platform.runLater(uiTask1);
        Platform.runLater(uiTask2);
    }

    }


