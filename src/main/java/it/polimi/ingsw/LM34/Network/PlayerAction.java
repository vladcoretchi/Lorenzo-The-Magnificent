package it.polimi.ingsw.LM34.Network;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;

import java.io.Serializable;

/**
 * Created by vladc on 6/17/2017.
 */
public class PlayerAction implements Serializable {
    private static final long serialVersionUID = 6920991146140642631L;

    private PlayerSelectableContexts context;

    private Object action;
}
