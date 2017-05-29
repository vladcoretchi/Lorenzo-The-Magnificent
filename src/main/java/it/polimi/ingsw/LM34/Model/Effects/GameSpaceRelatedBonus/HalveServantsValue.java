package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class HalveServantsValue extends AbstractEffect implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Player player = (Player) arg;

        //TODO: halve the servants value during "IncreasePawnsValueByServantsContext"
    }

//increase pawns by value context

    @Override
    public void applyEffect(Player player) {

    }


}
