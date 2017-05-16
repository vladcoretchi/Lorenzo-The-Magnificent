package it.polimi.ingsw.LM34.Controller.ObserverBonus;

import java.util.Observable;
import java.util.Observer;

;

/**
 * Created by GiulioComi on 15/05/2017.
 */
public class ObserverCurchReport implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ok, sono stato chiamato perchè siamo nel contesto di Curch Report");
    }
}
