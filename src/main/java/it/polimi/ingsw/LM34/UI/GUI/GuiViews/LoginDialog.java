package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.Set;

public class LoginDialog extends Application {

    private Stage primaryStage;

    public void show() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/login.fxml"));
        primaryStage = stage;
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/login/login.png").toExternalForm()));
        primaryStage.setTitle("Login - Lorenzo il Magnifico [LM34]");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.getScene().getStylesheets().add(LoginDialog.class.getResource("/css/login.css").toExternalForm());
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> stop(event));

    }

    public void stop(WindowEvent event) {
        primaryStage.setOnCloseRequest(event1 -> Platform.exit());
        primaryStage.close();
        System.exit(0);
    }
}
