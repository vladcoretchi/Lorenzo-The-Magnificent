package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Model.FamilyMember;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FamilyMemberSelectDialog {

    /**
     * @param membersAvailable available to the player
     * @return familyMember choosed from the player
     */
    public Integer interactWithPlayer(Stage primaryStage, List<FamilyMember> membersAvailable) {
        String choosed;
        ArrayList<String> members = new ArrayList<>();

        for(FamilyMember member : membersAvailable)
            members.add(member.getDiceColorAssociated().toString() + " (" + member.getValue() + ")");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(members.get(0), members);
        dialog.setTitle("Family Member Selection");
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setContentText("Family Member Selection");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/resources/SERVANTS.png").toExternalForm()));
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        dialog.getDialogPane().getStyleClass().add("dialogClass");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            Integer choiceId = 1;
            for (String choice : members) {
                if (choice.equalsIgnoreCase(result.get()))
                    return choiceId;

                choiceId++;
            }
        }
        return 0;
    }
}
