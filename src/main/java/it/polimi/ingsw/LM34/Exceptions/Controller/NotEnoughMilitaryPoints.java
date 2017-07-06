package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * Thi exception is invoked when the player wants to buy another
 * {@link it.polimi.ingsw.LM34.Model.Cards.TerritoryCard}
 * but does not have enough military points based on the corresponding track
 */
public class NotEnoughMilitaryPoints extends Exception {
    private static final long serialVersionUID = -7497697988174455243L;


    public NotEnoughMilitaryPoints() {
        super("You do not have enough military points for buying another territory card");
    }
}
