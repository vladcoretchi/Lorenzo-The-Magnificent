package it.polimi.ingsw.LM34.Controller.NonInteractiveContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;

import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.*;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.FAITH_POINTS;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.VICTORY_POINTS;

/**
 * This context does not provide an interaction with the player but it is important
 * for the effects that are applied when a player receives resources
 */
public class ResourceIncomeContext extends AbstractGameContext {
    private Resources income;
    private Boolean duplicateGoods;

    public ResourceIncomeContext() {
        this.contextType = ContextType.RESOURCE_INCOME_CONTEXT;
        this.income = new Resources();
    }

    @Override
    public Void interactWithPlayer(Object... args) throws IncorrectInputException {
        this.duplicateGoods = false;

        setChanged();
        notifyObservers(this);
        this.gameManager.getCurrentPlayer().getExcommunicationCards().
                            forEach(exCard -> {
                                if (exCard.getPenalty() instanceof ResourcesBonus)
                                        exCard.getPenalty().applyEffect(this);
                            });

        Integer multiplier = (this.duplicateGoods ? 1 : 2);

        this.gameManager.getCurrentPlayer().addResources(new Resources(
                this.income.getResourceByType(COINS) * multiplier,
                this.income.getResourceByType(WOODS) * multiplier,
                this.income.getResourceByType(STONES) * multiplier,
                this.income.getResourceByType(SERVANTS) * multiplier,
                this.income.getResourceByType(MILITARY_POINTS) * multiplier,
                this.income.getResourceByType(FAITH_POINTS) * multiplier,
                this.income.getResourceByType(VICTORY_POINTS) * multiplier));

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

    public void duplicateGoods() {
        this.duplicateGoods = true;
    }
}
