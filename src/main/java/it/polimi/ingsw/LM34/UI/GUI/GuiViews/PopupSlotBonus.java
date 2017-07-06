package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupSlotBonus {
    private static final String style = "-fx-background-color: transparent;";
    Double coordinateX;
    Double coordinateY;
    Event generatingEvent;
    private ResourcesBonus resourcesReward;

    /**Constructor that requires info about the origin of the event generated**/
    public PopupSlotBonus(MouseEvent generatingEvent, ResourcesBonus resourcesReward) {
        this.coordinateX = generatingEvent.getScreenX() + 10;
        this.coordinateY = generatingEvent.getScreenY()+ 10;
        this.resourcesReward = resourcesReward;
        this.generatingEvent = generatingEvent;
    }

    public void start(Stage primaryStage) throws Exception {
        /*Gui components of this view*/
        StackPane pane;
        VBox rewardList = new VBox();
        rewardList.setSpacing(10);
        rewardList.setStyle(style);
        ImageView tempImage;
        Text value;
        DropShadow borderGlow;

        for (ResourceType resType : ResourceType.values())
             /*---ADD AS IMAGE AND VALUE THE RESOURCES AND POINTS---*/
             if (resourcesReward.getResources().getResourceByType(resType) > 0) {
                pane = new StackPane();
                pane.setStyle(style);
                pane.setAlignment(Pos.TOP_CENTER);

                value = new Text(resourcesReward.getResources().getResourceByType(resType).toString());
                value.setStyle(style);
                value.setFont(Font.font("Verdana", 30));
                value.setFill(Color.BLACK);
                borderGlow = createEffect();

                value.setEffect(borderGlow);
                tempImage = new ImageView();
                tempImage.setFitHeight(50.0);
                tempImage.setFitWidth(50.0);
                tempImage.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/resources/" + resType.toString() + ".png").toExternalForm()));
                tempImage.setStyle(style);

                pane.getChildren().add(tempImage);
                pane.getChildren().add(value);
                rewardList.getChildren().add(pane);
                rewardList.setVgrow(pane, Priority.ALWAYS);
            }
        /*---ADD AS IMAGE AND VALUE THE COUNCIL PRIVILEGES---*/
        if(resourcesReward.getCouncilPrivilege().intValue() > 0) {
            pane = new StackPane();
            value = new Text(resourcesReward.getCouncilPrivilege().toString());
            value.setStyle(style);
            value.setFont(Font.font("Verdana", 30));
            value.setFill(Color.BLACK);
            borderGlow = createEffect();
            value.setEffect(borderGlow);
            tempImage = new ImageView();
            tempImage.setFitHeight(50.0);
            tempImage.setFitWidth(50.0);
            tempImage.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/resources/" + "COUNCIL_PRIVILEGE" + ".png").toExternalForm()));
            tempImage.setStyle(style);

            pane.getChildren().add(tempImage);
            pane.getChildren().add(value);
            rewardList.getChildren().add(pane);
            rewardList.setVgrow(pane, Priority.ALWAYS);
        }

       /*---Prepare the stage and scene*/
        Stage stage = new Stage();
        Scene scene = new Scene(rewardList);
        scene.setFill(Color.TRANSPARENT);
        stage.setX(coordinateX);
        stage.setY(coordinateY);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setFullScreen(false);
        stage.setScene(scene);

        /*Set a listener on the imageViewSlot that generated this info popup in order to close this at onMouseExited*/
        Node slotImage = (Node) generatingEvent.getSource();
        slotImage.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent m) {
                stage.close();
            }
        });

        stage.show();
    }

    public DropShadow createEffect() {
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setSpread(0.4);
        borderGlow.setRadius(25.0);
        borderGlow.setColor(Color.WHITE);
        borderGlow.setWidth(12);
        borderGlow.setHeight(12);

        return borderGlow;
    }
}
