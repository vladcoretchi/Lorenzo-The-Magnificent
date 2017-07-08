package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.UseCouncilPrivilegeContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Effects.AbstractOncePerRoundEffect;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.WorkingAreaValueEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TURN_CONTEXT;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

public class PerRoundLeaderReward extends AbstractOncePerRoundEffect implements Observer {
    private static final long serialVersionUID = 5004649676960788989L;
    
    private Resources resources;
    private Integer councilPrivilege;
    private WorkingAreaValueEffect workingAreaValueEffect;

    public PerRoundLeaderReward(Resources resources, Integer councilPrivilege) {
        this.resources = resources;
        this.councilPrivilege = councilPrivilege;
        this.workingAreaValueEffect = null;
    }

    public PerRoundLeaderReward(WorkingAreaValueEffect valueEffect) {
        this.resources = null;
        this.councilPrivilege = null;
        this.workingAreaValueEffect = valueEffect;
    }

    public Optional<Resources> getResources() {
        return Optional.ofNullable(this.resources);
    }

    public Optional<Integer> getCouncilPrivilege() {
        return Optional.ofNullable(this.councilPrivilege);
    }

    public Optional<WorkingAreaValueEffect> getWorkingAreaValueEffect() {
        return Optional.ofNullable(this.workingAreaValueEffect);
    }

    @Override
    public void update(Observable o, Object arg) {
        AbstractGameContext callerContext = (AbstractGameContext) arg;
        if(!this.used) {

        if(this.resources != null)
            ((ResourceIncomeContext) callerContext.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT)).setIncome(this.resources);

        if(this.councilPrivilege != null && this.councilPrivilege > 0)
            try {
                ((UseCouncilPrivilegeContext) callerContext.getContextByType(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT)).interactWithPlayer(councilPrivilege);
            } catch(IncorrectInputException ex) {
                LOGGER.log(Level.WARNING, ex.getMessage(), ex);
            }

        if(this.workingAreaValueEffect != null)
            this.workingAreaValueEffect.applyEffect(callerContext);
        }
        this.used = true;
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(TURN_CONTEXT).addObserver(this);
    }

    @Override
    public boolean isOncePerRound() {
        return true;
    }
}
