package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

public class LeaderCardsView {
    String decision;
    List<LeaderCard> leadersOwned;

    public Pair<String, LeaderCardsAction> interactWithPlayer(List<LeaderCard> leadersOwned) {
        final ChoiceDialog dialog = new ChoiceDialog();
        dialog.setTitle("Leaders Available");
        dialog.setHeaderText("leaderCard choice");
        dialog.setContentText("which leader do you wish to use? ");
        dialog.getDialogPane().setPrefHeight(370);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/leaderDialog.css").toExternalForm());

        ImageView tempImage;

        for (LeaderCard leader : leadersOwned) {
         /*---ADD AS IMAGE---*/
            tempImage = new ImageView();
            tempImage.setFitHeight(220.0);
            tempImage.setFitWidth(130.0);
            tempImage.setId(leader.getName());
            tempImage.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/leaderCards/" + leader.getName() + ".png").toExternalForm()));
            tempImage.setStyle("-fx-background-color: transparent;");

            dialog.getItems().add(tempImage);
            dialog.setResizable(true);
        }

        Optional<ImageView> result = dialog.showAndWait();
        dialog.getDialogPane().setMinHeight(800.0);
        String  leaderChoosed = result.orElse(new ImageView()).getId();
        LeaderCardsAction action = LeaderCardsAction.DISCARD;
        ButtonType activate = new ButtonType(LeaderCardsAction.PLAY.toString());
        ButtonType discard = new ButtonType(LeaderCardsAction.DISCARD.toString());
        ButtonType copy = new ButtonType(LeaderCardsAction.COPY.toString());
        Alert actionChoice = new Alert(Alert.AlertType.NONE, "Activate, Copy or Discard Leader", activate, discard, copy);
        actionChoice.setTitle("Leader Action Dialog");

        Optional<ButtonType> choice = actionChoice.showAndWait();

        if (choice.isPresent())
            if(choice.get().equals(activate))
                action = LeaderCardsAction.PLAY;
            else if (choice.get().equals(copy))
                action = LeaderCardsAction.COPY;
            else
                action = LeaderCardsAction.DISCARD;

        return new ImmutablePair(leaderChoosed, action);
    }
}