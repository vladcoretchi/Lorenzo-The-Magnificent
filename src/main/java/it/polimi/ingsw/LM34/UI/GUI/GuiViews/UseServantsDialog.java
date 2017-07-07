package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UseServantsDialog {

    public UseServantsDialog() {}

    public Integer interactWithPlayer(Integer servantsAvailable, Integer minimumServantsRequested) {
        List<Integer> choices = new ArrayList<>();
        for(Integer i = minimumServantsRequested; i <= servantsAvailable; i++ )
            choices.add(i);

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Servants Selection");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/resources/SERVANTS.png").toExternalForm()));
        dialog.setHeaderText("Increase family member value by using servants");
        dialog.setContentText("Choose number of servants to use:");
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialogClass");
        dialog.getDialogPane().getStyleClass().add("servant");

        Optional<Integer> result = dialog.showAndWait();

        return result.orElse(0);
    }

}

