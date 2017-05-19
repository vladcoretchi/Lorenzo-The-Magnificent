package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 *
 * This represents the context for both instant and permanent FamilyMemberValueEffects
 */
public class TowerContext  extends AbstractGameContext {

    public void initContext(Player player) {

        //TODO: handle Filippo Brunelleschi, Cesare Borgia
    }


    @Override
    public ContextType getType() {
        return ContextType.TOWER_CONTEXT;
    }

    public void endContext() {

    }
}
