package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public class GameInformationDialog {

    public void interactWithPlayer(GameInformationType type, String playerName, PawnColor playerColor) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informations on Players");
        alert.setHeaderText(playerName + " " + type.toString());
        ImageView imageView = new ImageView(Thread.currentThread().getContextClassLoader().
                getResource("images/pawns/" + playerColor.toString() +".png").toExternalForm());

        alert.setGraphic(imageView);

        alert.showAndWait();
    }
}
