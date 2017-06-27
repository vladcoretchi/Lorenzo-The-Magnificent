package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.TowersContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TOWERS_CONTEXT;

public class NoOccupiedTowerTax extends AbstractEffect implements Observer {

    public NoOccupiedTowerTax() {}
    @Override
    public void update(Observable o, Object arg) {
    //TODO
        TowersContext context = (TowersContext) o;

    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(TOWERS_CONTEXT);
    }
}
