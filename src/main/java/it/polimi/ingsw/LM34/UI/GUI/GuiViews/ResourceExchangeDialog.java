package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

/**
 * Created by GiulioComi on 07/06/2017.
 */

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
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
    AbstractClient networkClient;
    Resources res;
    ResourcesBonus bon;
    public ResourceExchangeDialog() {}
    public ResourceExchangeDialog(AbstractClient networkClient) {
        this.networkClient = networkClient;
    }
    public Integer interactWithPlayer(List<Pair<Resources,ResourcesBonus>> resourcesExchange) {
        List<String> choices = new ArrayList<>();


        for(Pair<Resources, ResourcesBonus> pair : resourcesExchange) {
            ArrayList<String> required = new ArrayList<>();
            for (ResourceType resourceType : ResourceType.values())
                required.add(resourceType.toString() + "(" + pair.getLeft().getResourceByType(resourceType) + ")");

            ArrayList<String> reward = new ArrayList<>();
            for (ResourceType resourceType : ResourceType.values())
                reward.add(resourceType.toString() + "(" + pair.getRight().getResources().getResourceByType(resourceType) + ")");
            reward.add("COUNCIL_PRIVILEGES (" + pair.getRight().getCouncilPrivilege() + ")");

            choices.add(required.toString() + "--->" + reward.toString());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Resource Exchange");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/servants.png").toExternalForm()));
        dialog.setContentText("Choose one of the options:");

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

    }
}
