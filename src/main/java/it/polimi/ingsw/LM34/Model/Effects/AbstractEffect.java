package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by vladc on 5/13/2017.
 */
public abstract class AbstractEffect {

    /**
     *
     * @return true if the observer is activable once per round (e.g. SkipTurn, PerRoundLeaderReward)
     * Default is false
     */
    public boolean isOncePerRound() {
        return false;
    }


    public abstract void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts);

    public abstract void applyEffect(ArrayList<AbstractGameContext> contexts, Player player);

}
