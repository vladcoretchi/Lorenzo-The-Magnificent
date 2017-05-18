package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.WorkingAreaValueEffect;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by GiulioComi on 18/05/2017.
 */

//TODO: apply DECORATOR pattern for aggregate in a unique instances all the rewards the leader gave in each phase

//TODO: remember to activate this rewards in the controller at the beginning of the phase of each player
public class LeaderPerPhaseReward {
    private Resources resources;
    private Integer councilPrivilege;
    private WorkingAreaValueEffect workingAreaValueEffect; //"francesco sforza, leonardo da vinci"

    public LeaderPerPhaseReward(Resources resources, Integer councilPrivilege) {
        this.resources = resources;
        this.councilPrivilege = councilPrivilege;
    }

    public LeaderPerPhaseReward(WorkingAreaValueEffect valueEffect) {
        this.workingAreaValueEffect = valueEffect;
    }

    public Resources getResources() {
        return this.resources;
    }

    public Integer getCouncilPrivilege() {
        return this.councilPrivilege;
    }

    public WorkingAreaValueEffect getWorkingAreaValueEffect() {
        return this.workingAreaValueEffect;
    }
}
