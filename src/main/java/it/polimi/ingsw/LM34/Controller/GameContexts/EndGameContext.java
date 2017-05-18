package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 15/05/2017.
 */

//here all excomunication cards of III period are all notified to apply their malus
public class EndGameContext  extends AbstractGameContext {
    public void initContext(Player player) {
        setChanged();
        notifyObservers(player);
    }

    public void endContext() {
        setChanged();
        notifyObservers("siamo alla fine del game");
    }

    }

