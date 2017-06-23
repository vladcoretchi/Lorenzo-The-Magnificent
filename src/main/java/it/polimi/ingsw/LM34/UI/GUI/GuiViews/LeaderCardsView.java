package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import javafx.event.EventHandler;
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
public class LeaderCardsView implements DialogInterface {
    String decision;
    List<LeaderCard> leadersOwned;

    public LeaderCardsView(List<LeaderCard> leadersOwned) {
        decision = new String();
        this.leadersOwned = leadersOwned;
    }

    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        HBox leaderList = new HBox();
        leaderList.setSpacing(10);
        leaderList.setStyle("-fx-background-color: transparent;");
        ImageView tempImage = new ImageView();

        for (LeaderCard leader : leadersOwned) {
         /*---ADD AS IMAGE---*/
                tempImage = new ImageView();
                tempImage.setFitHeight(300.0);
                tempImage.setFitWidth(200.0);
                tempImage.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/leaderCards/" + leader.getName() + ".png").toExternalForm()));
                tempImage.setStyle("-fx-background-color: transparent;");
                tempImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        getResult(leader.getName());

                        stage.close();
                    }
                });

                leaderList.getChildren().add(tempImage);
                leaderList.setHgrow(tempImage, Priority.ALWAYS);
        }

       /****Prepare the stage and scene****/
        Scene scene = new Scene(leaderList);
        scene.setFill(Color.TRANSPARENT);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(primaryStage);
        stage.setFullScreen(false);
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