package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

public class GameInformationDialog {

    /**
     * Shows multiple kind of info about players
     * @param infoType information about a player {@link GameInformationType}
     * @param playerName the associated phrase to show to player
     * @param playerColor the color associated to the player to whom the info concerns
     */
    public void interactWithPlayer(GameInformationType type, String playerName, PawnColor playerColor) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informations on Players");
        alert.setHeaderText(playerName + " " + type.toString());
        ImageView imageView = new ImageView(Thread.currentThread().getContextClassLoader().
                getResource("images/pawns/" + playerColor.toString() +".png").toExternalForm());
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialogClass");

        alert.setGraphic(imageView);

        alert.showAndWait();
    }
}
