package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 20/05/2017.
 */
public interface ObserverEffect {
    
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts);

    public void resetApplyFlag();
}
