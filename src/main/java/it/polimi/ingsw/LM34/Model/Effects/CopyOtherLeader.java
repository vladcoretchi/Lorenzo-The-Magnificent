package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.LeaderCardsContext;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.LEADER_CARDS_CONTEXT;

/**
 * Created by GiulioComi on 04/06/2017.
 */


/*Handle Lorenzo de Medici ability*/
public class CopyOtherLeader extends AbstractEffect implements Observer {

    @Override
    public void update(Observable o, Object arg) {
       LeaderCardsContext callerContext = (LeaderCardsContext) o;
       Player player = (Player) arg;

       callerContext.copyOtherLeaderAbility(player);

    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(LEADER_CARDS_CONTEXT).addObserver(this);
    }

}
