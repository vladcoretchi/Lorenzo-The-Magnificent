package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 24/05/2017.
 */
public class CouncilPalaceContext extends AbstractGameContext {
    @Override
    public void initContext() {

    }

    @Override
    public ContextType getType() {
        return ContextType.COUNCIL_PALACE_CONTEXT;
    }

    public void interactWithPlayer(Player player) {

    }

    @Override
    public void endContext() {
        //phaseContext.interactWithPlayer();
    }
}
