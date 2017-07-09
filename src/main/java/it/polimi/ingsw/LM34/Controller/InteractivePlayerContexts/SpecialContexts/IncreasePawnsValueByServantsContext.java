package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NetworkConnectionException;
import it.polimi.ingsw.LM34.Exceptions.Controller.NotEnoughServantsException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.SERVANTS;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class IncreasePawnsValueByServantsContext extends AbstractGameContext {
    private Integer servantsRequested;

    public IncreasePawnsValueByServantsContext() {
        contextType = INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
    }

    @Override
    public Integer interactWithPlayer(Object... args) throws IncorrectInputException, NotEnoughServantsException {
        try {
            this.servantsRequested = Math.max((Integer) args[0], 0);
        } catch(Exception ex) {
            LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            throw new IncorrectInputException();
        }

        if(this.gameManager.getCurrentPlayer().getResources().getResourceByType(SERVANTS) < servantsRequested)
            throw new NotEnoughServantsException();

        setChanged();
        notifyObservers(this);

        Integer availableServants = this.gameManager.getCurrentPlayer().getResources().getResourceByType(ResourceType.SERVANTS);
        Integer selectedServants = 0;
        if(this.getCurrentPlayer().isConnected())
            try {
                selectedServants = this.gameManager.getActivePlayerNetworkController().servantsSelection(availableServants, this.servantsRequested);
            } catch (NetworkConnectionException ex) {
                LOGGER.log(Level.INFO, ex.getMessage(), ex);
                this.getCurrentPlayer().setDisconnected();
            }
        Validator.checkValidity(selectedServants, this.servantsRequested, availableServants);

        return selectedServants;
    }

    public void duplicateServantsRequirements() {
        this.servantsRequested *= 2;
    }
}
