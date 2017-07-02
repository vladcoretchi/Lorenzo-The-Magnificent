package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BonusTileDialog {
    List<BonusTile> availableTiles;

    public Integer interactWithPlayer(List<BonusTile> bonusTiles) {
        List<String> choices = new ArrayList<>();

        bonusTiles.forEach(bt -> {
            choices.add("HarvestArea " + bt.getHarvestBonus().getResources().getResources()
                    + "ProductionArea " + bt.getHarvestBonus().getResources().getResources());
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        //setStyle(dialog);
        dialog.setTitle("BonusTile Selection");
        dialog.setHeight(600.0);
        dialog.setWidth(800.0);
        dialog.setContentText("Choose the the bonus tile you desire");
        dialog.setResizable(true);

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
