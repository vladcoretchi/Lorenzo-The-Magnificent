package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class IncreasePawnsValueByServantsContext extends AbstractGameContext {
    private Integer servantsRequested;

    public IncreasePawnsValueByServantsContext() {
        contextType = INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
    }

    @Override
    public Integer interactWithPlayer(Object... args) throws IncorrectInputException {
        try {
            this.servantsRequested = (Integer) args[0];
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        setChanged();
        notifyObservers(this);

        Integer availableServants = this.gameManager.getCurrentPlayer().getResources().getResourceByType(ResourceType.SERVANTS);
        Integer selectedServants = this.gameManager.getActivePlayerNetworkController().servantsSelection(availableServants, this.servantsRequested);
        Validator.checkValidity(selectedServants, this.servantsRequested, availableServants);

        return selectedServants;
    }

    public void duplicateServantsRequirements() {
        this.servantsRequested *= 2;
    }
}
