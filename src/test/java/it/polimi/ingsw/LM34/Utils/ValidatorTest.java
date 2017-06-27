package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test(expected = IncorrectInputException.class)
    public void checkValidity() throws IncorrectInputException {
        List<?> data = Arrays.<Object>asList("aldo", "giovanni", "giacomo");
        String input = "this string will not being parsed into Integer";

        Validator.checkValidity(input, data);
    }

    @Test
    public void checkValidity2() throws Exception {
        List<?> data = Arrays.<Object>asList(PawnColor.BLUE, PawnColor.GREEN, PawnColor.RED);
        String inputInString = "3";

        Integer inputInInteger = Integer.parseInt(inputInString);

        Validator.checkValidity(inputInInteger, data);

    }

    @Test
    public void checkValidity3() throws Exception {
        Player player = new Player("luca", PawnColor.GREEN, new PersonalBoard());
        Player player2 = new Player("rolando", PawnColor.GREEN, new PersonalBoard());
        Player player3 = new Player("alessandro", PawnColor.GREEN, new PersonalBoard());
        List<?> data = Arrays.<Object>asList(player, player2, player3);
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



       /* try {
            checkValidity(in.nextLine(), towerSlots);
        } catch(Exception e) {
            e.printStackTrace();
        }*/

    }