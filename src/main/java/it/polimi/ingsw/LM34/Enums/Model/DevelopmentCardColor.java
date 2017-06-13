package it.polimi.ingsw.LM34.Enums.Model;

/**
 * Created by Giulio Comi on 5/2/2017.
 */
public enum DevelopmentCardColor {
    GREEN("territories"),
    BLUE("characters"),
    YELLOW("buildings"),
    PURPLE("ventures"),
    MULTICOLOR("Multicolor");

    private String cardType;

    DevelopmentCardColor(String type) {
        this.cardType = type;
    }

    public String getDevType() {
        return cardType;
    }
}
