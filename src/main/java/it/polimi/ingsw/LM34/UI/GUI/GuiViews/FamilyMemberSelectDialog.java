package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by GiulioComi on 10/06/2017.
 */
public class FamilyMemberSelectDialog implements DialogInterface {

    public FamilyMemberSelectDialog() {}

    public String interactWithPlayer(ArrayList<FamilyMember> membersAvailable) {
        String choosed;
        ArrayList<String> members = new ArrayList<>();

        for(FamilyMember member : membersAvailable)
            members.add(member.getDiceColorAssociated().toString());


        ChoiceDialog<String> dialog = new ChoiceDialog<>(DiceColor.NEUTRAL.toString(), members);
        dialog.setTitle("Family Member Selection");
        dialog.setGraphic(new ImageView(Thread.currentThread().getContextClassLoader().getResource("images/servants.png").toExternalForm()));



        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your choice: " + result.get());
        }


        return Optional.empty().toString();
    }


    @Override
    public void setStyle(Dialog dialog) {

    }
}
