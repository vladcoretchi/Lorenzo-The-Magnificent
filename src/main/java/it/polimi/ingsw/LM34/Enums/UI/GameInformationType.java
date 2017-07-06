package it.polimi.ingsw.LM34.Enums.UI;

/**
 *Informations provided to players about what is happening to others
 */
public enum GameInformationType {
    INFO_DISCONNECTED_PLAYER(" disconnected"),
    INFO_NEW_PLAYER_TURN("'s Turn"),
    INFO_RECONNECTED_PLAYER(" reconnected"),
    INFO_EXCOMMUNICATION(" excommunicated");
    private String infoType;

    GameInformationType(String infoType) {
        this.infoType = infoType;
    }

    @Override
    public String toString() {
        return this.infoType;
    }
}

