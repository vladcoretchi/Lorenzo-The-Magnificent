package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

/**
 * Created by GiulioComi on 22/06/2017.
 */
public class LeaderActivateOrDiscardDialog implements DialogInterface {
    String leaderName;

public LeaderActivateOrDiscardDialog(String leaderName) {
    this.leaderName = leaderName;
}

    public String interactWithPlayer() {
        String decision;
        ButtonType activate = new ButtonType("Activate");
        ButtonType discard = new ButtonType("Discard");
        Alert actionChoice = new Alert(Alert.AlertType.NONE, "Activate or Discard Leader", activate, discard);
        actionChoice.setTitle("Leader Action Dialog");
        actionChoice.setHeaderText("Activate or Discard");

        Optional<ButtonType> choice = actionChoice.showAndWait();

        if(choice.get() == activate)
            decision = activate.toString();
        else
            decision = discard.toString();

        return decision;
    }

    @Override
    public void setStyle(Dialog dialog) {

    }
}
