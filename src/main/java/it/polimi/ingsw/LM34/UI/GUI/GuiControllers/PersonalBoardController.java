package it.polimi.ingsw.LM34.UI.GUI.GuiControllers;

import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.GREEN;

/**
 * Created by GiulioComi on 07/06/2017.
 */
public class PersonalBoardController {
    HashMap<String, Integer> map = new HashMap<>();
    PersonalBoard personalBoard;

    /*Constructor*/
    PersonalBoardController() {
        personalBoard = new PersonalBoard();
    }
      public void control() {
          Optional<List<AbstractDevelopmentCard>> territoryCards = personalBoard.getDevelopmentCardsByType(GREEN);

          Stream<AbstractDevelopmentCard> stream = territoryCards.orElse(Collections.emptyList()).stream();

          stream.forEach(card -> loadCardOnPersonalBoard(card));
          map.put("chiave", 1);
          ObservableMap observableMap = FXCollections.observableMap(map);
          observableMap.addListener(new MapChangeListener() {
              @Override
              public void onChanged(Change change) {
                  System.out.println("map changed");
              }
          });
         observableMap.put("chiave2",3);
      }

    private void loadCardOnPersonalBoard(AbstractDevelopmentCard card) {

    }

    public static void main(String [] args) {
          PersonalBoardController controller = new PersonalBoardController();
          controller.control();
      }

}
