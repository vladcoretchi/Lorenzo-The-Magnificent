package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class EndTurnPopup {

    /**
     * Inform the player that is turn has ended
     */
    public void interactWithPlayer() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End Turn Popup");
        alert.setHeaderText(null);
        alert.setContentText("Your turn has ended");
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialogClass");
        Image image = new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(85.2);
        imageView.setFitHeight(85.2);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }
}
