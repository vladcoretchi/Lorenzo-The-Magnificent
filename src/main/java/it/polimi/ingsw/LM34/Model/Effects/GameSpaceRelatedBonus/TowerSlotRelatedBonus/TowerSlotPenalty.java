package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.DiceDependentContexts.TowersContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/14/2017.
 */
public class TowerSlotPenalty extends AbstractEffect implements Observer {
    private Integer noResourcesFromTowerLevels[]; //handles card "predicatore"


    /**
     *
     * @param noResourcesFromTowerActionSpaces the levels of towers from where the player does not gain a reward
     */
    public TowerSlotPenalty(Integer noResourcesFromTowerActionSpaces[]) {
        this.noResourcesFromTowerLevels = noResourcesFromTowerActionSpaces;

    }

    public TowerSlotPenalty(Boolean marketBan) {
        this.noResourcesFromTowerLevels = null;
    }

    public Integer[] getBannedRewardTowerLevels() {
        return this.noResourcesFromTowerLevels;
    }



    @Override
    public void update(Observable o, Object arg) {
        TowersContext context = (TowersContext) o;
        context.setHasPenalty(true);
    }

//towers

    @Override
    public void applyEffect(AbstractGameContext callerContext, Player player) {

    }


}

