package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LeaderSelectionPhaseDialog {
    List<LeaderCard> leaderCards;

    /**
     * @param leaderCards that the players has the opportunity to choose one from
     * @return the leader the player wants to have as one of his 4 leaders
     */
    public Integer interactWithPlayer(List<LeaderCard> leaderCards) {
        List<String> choices = new ArrayList<>();

        leaderCards.forEach(lc -> {
            choices.add("Bonus Type: " + lc.getBonus().getClass().getSimpleName()
                    + "Requirements " + lc.getRequirements().getResourcesRequirements().get().getResources().toString());
        });

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Leader Selection Phase");
        dialog.setHeight(600.0);
        dialog.setWidth(800.0);
        dialog.setContentText("Choose the the leader card you desire to have at your service");
        dialog.setResizable(true);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/leaderSelectionPhaseDialog.css").toExternalForm());

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            Integer choiceId = 0;
            for (String choice : choices) {
                if (choice.equalsIgnoreCase(result.get()))
                    return choiceId;

                choiceId++;
            }
        }
        return 0;
    }
}
