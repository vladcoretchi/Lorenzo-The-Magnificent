package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext extends Observable implements ContextInterface {
    public void initContext() {
        setChanged();
        notifyObservers("siamo in una fase particolare del turno");
        setChanged();
        notifyObservers("chiama i bonus che si devono attivare in questo turno");
    }

    @Override
    public void initContext(Player player) {
        
    }

    @Override
    public void endContext() {

    }
}


