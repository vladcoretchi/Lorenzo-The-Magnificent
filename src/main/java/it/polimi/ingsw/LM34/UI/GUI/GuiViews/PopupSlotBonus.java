package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


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

    public void start(Stage primaryStage) throws Exception {

        VBox rewardList = new VBox();
        rewardList.setSpacing(10);
        rewardList.setStyle("-fx-background-color: transparent;");
        ImageView tempImage = new ImageView();
        Text value = new Text();
        value.setStyle("-fx-background-color: transparent;");
        StackPane pane = new StackPane();

        for(ResourceType resType : ResourceType.values())
            if(resourcesReward.getResources().getResourceByType(resType) > 0) {
                pane = new StackPane();
                pane.setStyle("-fx-background-color: transparent;");
                pane.setAlignment(Pos.TOP_CENTER);

                value = new Text(resourcesReward.getResources().getResourceByType(resType).toString());
                value.setStyle("-fx-background-color: transparent;");
                value.setFont(Font.font ("Verdana", 30));
                value.setFill(Color.WHITE);

                tempImage = new ImageView();
                tempImage.setFitHeight(50.0);
                tempImage.setFitWidth(50.0);
                tempImage.setImage(new Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/resources/" + resType.toString() + ".png").toExternalForm()));
                tempImage.setStyle("-fx-background-color: transparent;");

                pane.getChildren().add(tempImage);
                pane.getChildren().add(value);
                rewardList.getChildren().add(pane);
                rewardList.setVgrow(pane, Priority.ALWAYS);
            }

        Stage stage = new Stage();

        rewardList.setOnKeyPressed(new EventHandler<KeyEvent>()  {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            }
        });

        rewardList.setOnMouseExited(new EventHandler<MouseEvent>()  {
            @Override
            public void handle(MouseEvent m) {
                stage.close();
                }
        });

        Scene scene = new Scene(rewardList);
        scene.setFill(Color.TRANSPARENT);
        stage.setX(coordinateX);
        stage.setY(coordinateY);
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(primaryStage);
        stage.setFullScreen(false);
        stage.setScene(scene);

        stage.show();

    }

    @Override
    public void setStyle(Dialog dialog) {

    }


}
