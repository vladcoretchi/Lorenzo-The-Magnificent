package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/14/2017.
 */
public class ActionSlotPenalty extends AbstractEffect implements Observer {
    private Integer noResourcesFromTowerLevels[];

    public ActionSlotPenalty(Integer noResourcesFromTowerActionSpaces[]) {
        this.noResourcesFromTowerLevels = noResourcesFromTowerActionSpaces;

    }

    public ActionSlotPenalty(Boolean marketBan) {
        this.noResourcesFromTowerLevels = null;
    }

    public Integer[] getNoResourcesFromTowerLevels() {
        return this.noResourcesFromTowerLevels;
    }




//TODO: implement this
public void applyEffect() {}

    @Override
    public void update(Observable o, Object arg) {

    }
}

