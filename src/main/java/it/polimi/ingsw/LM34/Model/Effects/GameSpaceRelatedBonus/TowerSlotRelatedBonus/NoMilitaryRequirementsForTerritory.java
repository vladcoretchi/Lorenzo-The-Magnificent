package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.TowersContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.TOWERS_CONTEXT;

public class NoMilitaryRequirementsForTerritory extends AbstractEffect implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        //TODO: skip control on how many military points the player has if cesare borgia is activated
        TowersContext callerContext = (TowersContext) arg;
    }

    @Override
    public void applyEffect(AbstractGameContext callerContext) {
        callerContext.getContextByType(TOWERS_CONTEXT).addObserver(this);
    }
}
