package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

/**
 * Created by GiulioComi on 07/06/2017.
 */

import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

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
    public Integer interactWithPlayer(Integer servantsAvailable, Integer minimumServantsRequested) {
        List<String> choices = new ArrayList<>();
        Integer servantsToUse;
        for(Integer i = minimumServantsRequested; i < servantsAvailable; i++ )
            res = new Resources();
            bon = new ResourcesBonus(new Resources(), 2);
            /*ResourcesExchangeBonus bonus = new ResourcesExchangeBonus(new Player("player1",
                    PawnColor.BLUE, new PersonalBoard()), 3,
                    bon));*/



        ChoiceDialog<String> dialog = new ChoiceDialog<>("minimumServantsRequested", choices);
        dialog.setTitle("Servants Selection");
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
