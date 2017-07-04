package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BonusTileDialog {
    List<BonusTile> availableTiles;

    /**
     * @param bonusTiles that the players has the opportunity to choose one from
     * @return the bonus tile the player wants to have during the game
     */
    public Integer interactWithPlayer(List<BonusTile> bonusTiles) {
        List<String> choices = new ArrayList<>();

        bonusTiles.forEach(bt -> {
            choices.add("HarvestArea " + bt.getHarvestBonus().getResources().getResources()
                    + "\nProductionArea " + bt.getProductionBonus().getResources().getResources());
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("BonusTile Selection");
        dialog.setHeight(600.0);
        dialog.setWidth(800.0);
        dialog.setContentText("Choose the the bonus tile you desire");
        dialog.setResizable(false);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialogClass");
        Image image = new Image(Thread.currentThread().getContextClassLoader().getResource("images/personalBoard/personalTiles/basicPersonalTile.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(541.8);
        imageView.setFitHeight(88.5);
        dialog.setGraphic(imageView);

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
