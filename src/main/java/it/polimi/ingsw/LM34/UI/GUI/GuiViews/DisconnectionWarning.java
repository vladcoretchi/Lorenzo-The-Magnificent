package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;

public class DisconnectionWarning {

    /**
     * Inform the player that the server is not more reachable
     */
    public void interactWithPlayer() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Network Disconnection");
        alert.setHeaderText("You are no connected to the game");
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialogClass");

        alert.showAndWait();
    }
}
