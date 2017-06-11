package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by GiulioComi on 08/06/2017.
 */
public class LoginDialog extends Application implements DialogInterface {

    public void show() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/login.fxml"));
        Stage primaryStage = stage;
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        primaryStage.setTitle("Login - Lorenzo il Magnifico by CranioCreations");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
        primaryStage.setOnHidden(e -> primaryStage.close());

    }

    @Override
    public void setStyle(Dialog dialog) {

    }
}
