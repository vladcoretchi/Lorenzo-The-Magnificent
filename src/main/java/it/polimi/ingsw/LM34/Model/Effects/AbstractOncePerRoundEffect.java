package it.polimi.ingsw.LM34.Model.Effects;

import java.io.Serializable;

public abstract class AbstractOncePerRoundEffect extends AbstractEffect {
    private static final long serialVersionUID = 5179518676657243177L;

    protected Boolean used;

    public Boolean isUsed() {
        return this.isUsed();
    }

    public void newTurn() {
        this.used = false;
    }
}
