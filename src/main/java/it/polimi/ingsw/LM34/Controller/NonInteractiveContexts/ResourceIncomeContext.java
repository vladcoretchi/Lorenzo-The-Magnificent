package it.polimi.ingsw.LM34.Controller.NonInteractiveContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Resources;

//This context does not provide an interaction with the player but it is important
// for the effects that are applied when a player receives resources
public class ResourceIncomeContext extends AbstractGameContext {
    private Resources income;


    public ResourceIncomeContext() {
        this.contextType = ContextType.RESOURCE_INCOME_CONTEXT;
        income = new Resources();
    }

    @Override
    public void interactWithPlayer() {/*Void*/}



    /**
     * Called by the observers so that they add or subtract their effects from the final income bonus
     */
    public void setIncome(Resources res) {
        income.sumResources(res);
    }

    public void finalizeIncome() {
        setChanged();
        notifyObservers(income);

        gameManager.getCurrentPlayer().addResources(income);
        /*Reset the temporary value*/
        income = new Resources();
    }
}
