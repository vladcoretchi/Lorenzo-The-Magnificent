package it.polimi.ingsw.LM34.Exceptions.Model;

/**
 * This exception is invoked inside the {@link it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard}
 * if a card of unknown {@link it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor}
 * is stored in it
 */
public class InvalidCardType extends Exception {
    private static final long serialVersionUID = 6780713289649362092L;

    public InvalidCardType(String s) {
        super("Invalid card type");
    }
}
