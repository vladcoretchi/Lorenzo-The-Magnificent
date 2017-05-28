package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameContexts.TowersContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 18/05/2017.
 */
//"cesare borgia"
public class NoMilitaryRequirementsForTerritory extends AbstractEffect implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        //TODO: skip control on how many military points the player has if cesare borgia is activated
        TowersContext context = (TowersContext) o;

    }

    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(contexts, ContextType.TOWERS_CONTEXT).addObserver(this);
    }

    @Override
    public void applyEffect(ArrayList<AbstractGameContext> contexts, Player player) {

    }


}
