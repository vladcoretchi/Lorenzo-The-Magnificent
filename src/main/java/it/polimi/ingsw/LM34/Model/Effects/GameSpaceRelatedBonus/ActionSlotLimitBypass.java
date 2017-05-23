package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ActionSlotLimitBypass extends AbstractEffect implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        
    }

    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(contexts, ContextType.ACTION_SLOT_CONTEXT).addObserver(this);
    }


    public void resetApplyFlag() {

    }

}
