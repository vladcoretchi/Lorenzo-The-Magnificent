package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Model.Player;
import javafx.beans.InvalidationListener;

import java.util.Observable;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext  extends Observable implements ContextInterface {

    public void initContext(Player player) {
        setChanged();
        notifyObservers("siamo in una fase particolare del turno");
    }

    public void endContext() {


    }


}


