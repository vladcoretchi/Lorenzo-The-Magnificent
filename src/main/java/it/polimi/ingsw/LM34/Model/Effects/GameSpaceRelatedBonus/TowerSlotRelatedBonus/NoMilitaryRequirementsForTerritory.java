package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.TowersContext;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

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

  //towers

    @Override
    public void applyEffect(AbstractGameContext callerContext, Player player) {

    }


}
