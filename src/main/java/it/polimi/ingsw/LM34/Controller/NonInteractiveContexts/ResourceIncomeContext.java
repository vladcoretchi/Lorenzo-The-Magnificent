package it.polimi.ingsw.LM34.Controller.NonInteractiveContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Resources;

import javax.annotation.Resource;

import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

//This context does not provide an interaction with the player but it is important
// for the effects that are applied when a player receives resources
public class ResourceIncomeContext extends AbstractGameContext {
    private Resources income;

    public ResourceIncomeContext() {
        this.contextType = ContextType.RESOURCE_INCOME_CONTEXT;
        this.income = new Resources();
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException {
        setChanged();
        notifyObservers(this);

        this.gameManager.getCurrentPlayer().addResources(income);

        return null;
    }

    public void initIncome() {
        /*Reset the temporary value*/
        income = new Resources();
    }

    /**
     * Called by the observers so that they add or subtract their effects from the final income bonus
     */
    public void setIncome(Resources res) {
        this.income.sumResources(res);
    }

    public Resources getIncome() {
        return this.income;
    }
}
