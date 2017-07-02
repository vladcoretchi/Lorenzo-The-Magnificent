package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Model.Resources;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UseCouncilPrivilegeDialog {

    public Integer interactWithPlayer(List<Resources> availableBonuses) {
        List<String> choices = new ArrayList<>();
        Optional<String> result;

        //TODO
        availableBonuses.forEach(resources -> {
            Integer index = 0;
            List<String> reward = new ArrayList<>();

            resources.getResources().forEach((type, value) ->
                            reward.add(String.format("%1$s(%2$d)", type.toString(), value)));

            choices.add(index + ") " + reward);
            index++;
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>("0", choices);
        dialog.setTitle("Servants Selection");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/resources/COUNCIL_PRIVILEGE.png").toExternalForm()));
        dialog.setHeaderText("Use privilege:");
        dialog.setContentText("Choose type of reward:");
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialogClass");

        result = dialog.showAndWait();

        return Integer.parseInt(result.get().substring(0,1));
    }
}
