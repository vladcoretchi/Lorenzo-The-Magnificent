package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Model.Resources;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UseCouncilPrivilegeDialog {

    public Integer interactWithPlayer(List<Resources> availableBonuses) {
        List<String> choices = new ArrayList<>();

        availableBonuses.forEach(resources -> {
            Integer index = 0;
            List<String> reward = new ArrayList<>();

            resources.getResources().forEach((type, value) ->
                    reward.add(String.format("%1$s(%2$d)", type.toString(), value)));

            choices.add("" + reward);
            index++;
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        //dialog.setTitle("Servants Selection");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/resources/COUNCIL_PRIVILEGE.png").toExternalForm()));
        dialog.setHeaderText("Use privilege:");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setContentText("Choose type of reward:");
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialogClass");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Integer choiceId = 1;
            for (String choice : choices) {
                if (choice.equalsIgnoreCase(result.get()))
                    return choiceId;

                choiceId++;
            }
        }
        return 0;
    }
}
