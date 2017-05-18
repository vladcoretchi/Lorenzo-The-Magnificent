package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/14/2017.
 */
public class ActionSlotPenalty extends AbstractEffect implements Observer {
    private Integer noResourcesFromTowerLevels[]; //handles card "predicatore"


    /**
     *
     * @param noResourcesFromTowerActionSpaces the levels of towers from where the player does not gain a reward
     */
    public ActionSlotPenalty(Integer noResourcesFromTowerActionSpaces[]) {
        this.noResourcesFromTowerLevels = noResourcesFromTowerActionSpaces;

    }

    public ActionSlotPenalty(Boolean marketBan) {
        this.noResourcesFromTowerLevels = null;
    }

    public Integer[] getBannedRewardTowerLevels() {
        return this.noResourcesFromTowerLevels;
    }



    @Override
    public void update(Observable o, Object arg) {

    }
}

