package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test(expected = IncorrectInputException.class)
    public void checkValidity() throws IncorrectInputException {
        List<?> data = Arrays.<Object>asList("aldo", "giovanni", "giacomo"); //list<?> means List<? extends Object>
        String input = "this string will not being parsed into Integer";

        Validator.checkValidity(input, data);
    }

    @Test
    public void checkValidity2() throws Exception {
        List<?> data = Arrays.<Object>asList("aldo", "giovanni", "giacomo");
        String inputInString = "3";

        Integer inputInInteger = Integer.parseInt(inputInString);

        Validator.checkValidity(inputInInteger, data);

    }

    @Test
    public void checkValidity3() throws Exception {
        List<?> data = Arrays.<Object>asList("aldo", "giovanni", "giacomo");
        Integer input = 3;

        Validator.checkValidity(input, data);

    }

    @Test
    public void checkValidity4() throws Exception {
        String input = "3";

        Validator.checkValidity(input);
    }

    @Test
    public void checkValidity5() throws Exception {
        Integer input = 0;
        Integer max = 0;

        Validator.checkValidity(input, max);

    }

    @Test
    public void checkLeaderValidity() throws Exception {
        String choice = "Lorenzo Il Magnifico";
        List<LeaderCard> leaderCards = new ArrayList<>();
        LeaderCard lorenzo_il_magnifico = new LeaderCard("lorenzo il magnifico", null, null, true);
        leaderCards.add(lorenzo_il_magnifico);
        leaderCards.add(lorenzo_il_magnifico);

        Validator.checkLeaderValidity(leaderCards, choice);

    }

    /*
    	/*TEST Validation Methods here
    public static void main (String[] args) {
        Player player = new Player("cicoio", PawnColor.RED, new PersonalBoard());
        Configurator.loadConfigs();

        /*PLAYERNAMES STRINGS
        ArrayList<String> playersName = new ArrayList<>();
        playersName.add("pippo");

        /*PAWNCOLORS ENUM
        ArrayList<PawnColor> pawnColors = new ArrayList<>();
        pawnColors.add(PawnColor.RED);
        pawnColors.add(PawnColor.GREEN);

        GameManager gameManager = new GameManager(new GameRoom(),playersName);
        gameManager.setupGameContexts();
        System.out.println("Scrivi");
        Scanner in = new Scanner(System.in);

        /*CONTEXTTYPE ENUM
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