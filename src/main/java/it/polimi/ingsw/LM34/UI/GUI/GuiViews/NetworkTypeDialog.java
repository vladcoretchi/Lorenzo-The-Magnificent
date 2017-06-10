package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

import static it.polimi.ingsw.LM34.Enums.UI.NetworkType.RMI;
import static it.polimi.ingsw.LM34.Enums.UI.NetworkType.SOCKET;

/**
 * Created by GiulioComi on 08/06/2017.
 */
public class NetworkTypeDialog {
    public NetworkType interactWithPlayer() {
        NetworkType networkChoosed;
        ButtonType rmi = new ButtonType("RMI");
        ButtonType socket = new ButtonType("Socket");
        Alert networkChoice = new Alert(Alert.AlertType.NONE, "Network Choice",rmi, socket );

        //deve ricevere in ingresso primaryStage, in modo da settare la scena come in LoginDialog
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root, 400, 300));
        //stage.initOwner(primaryStage);
        //stage.showAndWait();
        networkChoice.setContentText("Choose RMI or Socket");

        Optional<ButtonType> choice = networkChoice.showAndWait();
        if(choice.get() == ButtonType.OK)
        {
            networkChoosed = RMI;
        } else
        {
            networkChoosed = SOCKET;
        }
        System.out.println(networkChoosed);
        return networkChoosed;
    }
}
