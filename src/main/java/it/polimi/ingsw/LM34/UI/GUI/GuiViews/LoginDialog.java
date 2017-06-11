package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
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
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("gui/login.fxml"));

        Stage primaryStage = stage;
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();

    }

    @Override
    public void setStyle(Dialog dialog) {

    }
}
