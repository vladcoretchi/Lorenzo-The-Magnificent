package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.WorkingAreaValueEffect;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TURN_CONTEXT;

//TODO: remember to activate these rewards in the controller at the beginning of the phase of each player
public class PerRoundLeaderReward extends AbstractEffect implements Observer {
    private Resources resources;
    private Integer councilPrivilege;
    private WorkingAreaValueEffect workingAreaValueEffect; //"francesco sforza, leonardo da vinci"

    public PerRoundLeaderReward(Resources resources, Integer councilPrivilege) {
        this.resources = resources;
        this.councilPrivilege = councilPrivilege;
        this.workingAreaValueEffect = null;
    }

    public PerRoundLeaderReward(WorkingAreaValueEffect valueEffect) {
        this.resources = new Resources();
        this.councilPrivilege = 0;
        this.workingAreaValueEffect = valueEffect;
    }

    public Resources getResources() {
        return this.resources;
    }

    public Integer getCouncilPrivilege() {
        return this.councilPrivilege;
    }

    public Optional<WorkingAreaValueEffect> getWorkingAreaValueEffect() {
        return Optional.ofNullable(this.workingAreaValueEffect);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Sono PerRoundLeaderReward e sono stato notificato");

          /*  ((ResourceIncomeContext)gameManager.getContextByType(RESOURCE_INCOME_CONTEXT)).handleResources(player, resources);
            player.addCouncilPrivileges(councilPrivilege);
        }*/
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(TURN_CONTEXT).addObserver(this);

        System.out.println("mi sono iscritto al contesto");
        /*VOID*/
    }

    @Override
    public boolean isOncePerRound() {
        return true; //all these leader bonuses are activable once per round
    }
}
