package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class ChurchReportDialog {

    public Boolean interactWithPlayer() {
        Boolean result;
        Alert excommunicationChoice = new Alert(Alert.AlertType.NONE, "Curch Support",ButtonType.OK, ButtonType.NO);
        excommunicationChoice.setTitle("Church Support");
        excommunicationChoice.setHeaderText("Church Support Decision");
        excommunicationChoice.setContentText("Would you like to be excommunicated?");
        excommunicationChoice.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/churchReportDialog.css").toExternalForm());
        excommunicationChoice.getDialogPane().getStyleClass().add("churchReport");
        Image image = new Image(Thread.currentThread().getContextClassLoader().getResource("images/resources/FAITH_POINTS.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(85.2);
        imageView.setFitHeight(85.2);
        excommunicationChoice.setGraphic(imageView);
        Optional<ButtonType> choice = excommunicationChoice.showAndWait();

        if(choice.get() == ButtonType.OK)
            result = true;
        else
            result = false;

        return result;
    }
}