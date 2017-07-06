package it.polimi.ingsw.LM34.Exceptions.Controller;

/**
 * This exception is invoked inside the MarketContext
 * (an {@link it.polimi.ingsw.LM34.Controller.AbstractGameContext)
 * if the player has the {@link it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.MarketBan}
 * penalty and cannot place anymore his {@link it.polimi.ingsw.LM34.Model.FamilyMember}
 * inside the {@link it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market}
 */
public class MarketBanException extends Exception {
    private static final long serialVersionUID = -2815915924069249805L;

    public MarketBanException() {
        super("You cannot place a family member in the market because of the penalty");
    }
}
