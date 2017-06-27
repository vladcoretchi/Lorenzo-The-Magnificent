package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.TowersContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TowerSlotPenalty extends AbstractEffect implements Observer {
    private List<Integer> noResourcesFromTowerLevels; //handles card "predicatore"

    /**
     *
     * @param noResourcesFromTowerActionSpaces the levels of towers from where the player does not gain a reward
     */
    public TowerSlotPenalty(List<Integer> noResourcesFromTowerActionSpaces) {
        this.noResourcesFromTowerLevels = noResourcesFromTowerActionSpaces;

    }

    public List<Integer> getBannedRewardTowerLevels() {
        return this.noResourcesFromTowerLevels;
    }

    @Override
    public void update(Observable o, Object arg) {
        TowersContext context = (TowersContext) o;
        context.setHasPenalty(true);
    }

//towers

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
    }
}

