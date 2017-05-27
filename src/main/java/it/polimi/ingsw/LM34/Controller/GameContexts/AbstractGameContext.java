package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by GiulioComi on 17/05/2017.
 */
public abstract class AbstractGameContext extends Observable  {
    protected ArrayList<AbstractGameContext> contexts;



    public abstract ContextType getType();

    public void interactWithPlayer(Player player) {
        //TODO: implement what player can do here and modify the model in this controller class
    }


    public void setContexts(ArrayList<AbstractGameContext> contexts) {
        this.contexts = contexts;
    }

    /**
     This static method is called often by observers to register themselves to the right context
     */
    public AbstractGameContext getContextByType(ContextType contextType) {
        for(AbstractGameContext context : contexts)
            if(context.getType() == ContextType.CURCH_REPORT_CONTEXT)
                return context;

        return null;
    }

}
