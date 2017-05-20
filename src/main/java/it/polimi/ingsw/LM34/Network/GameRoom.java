package it.polimi.ingsw.LM34.Network;

import it.polimi.ingsw.LM34.Controller.GameManager;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 07/05/2017.
 */
public class GameRoom {
    private ArrayList<RemotePlayer> players;
    private GameManager gameManager;
}


//TODO: handle disconnections, timer of turn, ecc