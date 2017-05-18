package it.polimi.ingsw.LM34.Model.Effects;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/14/2017.
 */


public class SkipFirstTurn extends AbstractEffect implements Observer {
    private Boolean skipFirstTurn;

    public SkipFirstTurn(Boolean skipFirstTurn) {
        this.skipFirstTurn = skipFirstTurn;
    }

    public Boolean hasToSkipFirstTurn() {
        return this.skipFirstTurn;
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}