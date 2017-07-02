package it.polimi.ingsw.LM34.Model.Effects;

import java.io.Serializable;

public abstract class AbstractOncePerRoundEffect extends AbstractEffect implements Serializable {
    protected Boolean used;

    public Boolean isUsed() {
        return this.isUsed();
    }

    public void newTurn() {
        this.used = false;
    }
}
