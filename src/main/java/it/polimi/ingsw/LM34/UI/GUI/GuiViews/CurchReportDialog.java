package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.util.Optional;

/**
 * Created by GiulioComi on 07/06/2017.
 */
public class CurchReportDialog implements DialogInterface{
    public Integer interactWithPlayer(Integer servantsAvailable, Integer minimumServantsRequested) {
        Alert excommunicationChoice = new Alert(Alert.AlertType.NONE, "Curch Report",ButtonType.OK, ButtonType.NO);
        excommunicationChoice.setContentText("Would you like to be excommunicated?");
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
                getClass().getResource("css/curchReport").toExternalForm());
        dialogPane.getStyleClass().add("curchReportDialog");
    }



}