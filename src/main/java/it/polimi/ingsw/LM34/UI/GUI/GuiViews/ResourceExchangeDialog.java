package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

/**
 * Created by GiulioComi on 07/06/2017.
 */

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provide the user a way to choose what alternative of buildingCard permanent bonus he desires
 */
public class ResourceExchangeDialog implements DialogInterface {

    public ResourceExchangeDialog() {}

    public Integer interactWithPlayer(List<Pair<Resources,ResourcesBonus>> resourcesExchange) {
        List<String> choices = new ArrayList<>();

        for(Pair<Resources, ResourcesBonus> pair : resourcesExchange) {
            ArrayList<String> required = new ArrayList<>();
            pair.getLeft().getResources().forEach((type, value) -> required.add(String.format("%1$s(%2$d)", type.toString(), value)));

            ArrayList<String> reward = new ArrayList<>();
            pair.getRight().getResources().getResources().forEach((type, value) -> reward.add(String.format("%1$s(%2$d)", type.toString(), value)));
            reward.add(String.format("COUNCIL_PRIVILEGES(%1$d)", pair.getRight().getCouncilPrivilege()));

            choices.add(required.toString() + "--->" + reward.toString());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        //setStyle(dialog);
        dialog.setTitle("Resource Exchange");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/servants.png").toExternalForm()));
        dialog.setContentText("Choose one of the options:");
        dialog.setResizable(true);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your choice: " + result.get());
        }

        if(result.isPresent()) {
            System.out.println(Integer.parseInt(result.get()));
            return Integer.parseInt(result.get());
        }
        return 0;
    }

    @Override
    public void setStyle(Dialog dialog) {
       dialog.getDialogPane().getStylesheets().add(
               getClass().getClassLoader().getResource("css/resourceExchangeDialog.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("css/resourceExchangeDialog.css");

    }
}
