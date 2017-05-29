package it.polimi.ingsw.LM34.Model.Effects;

import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 16/05/2017.
 *
 * This class represents Sisto IV peculiar effect and registers itself to CurchReportContext
 */
public class CurchSupportBonus extends AbstractEffect implements Observer {
    private boolean alreadyApplied;
    @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) arg;
        player.addResources(new Resources(0,0,5));
        
    }

//curch report



    @Override
    public void applyEffect(Player player) {

    }

    @Override
    public boolean isOncePerRound() {
        return true;
    }

}
