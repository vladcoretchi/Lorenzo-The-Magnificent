package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;

/**
 * Created by GiulioComi on 17/05/2017.
 */
public abstract class AbstractGameContext extends Observable  {
    public abstract void initContext();

    public abstract ContextType getType();

    private void interactWithPlayer(Player player) {
        //TODO: implement what player can do here and modify the model in this controller class
    }

    public abstract void endContext();


}
