package it.polimi.ingsw.LM34.ui;

public interface UIController {

    void login(String nickname);
    void setNetworkSettings(String networkType, String address, int port);
    String getCurrentPlayerNickname();
    Boolean isCurrentPlayer(Turn CurrentTurn);
    void endTurn();
}