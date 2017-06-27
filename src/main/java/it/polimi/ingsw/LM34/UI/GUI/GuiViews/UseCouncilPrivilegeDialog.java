package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
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
            Integer value = 0;

            for(ResourceType type : ResourceType.values()) {
            value = availableBonuses.get(index).getResourceByType(type);
            if(availableBonuses.get(index).getResourceByType(type) > 0)
                choices.add(type.toString() + " :" + value);
            index++;
        }
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>("0", choices);
        dialog.setTitle("Servants Selection");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/resources/COUNCIL_PRIVILEGE.png").toExternalForm()));
        dialog.setHeaderText("Use privilege:");
        dialog.setContentText("Choose type of reward:");

        result = dialog.showAndWait();

        return Integer.parseInt(result.get());
    }
}
