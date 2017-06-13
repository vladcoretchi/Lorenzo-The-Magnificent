package it.polimi.ingsw.LM34.UI.GUI.GuiControllers;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.*;

/**
 * Created by GiulioComi on 07/06/2017.
 */
public class PersonalBoardController {
    Parent root;

    HashMap<String, Integer> map = new HashMap<>();
    List<AbstractDevelopmentCard> list = new ArrayList<AbstractDevelopmentCard>();
    @FXML private HBox territories;
    @FXML private HBox characters;
    @FXML private HBox ventures;
    @FXML private HBox buildings;
    @FXML private HBox activatedLeaders;

    PersonalBoard personalBoard;

    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/personalBoard.fxml"));
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = gd.getDisplayMode().getWidth();
        double height = gd.getDisplayMode().getHeight();
        primaryStage.setMaxWidth(width);
        primaryStage.setMaxHeight(height);

        primaryStage.show();
    }

    /*Constructor*/
    public PersonalBoardController() {
        personalBoard = new PersonalBoard();
    }

    public void control() {
        List<AbstractDevelopmentCard> paramCards = new ArrayList<>();
        //TODO
        Player player = new Player("player1", PawnColor.BLUE, new PersonalBoard());

        Optional<List<AbstractDevelopmentCard>> territoryCards = personalBoard.getDevelopmentCardsByType(GREEN);
        Optional<List<AbstractDevelopmentCard>> charactersCards = personalBoard.getDevelopmentCardsByType(BLUE);
        Optional<List<AbstractDevelopmentCard>> venturesCards = personalBoard.getDevelopmentCardsByType(PURPLE);
        Optional<List<AbstractDevelopmentCard>> buildingsCards = personalBoard.getDevelopmentCardsByType(YELLOW);
        List<LeaderCard> leaderCards = player.getActivatedLeadercards();

        territoryCards.ifPresent(cards -> loadDevelopmentCardsOnPersonalBoard(cards));
        charactersCards.ifPresent(cards -> loadDevelopmentCardsOnPersonalBoard(cards));
        buildingsCards.ifPresent(cards -> loadDevelopmentCardsOnPersonalBoard(cards));
        venturesCards.ifPresent(cards -> loadDevelopmentCardsOnPersonalBoard(cards));
        loadActivatedLeadersOnPersonalBoard(leaderCards);

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

    private void loadActivatedLeadersOnPersonalBoard(List<LeaderCard> leaderCards) {
        ImageView tempImage = new ImageView();
        if (leaderCards.size() > 0) {
            for (LeaderCard card : leaderCards) {
                tempImage = new ImageView();
                tempImage.setFitHeight(200.0);
                tempImage.setFitWidth(100.0);
                tempImage.setImage(new javafx.scene.image.Image(Thread.currentThread()
                        .getContextClassLoader().getResource("images/leaderCards/" + card.getName() + ".png").toExternalForm()));
                tempImage.setStyle("-fx-background-color: transparent;");

                activatedLeaders.getChildren().add(tempImage);
            }
        }
    }

    //TODO
    public void loadDevelopmentCardsOnPersonalBoard(List<AbstractDevelopmentCard> cardsOwned) {
        DevelopmentCardColor color = cardsOwned.get(0).getColor();
        String type = color.getDevType();
        ImageView tempImage = new ImageView();

        for (AbstractDevelopmentCard card : cardsOwned) {
            tempImage = new ImageView();
            tempImage.setFitHeight(200.0);
            tempImage.setFitWidth(100.0);
            tempImage.setImage(new javafx.scene.image.Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/developmentCards/" + type + "/" + card.getName() + ".png").toExternalForm()));
            tempImage.setStyle("-fx-background-color: transparent;");

            getHBoxByType(color).getChildren().add(tempImage);
        }
    }

    public static void main(String[] args) {
        PersonalBoardController controller = new PersonalBoardController();
        //controller.control();
    }

    public HBox getHBoxByType(DevelopmentCardColor color) {
        switch(color) {
            case GREEN: return territories;
            case BLUE: return characters;
            case YELLOW: return buildings;
            case PURPLE: return ventures;
            default: return null; //TODO
        }
    }
}
