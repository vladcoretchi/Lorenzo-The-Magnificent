package it.polimi.ingsw.LM34.Utils;

/**
 * Created by GiulioComi on 30/05/2017.
 */

import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
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
    public static final void checkValidity(String input,List<?> dataType) throws IncorrectInputException {
        try {
            /*Try to extract an Integer from input*/
            Integer inputValue = Integer.parseInt(input);
            /*Calculates range*/
            Integer min = 1; //TODO: FIX THIS BASE INDEX HERE OR IN THE CONTEXTS
            Integer max = dataType.toArray().length;
            /*Check if input meets the requirements*/
            if(inputValue < min || inputValue > max)
                throw new IncorrectInputException();

        } catch (Exception e) {
            throw new IncorrectInputException();
        }
    }

    /*Validate Number type inputs*/
    public static final void checkValidity(String input) throws IncorrectInputException {
        if(Integer.parseInt(input) <= 0)
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
        ArrayList<TowerSlot> towerSlots = new ArrayList<>();
        towerSlots.add(new TowerSlot(null,1,1,1));
        towerSlots.add(new TowerSlot(null,2,2,2));
        towerSlots.add(new TowerSlot(null,3,3,3));
        towerSlots.add(new TowerSlot(null,4,4,4));

        /*AREATYPE*/

        /*BONUSTILE*/ //TODO: let the player choose one from the list provided at the beginning

        /*LEADERCARDS*/ //TODO: let the player choose one at each step from the list provided at the beginning



        try {
            checkValidity(in.nextLine(), towerSlots);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
