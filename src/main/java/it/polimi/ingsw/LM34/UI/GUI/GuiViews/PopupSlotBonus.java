package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by GiulioComi on 11/06/2017.
 */
public class PopupSlotBonus implements DialogInterface {
    Double coordinateX;
    Double coordinateY;
    Parent root;

    private ResourcesBonus resourcesReward;

    public PopupSlotBonus(Double coordinateX, Double coordinateY, ResourcesBonus resourcesReward) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.resourcesReward = resourcesReward;
    }

    public void start(Stage primaryStage, Scene guiScene) throws Exception {
        System.out.println("siamo nello start almeno");
        ScrollPane scrollPane = new ScrollPane();
        VBox rewardList = new VBox();
        rewardList.setSpacing(10);
        scrollPane.setContent(rewardList);

        ImageView tempImage = new ImageView();
        ImageView tempValue = new ImageView();
        Text value = new Text();
        StackPane pane = new StackPane();


        for(ResourceType resType : ResourceType.values())
            if(resourcesReward.getResources().getResourceByType(resType) != 0) {
                pane = new StackPane();
                pane.setAlignment(Pos.CENTER);

                value = new Text(resourcesReward.getResources().getResourceByType(resType).toString());
                value.setFont(Font.font ("Verdana", 30));
                value.setFill(Color.WHITE);

                tempImage = new ImageView();
                tempImage.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/servants.png").toExternalForm()));

                pane.getChildren().add(tempImage);
                pane.getChildren().add(value);
                rewardList.getChildren().add(pane);
                rewardList.setVgrow(pane, Priority.ALWAYS);
            }


        Scene scene = new Scene(scrollPane, 50, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        //TODO: close and go back to guiScene
        Thread.sleep(2000);
        primaryStage.setScene(guiScene);

    }

    @Override
    public void setStyle(Dialog dialog) {

    }


}
