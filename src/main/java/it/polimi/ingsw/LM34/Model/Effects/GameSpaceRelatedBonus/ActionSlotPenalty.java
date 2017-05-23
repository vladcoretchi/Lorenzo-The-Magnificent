package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameContexts.DevelopmentCardAcquireContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
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
        DevelopmentCardAcquireContext context = (DevelopmentCardAcquireContext) o;
        context.setHasPenalty(true);
    }

    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(contexts, ContextType.DEVELOPMENT_CARD_ACQUIRE_CONTEXT).addObserver(this);
    }


    public void resetApplyFlag() {

    }

}

