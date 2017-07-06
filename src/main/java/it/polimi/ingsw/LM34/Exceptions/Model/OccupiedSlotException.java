package it.polimi.ingsw.LM34.Exceptions.Model;

/**
 * This exception is invoked by {@link it.polimi.ingsw.LM34.Model.Boards.GameBoard.GameSpace}
 * when trying to insert {@link it.polimi.ingsw.LM34.Model.FamilyMember} in an occupied
 * {@link it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot}
 */
public class OccupiedSlotException extends Exception {
    private static final long serialVersionUID = -4176038414472754596L;

    public OccupiedSlotException() {
        super("this slot is already occupied by another pawn");
    }
}
