package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by GiulioComi on 07/06/2017.
 */
public class UseServantsDialog {
    public Integer interactWithPlayer(Integer servantsAvailable, Integer minimumServantsRequested) {
        List<String> choices = new ArrayList<>();
        for(Integer i = minimumServantsRequested; i < servantsAvailable; i++ )
            choices.add(i.toString());


        ChoiceDialog<String> dialog = new ChoiceDialog<>("minimumServantsRequested", choices);
        dialog.setTitle("Servants Selection");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/servants.png").toExternalForm()));
        dialog.setHeaderText("Increase family member value by using servants");
        dialog.setContentText("Choose number of servants:");


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your choice: " + result.get());
        }


        result.ifPresent(letter -> System.out.println("Your choice: " + letter));

        return 0;
    }
}

