package it.polimi.ingsw.LM34.UI.GUI.GuiControllers;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;
import java.util.stream.Stream;

import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.GREEN;

/**
 * Created by GiulioComi on 07/06/2017.
 */
public class PersonalBoardController {
    HashMap<String, Integer> map = new HashMap<>();
    List<AbstractDevelopmentCard> list = new ArrayList<AbstractDevelopmentCard>();

    PersonalBoard personalBoard;

    /*Constructor*/
    PersonalBoardController() {
        personalBoard = new PersonalBoard();
    }
      public void control() {
          Optional<List<AbstractDevelopmentCard>> territoryCards = personalBoard.getDevelopmentCardsByType(GREEN);
          loadCardOnPersonalBoard(territoryCards.get());
          Stream<AbstractDevelopmentCard> stream = territoryCards.orElse(Collections.emptyList()).stream();


         /*stream.forEach(card -> loadCardOnPersonalBoard(card));
          ObservableList observableList = FXCollections.observableList(list);
          observableList.addListener(new ListChangeListener() {
              @Override
              public void onChanged(Change change) {
                  System.out.println("map changed");
              }
          });*/
         //observableList.add(stream.)

          /*stream.forEach(card -> loadCardOnPersonalBoard(card));
          map.put("chiave", 1);
          ObservableMap observableMap = FXCollections.observableMap(map);
          observableMap.addListener(new MapChangeListener() {
              @Override
              public void onChanged(Change change) {
                  System.out.println("map changed");
              }
          });
         observableMap.put("chiave2",3);*/
      }

    //TODO
    private void loadCardOnPersonalBoard(List<AbstractDevelopmentCard> territoryCards) {
        Parent root;
        Integer index = 1;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/gui.fxml"));
            for (AbstractDevelopmentCard card : territoryCards) {
                ((ImageView) root.lookup("#tower" + DevelopmentCardColor.GREEN.toString() + "_level" + index))
                                 .setImage(new Image(Thread.currentThread().getContextClassLoader().
                                         getResource("images/developmentCards/territories/" + card.getName() +".png").toExternalForm()));
            index++;
        }

        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String [] args) {
          PersonalBoardController controller = new PersonalBoardController();
          controller.control();
      }

}
