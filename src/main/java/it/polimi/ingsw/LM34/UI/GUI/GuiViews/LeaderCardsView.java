package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.UI.GUI.GUI;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

/**
 * Created by GiulioComi on 07/06/2017.
 */
public class LeaderCardsView extends Application implements DialogInterface {
    String decision;
    List<LeaderCard> leadersOwned;
    GUI gui;

    private Parent root;

    public LeaderCardsView() {}

    public LeaderCardsView(GUI gui, List<LeaderCard> leadersOwned) {
        decision = new String();
        this.leadersOwned = leadersOwned;
        this.gui = gui;
    }

    public void show() {
        launch();
    }

    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/leaderCardAction.fxml"));

        Stage stage = new Stage();
        ImageView tempImage = new ImageView();
        HBox hboxLeaders = (HBox) root.lookup("#leaderList");

        for (LeaderCard leader : leadersOwned) {
         /*---ADD AS IMAGE---*/
                tempImage = new ImageView();
                tempImage.setFitHeight(220.0);
                tempImage.setFitWidth(130.0);
                tempImage.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/leaderCards/" + leader.getName() + ".png").toExternalForm()));
                tempImage.setStyle("-fx-background-color: transparent;");
                tempImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                       gui.passLeaderChoosed(leader.getName());
                        stage.close();
                    }
                });

            hboxLeaders.getChildren().add(tempImage);
                hboxLeaders.setHgrow(tempImage, Priority.ALWAYS);

        }

       /****Prepare the stage and scene****/
        Scene scene = new Scene(root);
        stage.setTitle("Leader Action Selection");
        scene.setFill(Color.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        //stage.initOwner(primaryStage);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void setStyle(Dialog dialog) {

    }

    public LeaderCardsAction getResult(String leaderName) {
        decision = new LeaderActivateOrDiscardDialog(leaderName).interactWithPlayer();

        if (LeaderCardsAction.PLAY.toString().equalsIgnoreCase(decision))
            return LeaderCardsAction.PLAY;
        else
            return LeaderCardsAction.DISCARD;
    }
}