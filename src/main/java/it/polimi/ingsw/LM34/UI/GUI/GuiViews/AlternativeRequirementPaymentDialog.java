package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

/**
 * This class is called for the {@link it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard}
 * that have as an alternative requirement option the payment of {@link ResourceType.MILITARY_POINTS}
 */
public class AlternativeRequirementPaymentDialog {

    public Boolean interactWithPlayer() {
        Boolean result = false;
        ButtonType military = new ButtonType(ResourceType.MILITARY_POINTS.toString());
        ButtonType resources = new ButtonType("RESOURCES");
        
        Alert alternativePayment = new Alert(Alert.AlertType.NONE, "Alternative Payment", military, resources);
        alternativePayment.setTitle("Alternative Payment Selection");
        alternativePayment.setHeaderText("Select the payment option");
        alternativePayment.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        alternativePayment.getDialogPane().getStyleClass().add("dialogClass");
        Image image = new Image(Thread.currentThread().getContextClassLoader().getResource("images/resources/MILITARY_POINTS.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(85.2);
        imageView.setFitHeight(85.2);
        alternativePayment.setGraphic(imageView);
        Optional<ButtonType> choice = alternativePayment.showAndWait();

        if (choice.isPresent()) {
            String stringResult = choice.get().toString();
            if(stringResult.equalsIgnoreCase(military.getText()))
                result = true;
        }
        return result;
    }
}
