package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameContexts.TowersContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
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

    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(contexts, ContextType.DEVELOPMENT_CARD_ACQUIRE_CONTEXT).addObserver(this);
    }


    public void resetApplyFlag() {

    }

}

