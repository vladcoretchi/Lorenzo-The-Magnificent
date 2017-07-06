package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;

public class LastActionInvalid {

    /**
     * Inform the player that the his last action was not allowed by the server
     * and specify what went wrong with that move
     */
    public void interactWithPlayer(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Action");
        alert.setHeaderText("Your last action was not valid");
        alert.setContentText(errorMessage.toLowerCase());
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialogClass");

        alert.showAndWait();
    }
}
