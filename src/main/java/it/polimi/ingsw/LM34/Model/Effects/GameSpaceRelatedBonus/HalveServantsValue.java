package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class HalveServantsValue implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) arg;

        //TODO: halve the servants value during "IncreasePawnsValueByServantsContext"
    }

    public void registerObserverToContext(ArrayList<AbstractGameContext> contexts) throws NoSuchContextException {
        Utilities.getContextByType(contexts, ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT).addObserver(this);
    }
}
