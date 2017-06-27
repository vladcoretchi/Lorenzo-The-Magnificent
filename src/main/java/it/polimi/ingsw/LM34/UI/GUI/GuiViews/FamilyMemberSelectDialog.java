package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FamilyMemberSelectDialog implements DialogInterface {

    public Integer interactWithPlayer(List<FamilyMember> membersAvailable) {
        String choosed;
        ArrayList<String> members = new ArrayList<>();

        for(FamilyMember member : membersAvailable)
            members.add(member.getDiceColorAssociated().toString());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(DiceColor.NEUTRAL.toString(), members);
        dialog.setTitle("Family Member Selection");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/servants.png").toExternalForm()));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return Integer.parseInt(result.get());
        }
        else
            return 0;
    }

    @Override
    public void setStyle(Dialog dialog) {

    }
}
