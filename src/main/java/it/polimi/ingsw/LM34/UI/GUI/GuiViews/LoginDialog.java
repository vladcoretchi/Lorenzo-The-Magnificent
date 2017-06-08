package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by GiulioComi on 08/06/2017.
 */
public class LoginDialog {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    TextInputDialog dialog = new TextInputDialog("");

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("gui/login.fxml"));
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        primaryStage.setTitle("Lorenzo il Magnifico by CranioCreations");
        primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public void InteractWithPlayer() {
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");
        dialog.setContentText("Please enter your name:");
    }


    public TextField getUsername() {
        return this.username;
    }
    public PasswordField getPassword() {
        return this.password;
    }

}
