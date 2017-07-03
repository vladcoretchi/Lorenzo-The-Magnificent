package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.PathType;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PathTypesVisualizationDialog {

    public void interactWithPlayer(PathType map, Map<Integer, Integer> path) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(map.toString());
        alert.setHeaderText(map.toString());
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/dialogStyle.css").toExternalForm());
        alert.getDialogPane().getStyleClass().add("dialogClass");
        List<String> list = new ArrayList<>();
        String unifiedString;

        Iterator iterator = path.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iterator.next();
            list.add("Position: " + entry.getKey().toString() + " --> " + (entry.getValue().toString()) + "\n");
        }
        unifiedString = String.join("", list);
        alert.setContentText(unifiedString);
        alert.showAndWait();
    }
}
