package it.polimi.ingsw.LM34.Enums.UI;

/**
 *Information provided to players about what is happening to others during the game
 */
public enum GameInformationType {
    INFO_DISCONNECTED_PLAYER("Disconnected"),
    INFO_NEW_PLAYER_TURN("'s Turn"),
    INFO_RECONNECTED_PLAYER("Reconnected"),
    INFO_EXCOMMUNICATION("Excommunicated"),
    ADDITIONAL_CARD_ACTION("Special card acquire action"),
    ADDITIONAL_WORKING_AREA_ACTION("Special action on production or harvest areas");
    private String infoType;

    GameInformationType(String infoType) {
        this.infoType = infoType;
    }

    @Override
    public String toString() {
        return this.infoType;
    }
}

