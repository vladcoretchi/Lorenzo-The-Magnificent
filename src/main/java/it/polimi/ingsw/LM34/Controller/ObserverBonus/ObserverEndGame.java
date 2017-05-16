package it.polimi.ingsw.LM34.Controller.ObserverBonus;

import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 15/05/2017.
 */
public class ObserverEndGame implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) arg;
        player.addResources(new Resources(0,0,0,0,0,0,5));
        System.out.println("gli observer dell'endgame han finito");
    }
}