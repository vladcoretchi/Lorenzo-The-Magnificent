package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 16/05/2017.
 *
 * This class represents Sisto IV peculiar effect and registers itself to CurchReportContext
 */
public class CurchSupportBonus extends AbstractEffect implements Observer, ObserverEffect {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ok, sono stato chiamato perchè siamo nel contesto di Curch Report");
    }

    public void registerObserverToContext(ArrayList<AbstractGameContext> contexts) throws NoSuchContextException {
        Utilities.getContextByType(contexts, ContextType.CURCH_REPORT_CONTEXT).addObserver(this);
    }
}
