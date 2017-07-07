package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.TowersContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TOWERS_CONTEXT;

public class TowerSlotPenalty extends AbstractEffect implements Observer {
    private static final long serialVersionUID = 2670003935360198120L;

    private List<Integer> noResourcesFromTowerLevels;

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
        TowersContext callerContext = (TowersContext) arg;
        if(this.noResourcesFromTowerLevels.contains(callerContext.getSlotDiceValue()))
            callerContext.setSlotsRewardPenalty();
    }

//towers

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(TOWERS_CONTEXT).addObserver(this);
    }
}

