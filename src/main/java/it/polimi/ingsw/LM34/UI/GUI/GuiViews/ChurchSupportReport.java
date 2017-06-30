package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;

/**
 * Created by GiulioComi on 28/06/2017.
 */
public class ChurchSupportReport {

    public void interactWithPlayer(String churchResult) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Church Result");
        alert.setHeaderText(churchResult);

        alert.showAndWait();
    }
}
