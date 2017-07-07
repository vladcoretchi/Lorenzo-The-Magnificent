package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.LeaderCardsContext;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.LEADER_CARDS_CONTEXT;

public class CopyOtherLeader extends AbstractEffect implements Observer {
    private static final long serialVersionUID = -2188110517761913150L;

    @Override
    public void update(Observable o, Object arg) {
       LeaderCardsContext callerContext = (LeaderCardsContext) arg;
       callerContext.copyOtherLeaderAbility();
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(LEADER_CARDS_CONTEXT).addObserver(this);
    }
}
