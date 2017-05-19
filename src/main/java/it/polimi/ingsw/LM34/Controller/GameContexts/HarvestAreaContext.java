package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class HarvestAreaContext extends AbstractGameContext {

    public void initContext(Player player) {
        
    }

    @Override
    public ContextType getType() {
        return ContextType.HARVEST_AREA_CONTEXT;
    }

    public void endContext() {

    }

}
