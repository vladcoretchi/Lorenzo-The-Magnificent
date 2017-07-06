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
        List<String> choices = new ArrayList<>();
        for(Integer i = minimumServantsRequested; i <= servantsAvailable; i++ )
            choices.add(i.toString());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
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
        ImageView tempImage;
                getClass().getResource("/css/servantsDialog.css").toExternalForm();
        dialog.getDialogPane().getStyleClass().add("servant");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            Integer choiceId = 0;
            for (String choice : choices) {
                if (choice.equalsIgnoreCase(result.get()))
                    return choiceId;

                choiceId++;
            }
        }
        return 0;
    }
}

