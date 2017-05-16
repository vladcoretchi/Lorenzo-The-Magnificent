package it.polimi.ingsw.LM34.Controller.GameContext;

import it.polimi.ingsw.LM34.Controller.ObserverBonus.ObserverCurchReport;
import it.polimi.ingsw.LM34.Controller.ObserverBonus.ObserverEndGame;

import java.util.Observable;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext extends Observable {
    public void initContext() {
        setChanged();
        notifyObservers("siamo in una fase particolare del turno");
        setChanged();
        notifyObservers("chiama i bonus che si devono attivare in questo turno");
    }
}


