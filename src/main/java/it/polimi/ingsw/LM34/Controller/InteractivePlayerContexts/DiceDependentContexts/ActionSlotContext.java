package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.ACTION_SLOT_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class ActionSlotContext extends AbstractGameContext {
    private AbstractGameContext referenceContext;
    private Boolean ignoreSlotLimit;

    public ActionSlotContext() {
        this.contextType = ACTION_SLOT_CONTEXT;
        this.ignoreSlotLimit = false;
    }

    @Override
    public Boolean interactWithPlayer(Object... args) throws IncorrectInputException {
        ActionSlot slot;
        try {
            this.referenceContext = (AbstractGameContext) args[0];
            slot = (ActionSlot) args[1];
        } catch(Exception ex) {
            LOGGER.log(Level.FINEST, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }
        this.ignoreSlotLimit = false;

        setChanged();
        notifyObservers(this);

        return slot.isEmpty() || this.ignoreSlotLimit;
    }

    public AbstractGameContext getReferenceContext() {
        return this.referenceContext;
    }

    public Boolean getIgnoreOccupiedSlot() {
        return this.ignoreSlotLimit;
    }

    public void ignoreSlotLimit() {
        this.ignoreSlotLimit = true;
    }
}



