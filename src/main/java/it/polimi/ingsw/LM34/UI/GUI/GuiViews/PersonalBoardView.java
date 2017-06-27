package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Player;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PersonalBoardView extends Application implements DialogInterface {
    private Parent root;
    private Player player;

    @FXML
    private Group personalBoardCards;

    public PersonalBoardView(Player playerReceived) {
        this.player = playerReceived;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/personalBoard.fxml"));


        Stage stage = new Stage();
        stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.initOwner(primaryStage);
        stage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        stage.setTitle("PersonalBoard - "+ this.player.getPlayerName());
        stage.setResizable(false);
        stage.show();
        updatePersonalBoard();
        stage.setOnHidden(e -> stage.close());
    }

    @Override
    public void setStyle(Dialog dialog) {

    }

    public void updatePersonalBoard() {
        ImageView imageView;

        List<AbstractDevelopmentCard> playerCards = new ArrayList<>();
            playerCards.addAll(this.player.getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.YELLOW).orElseGet(null));
            playerCards.addAll(this.player.getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.GREEN).orElseGet(null));
            playerCards.addAll(this.player.getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.BLUE).orElseGet(null));
            playerCards.addAll(this.player.getPersonalBoard().getDevelopmentCardsByType(DevelopmentCardColor.PURPLE).orElseGet(null));

        for (int i = 0; i < playerCards.size(); i++) {
            imageView = ((ImageView) root.lookup(String.format("#personalBoard_%1$sCard%2$d", playerCards.get(i).getColor().toString(), i)));

            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource(String.format("images/developmentCards/%1$s/%2$s.png", playerCards.get(i).getColor().getDevType(), playerCards.get(i).getName()))
                    .toExternalForm()));

            imageView.setVisible(true);
        }

        List<LeaderCard> leaderCards = this.player.getActivatedLeaderCards();
        for (Integer i = 0; i < leaderCards.size(); i++) {
            imageView = ((ImageView) root.lookup("#personalBoard_LeaderCard" + i.toString()));

            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource(String.format("images/leaderCards/%1$s.png", leaderCards.get(i).getName()))
                    .toExternalForm()));

            imageView.setVisible(true);
        }
    }
}

