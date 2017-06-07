package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by GiulioComi on 07/06/2017.
 */
public class UseCouncilPrivilegeDialog {
    public Integer interactWithPlayer(Integer servantsAvailable, Integer numberOfPrivileges) {
        List<String> choices = new ArrayList<>();
        for(Integer i = numberOfPrivileges; i < servantsAvailable; i++ )
            choices.add(i.toString());


        ChoiceDialog<String> dialog = new ChoiceDialog<>("minimumServantsRequested", choices);
        dialog.setTitle("Servants Selection");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/servants.png").toExternalForm()));
        dialog.setHeaderText("Use privileges:");
        dialog.setContentText("Choose type of reward:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your choice: " + result.get());
        }

// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(letter -> System.out.println("Your choice: " + letter));

        return 0;
    }
}
