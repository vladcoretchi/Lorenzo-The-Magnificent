package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

/**
 * Created by GiulioComi on 27/06/2017.
 */
public class PlayersInformativeDialog {

    public void interactWithPlayer(String phrase, PawnColor playerColor) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informations on Players");
        alert.setHeaderText(phrase);
        ImageView imageView = new ImageView(Thread.currentThread().getContextClassLoader().
                getResource("images/pawns/" + playerColor.toString() +".png").toExternalForm());

        alert.setGraphic(imageView);

        alert.showAndWait();


    }
}
