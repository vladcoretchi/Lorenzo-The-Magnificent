package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.MULTICOLOR;

public class PersonalBoardView extends Application {
    private Parent root;
    private Player player;
    @FXML private Group personalBoardCards;

    public PersonalBoardView(Player playerReceived) {
        this.player = playerReceived;
    }
    public PersonalBoardView() {/**Intentionally left void**/}

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/personalBoard.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.initOwner(primaryStage);
        stage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        stage.setTitle("PersonalBoard - " + this.player.getPlayerName());
        stage.setResizable(false);
        stage.show();
        updatePersonalBoard();
        stage.setOnHidden(e -> stage.close());
    }

    /**
     * Show all the {@link AbstractDevelopmentCard} && {@link LeaderCard} that the specified {@link Player} has
     * in his {@link PersonalBoard}
     */
    public void updatePersonalBoard() {
        List<AbstractDevelopmentCard> devDecks;
        ImageView imageView;

        for(DevelopmentCardColor type : DevelopmentCardColor.values())
            if(type != MULTICOLOR && player.getPersonalBoard().getDevelopmentCardsByType(type).isPresent()) {
                devDecks = player.getPersonalBoard().getDevelopmentCardsByType(type).get();
                for (int i = 0; i < devDecks.size(); i++) {
                    imageView = (ImageView) root.lookup(String.format("#personalBoard_%1$sCard%2$d",
                            devDecks.get(i).getColor().toString(), i));

                    imageView.setImage(new Image(Thread.currentThread()
                            .getContextClassLoader().getResource(String.format("images/developmentCards/%1$s/%2$s.png",
                                    devDecks.get(i).getColor().getDevType(), devDecks.get(i).getName()))
                            .toExternalForm()));

                    imageView.setVisible(true);
                }
            }

        List<LeaderCard> leaderCards = player.getActivatedLeaderCards();
        for (Integer i = 0; i < leaderCards.size(); i++) {
            imageView = (ImageView) root.lookup("#personalBoard_LeaderCard" + i.toString());

            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource(String.format("images/leaderCards/%1$s.png", leaderCards.get(i).getName()))
                    .toExternalForm()));

            imageView.setVisible(true);
        }
    }
}

