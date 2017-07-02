package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provide the user a way to choose what alternative of buildingCard permanent bonus he desires
 */
public class ResourceExchangeDialog {

    public Integer interactWithPlayer(List<Pair<Resources,ResourcesBonus>> resourcesExchange) {
        List<String> choices = new ArrayList<>();

        for (Pair<Resources, ResourcesBonus> pair : resourcesExchange) {
            List<String> required = new ArrayList<>();
            pair.getLeft().getResources().forEach((type, value) -> required.add(String.format("%1$s(%2$d)", type.toString(), value)));

            List<String> reward = new ArrayList<>();
            pair.getRight().getResources().getResources().forEach((type, value) -> reward.add(String.format("%1$s(%2$d)", type.toString(), value)));
            reward.add(String.format("COUNCIL_PRIVILEGES(%1$d)", pair.getRight().getCouncilPrivilege()));

            choices.add(required.toString() + "--->" + reward.toString());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Resource Exchange");
        dialog.setHeight(600.0);
        dialog.setWidth(400.0);
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/resources/SERVANTS.png").toExternalForm()));
        dialog.setContentText("Choose the Leader and the Option to perform:");
        dialog.setResizable(true);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/resourceExcangeDialog.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("resourceExcange");

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
