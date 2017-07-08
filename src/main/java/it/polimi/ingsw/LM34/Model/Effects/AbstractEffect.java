package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;

import java.io.Serializable;

public abstract class AbstractEffect implements Serializable {
    private static final long serialVersionUID = 2089703062600133920L;

    public abstract void applyEffect(AbstractGameContext callerContext);
}
