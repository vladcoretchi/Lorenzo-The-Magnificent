package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginDialog extends Application {

    public void show() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/login.fxml"));
        Stage primaryStage = stage;
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/login/login.png").toExternalForm()));
        primaryStage.setTitle("Login - Lorenzo il Magnifico [LM34]");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.getScene().getStylesheets().add(LoginDialog.class.getResource("/css/login.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        primaryStage.setOnHidden(e -> primaryStage.close());

    }
}
