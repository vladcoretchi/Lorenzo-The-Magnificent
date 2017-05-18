package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext  extends AbstractGameContext {

    public void initContext(Player player) {
        setChanged();
        notifyObservers("siamo in una fase particolare del turno");
    }

    public void endContext() {


    }


}


