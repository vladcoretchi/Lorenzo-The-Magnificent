package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;

public class DisconnectionWarning {

    public void interactWithPlayer() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Network Disconnection");
        alert.setHeaderText("You are no connected to the game");

        alert.showAndWait();
    }
}
