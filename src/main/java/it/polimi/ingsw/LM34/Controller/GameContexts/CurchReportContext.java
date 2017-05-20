package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext  extends AbstractGameContext {


    public void initContext(ArrayList<Player> players) {
        //TODO:implementation
        setChanged(); //trigger sisto IV if is an observer
        notifyObservers(ContextStatus.ENTERED);
    }


    @Override
    public void initContext() {}

    @Override
    public ContextType getType() {
        return ContextType.CURCH_REPORT_CONTEXT;
    }

    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }



}


