package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by GiulioComi on 11/06/2017.
 */
public class PopupSlotBonus implements DialogInterface {
    Double coordinateX;
    Double coordinateY;
    @FXML
    private VBox rewardList;

    public PopupSlotBonus(Double coordinateX, Double coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public void interactWithPlayer(ResourcesBonus resourcesReward) {
        //AnchorPane divider = new AnchorPane();
        ImageView tempImage = new ImageView();
        Label value = new Label();

        rewardList.setPadding(new Insets(10, 50, 50, 50));
        rewardList.setSpacing(10);

        for(ResourceType resType : ResourceType.values())
            if(resourcesReward.getResources().getResourceByType(resType) != 0) {
                tempImage.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/servants.png").toExternalForm()));
                value.setLabelFor(tempImage);

                rewardList.getChildren().add(tempImage);
                rewardList.setVgrow(tempImage, Priority.ALWAYS);
            }
    }

    @Override
    public void setStyle(Dialog dialog) {

    }

    //TODO: remove this testing main
    public void main (String[] args) {
        PopupSlotBonus popup = new PopupSlotBonus(500.0, 500.0);
        popup.interactWithPlayer(new ResourcesBonus(new Resources(3,4,4,3),1));

    }
}
