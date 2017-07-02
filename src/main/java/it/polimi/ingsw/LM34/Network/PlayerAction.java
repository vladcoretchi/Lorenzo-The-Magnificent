package it.polimi.ingsw.LM34.Network;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;

import java.io.Serializable;

public class PlayerAction implements Serializable {
    private static final long serialVersionUID = 6920991146140642631L;

    private PlayerSelectableContexts context;
    private Object action;

    public PlayerAction(PlayerSelectableContexts context, Object action) {
        this.context = context;
        this.action = action;
    }

    public PlayerSelectableContexts getContext() {
        return context;
    }

    public Object getAction() {
        return action;
    }
}
