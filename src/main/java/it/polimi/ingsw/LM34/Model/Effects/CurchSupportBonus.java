package it.polimi.ingsw.LM34.Model.Effects;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 16/05/2017.
 *
 * This class represents Sisto IV peculiar effect and registers itself to CurchReportContext
 */
public class CurchSupportBonus extends AbstractEffect implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ok, sono stato chiamato perchè siamo nel contesto di Curch Report");
    }

}
