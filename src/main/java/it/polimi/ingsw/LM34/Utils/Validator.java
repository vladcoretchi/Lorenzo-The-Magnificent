package it.polimi.ingsw.LM34.Utils;


import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.GameRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by GiulioComi on 30/5/2017.
 */


/**
 * This static class is unbelievable useful for validate data inserted by player both client and server side
 * Client side is used as a preliminary approach to avoid malicious input
 *Server side is adopted broadly by all contexts that calls the overloaded methods they need
 * Server side is needed to enforce and grant consistency of the games tate
 */

/*Proudly designed and implemented by 0 :-)*/
public final class Validator {


    /*Validate input based on expected data types and ranges*/
    public static final Integer checkValidity(String input, List<?> data) throws IncorrectInputException {
        try {
            /*Try to extract an Integer from input*/
            Integer inputValue = Integer.parseInt(input);
            checkValidity(inputValue, data);
            return inputValue;
        } catch (Exception e) {
            throw new IncorrectInputException();
        }
    }

    /*Validate input based on expected data types and ranges*/
    public static final void checkValidity(Integer input, List<?> data) throws IncorrectInputException {
        try {
            /*Calculates range*/
            Integer min = 0;
            Integer max = data.size();

            /*Check if input meets the requirements*/
            if(input < min || input > max)
                throw new IncorrectInputException();
        } catch (Exception e) {
            throw new IncorrectInputException();
        }
    }

    /*Validate Number type inputs*/
    public static final void checkValidity(String input) throws IncorrectInputException {
        try {
            Integer.parseInt(input);
        } catch(Exception e) {
            throw new IncorrectInputException();
        }
    }


    /*Validate Number type inputs*/
    public static final void checkValidity(Integer input, Integer max) throws IncorrectInputException {
        if(input < 0 || input > max)
            throw new IncorrectInputException();
    }

    /*TEST Validation Methods here*/
    public static void main (String[] args) {
        Player player = new Player("cicoio", PawnColor.RED, new PersonalBoard());
        Configurator.loadConfigs();

        /*PLAYERNAMES STRINGS*/
        ArrayList<String> playersName = new ArrayList<>();
        playersName.add("pippo");

        /*PAWNCOLORS ENUM*/
        ArrayList<PawnColor> pawnColors = new ArrayList<>();
        pawnColors.add(PawnColor.RED);
        pawnColors.add(PawnColor.GREEN);

        GameManager gameManager = new GameManager(new GameRoom(),playersName);
        gameManager.setupGameContexts();
        System.out.println("Scrivi");
        Scanner in = new Scanner(System.in);

        /*CONTEXTTYPE ENUM*/
        ArrayList<PlayerSelectableContexts> accessibleContexts = new ArrayList<>();
        for(PlayerSelectableContexts p : PlayerSelectableContexts.values())
            accessibleContexts.add(p);

        /*TOWERSLOTS*/
        /*ArrayList<TowerSlot> towerSlots = new ArrayList<>();
        towerSlots.add(new TowerSlot(null,1,1,1));
        towerSlots.add(new TowerSlot(null,2,2,2));
        towerSlots.add(new TowerSlot(null,3,3,3));
        towerSlots.add(new TowerSlot(null,4,4,4));*/

        /*AREATYPE*/

        /*BONUSTILE*/ //TODO: let the player choose one from the list provided at the beginning

        /*LEADERCARDS*/ //TODO: let the player choose one at each step from the list provided at the beginning



       /* try {
            checkValidity(in.nextLine(), towerSlots);
        } catch(Exception e) {
            e.printStackTrace();
        }*/

    }

}
