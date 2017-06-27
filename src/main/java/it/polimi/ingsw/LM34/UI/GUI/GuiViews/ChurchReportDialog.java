package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class ChurchReportDialog implements DialogInterface {

    public Boolean interactWithPlayer() {
        Boolean result;
        Alert excommunicationChoice = new Alert(Alert.AlertType.NONE, "Curch Support",ButtonType.OK, ButtonType.NO);
        excommunicationChoice.setTitle("Church Support");
        excommunicationChoice.setHeaderText("Church Support Decision");
        excommunicationChoice.setContentText("Would you like to be excommunicated?");
        //DialogPane dialogPane = excommunicationChoice.getDialogPane();
        //dialogPane.getStylesheets().add(ChurchReportDialog.class.getResource("/css/curchReportDialog.css").toExternalForm());
        Optional<ButtonType> choice = excommunicationChoice.showAndWait();

        if(choice.get() == ButtonType.OK)
            result = true;
        else
            result = false;

        return result;
    }

    @Override
    public void setStyle(Dialog excommunicationChoice) {
        DialogPane dialogPane = excommunicationChoice.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("css/curchReportDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("curchReportDialog");
    }



}