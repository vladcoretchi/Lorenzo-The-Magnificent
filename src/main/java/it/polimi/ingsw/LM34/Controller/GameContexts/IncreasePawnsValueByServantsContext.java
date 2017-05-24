package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class IncreasePawnsValueByServantsContext extends AbstractGameContext {


    @Override
    public void initContext() {
    }

    //TODO: o is the context from which this context is called
    private void interactWithPlayer(Observable o, Player player) {
        AbstractGameContext callerContext = (AbstractGameContext) o;
        //TODO: implement what player can do here and modify the model in this controller class
        setChanged();
        notifyObservers(ContextStatus.ENTERED);
    }

    @Override
    public ContextType getType() {
        return ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT;
    }

    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
        //phaseContext.interactWithPlayer();
    }
}
