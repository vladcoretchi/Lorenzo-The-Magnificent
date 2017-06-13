package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.util.Optional;

/**
 * Created by GiulioComi on 07/06/2017.
 */
public class ChurchReportDialog implements DialogInterface {

    public ChurchReportDialog() {}

    public Integer interactWithPlayer() {
        Alert excommunicationChoice = new Alert(Alert.AlertType.NONE, "Curch Report",ButtonType.OK, ButtonType.NO);
        excommunicationChoice.setTitle("Confirmation Dialog");
        excommunicationChoice.setHeaderText("This is a Custom Confirmation Dialog");
        excommunicationChoice.setContentText("We override the style classes of the dialog");
        excommunicationChoice.setContentText("Would you like to be excommunicated?");
        DialogPane dialogPane = excommunicationChoice.getDialogPane();
        dialogPane.getStylesheets().add(ChurchReportDialog.class.getResource("/css/curchReportDialog.css").toExternalForm());
        Optional<ButtonType> choice = excommunicationChoice.showAndWait();
        if(choice.get() == ButtonType.OK)
        {
            System.out.println("Excommunicated");
        } else
        {
           System.out.println("Well done!");
        }

        return 0; //TODO
    }

    @Override
    public void setStyle(Dialog excommunicationChoice) {
        DialogPane dialogPane = excommunicationChoice.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("css/curchReportDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("curchReportDialog");
    }



}