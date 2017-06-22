package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;

import java.io.Serializable;

/**
 * Created by vladc on 5/13/2017.
 */
public abstract class AbstractEffect implements Serializable {
    //protected ArrayList<ContextType> observableContexts;
    /**
     * @return true if the observer is activable once per round (e.g. SkipTurn, PerRoundLeaderReward)
     * Default is false
     */
    public boolean isOncePerRound() {
        return false;
    }


    //public ArrayList<ContextType> getContextToBeSubscribedTo() {return observableContexts;}

    public abstract void applyEffect(AbstractGameContext callerContext);

}
