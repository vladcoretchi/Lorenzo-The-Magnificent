package it.polimi.ingsw.LM34.UI.GUI.GuiViews;

import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
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

public class PersonalBoardView extends Application implements DialogInterface {
    private Parent root;
    private PersonalBoard playerPersonalBoard;

    @FXML
    private Group personalBoardCards;

    public PersonalBoardView(Player player) {
        this.playerPersonalBoard = player.getPersonalBoard();
    }

    public void show() {
        try {
            this.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("views/personalBoard.fxml"));
        Stage primaryStage = stage;
        stage.setResizable(false);
        stage.setFullScreen(false);
        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResource("images/icon.png").toExternalForm()));
        primaryStage.setTitle("PersonalBoard - Lorenzo il Magnifico by CranioCreations");
        primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setResizable(false);
        primaryStage.show();
        updatePersonalBoard(playerPersonalBoard);
        primaryStage.setOnHidden(e -> primaryStage.close());

    }

    @Override
    public void setStyle(Dialog dialog) {

    }

    public void updatePersonalBoard(PersonalBoard playerPersonalBoard) {
        Integer PERSONAL_BOARD_BUILDING_SLOTS = 6; //min 0 max 20
        Integer ACTUALLY_PLAYER_CHARACTER_CARDS = 6; //min 0 max 20
        Integer PERSONAL_BOARD_TERRITORIES_SLOT = 6; //min 0 max 20
        Integer ACTUALLY_PLAYER_VENTURE_CARDS = 6; //min 0 max 20
        Integer ACTUALLY_PLAYER_LEADER_CARDS = 5; //min 0 max 5

        ImageView imageView;

        /*List<AbstractDevelopmentCard> yellowCards =  playerPersonalBoard.getDevelopmentCardsByType(DevelopmentCardColor.YELLOW).get();
        List<AbstractDevelopmentCard> greenCards = playerPersonalBoard.getDevelopmentCardsByType(DevelopmentCardColor.GREEN).get();
        List<AbstractDevelopmentCard> purpleCards = playerPersonalBoard.getDevelopmentCardsByType(DevelopmentCardColor.PURPLE).get();
        List<AbstractDevelopmentCard> blueCards = playerPersonalBoard.getDevelopmentCardsByType(DevelopmentCardColor.BLUE).get();*/


        //for (Integer i = 0; i < yellowCards.size(); i++)

        for(Integer i = 0; i < PERSONAL_BOARD_BUILDING_SLOTS; i++) {

            imageView = ((ImageView) root.lookup("#personalBoard_YELLOWCard"+ i.toString()));

            imageView.setImage(new Image(Thread.currentThread()
                     .getContextClassLoader().getResource("images/developmentCards/buildings/Bank.png")
                     .toExternalForm()));

            imageView.setVisible(true);
        }

        for(Integer i = 0; i < PERSONAL_BOARD_TERRITORIES_SLOT; i++) {
            imageView = ((ImageView) root.lookup("#personalBoard_GREENCard"+ i.toString()));

            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/developmentCards/territories/Castle.png")
                    .toExternalForm()));

            imageView.setVisible(true);
        }

        for(Integer i = 0; i < ACTUALLY_PLAYER_VENTURE_CARDS; i++) {
            imageView = ((ImageView) root.lookup("#personalBoard_PURPLECard"+ i.toString()));

            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/developmentCards/ventures/Building the Bastions.png")
                    .toExternalForm()));

            imageView.setVisible(true);
        }

        for(Integer i = 0; i < ACTUALLY_PLAYER_CHARACTER_CARDS; i++) {
            imageView = ((ImageView) root.lookup("#personalBoard_BLUECard"+ i.toString()));

            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/developmentCards/characters/Abbess.png")
                    .toExternalForm()));

            imageView.setVisible(true);
        }

        for(Integer i = 0; i < ACTUALLY_PLAYER_LEADER_CARDS; i++) {
            imageView = ((ImageView) root.lookup("#personalBoard_LeaderCard"+ i.toString()));

            imageView.setImage(new Image(Thread.currentThread()
                    .getContextClassLoader().getResource("images/leaderCards/Bartolomeo Colleoni.png")
                    .toExternalForm()));

            imageView.setVisible(true);
        }
    }
}

