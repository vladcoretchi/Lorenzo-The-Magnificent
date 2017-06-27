package it.polimi.ingsw.LM34.Enums.Model;

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
