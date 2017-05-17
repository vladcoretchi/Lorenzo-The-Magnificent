package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 17/05/2017.
 */
public abstract class AbstractGameContext extends Observable  {

    public abstract void initContext(Player player);

    public abstract void endContext();
}
