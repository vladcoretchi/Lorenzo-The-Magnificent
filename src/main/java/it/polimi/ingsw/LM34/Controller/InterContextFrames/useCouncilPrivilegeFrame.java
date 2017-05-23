package it.polimi.ingsw.LM34.Controller.InterContextFrames;

import it.polimi.ingsw.LM34.Model.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GiulioComi on 23/05/2017.
 */
public class useCouncilPrivilegeFrame implements Observer {


    public void interactWithPlayer(Player player) {
        //TODO: handle the player choice on how to use the privileges
    }

    @Override
    public void update(Observable o, Object arg) {
        Integer numberOfPrivileges = (Integer) arg;

        //TODO interact with player
    }
}
