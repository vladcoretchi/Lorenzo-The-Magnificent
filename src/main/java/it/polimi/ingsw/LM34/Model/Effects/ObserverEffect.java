package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 20/05/2017.
 */
public interface ObserverEffect {

    public void registerObserverToContext(ArrayList<AbstractGameContext> contexts) throws NoSuchContextException;

}
