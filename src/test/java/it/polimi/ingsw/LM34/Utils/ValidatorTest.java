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
        Validator validator = new Validator();
        List<?> data = Arrays.<Object>asList("aldo", "giovanni", "giacomo"); //list<?> means List<? extends Object>
        String input = "this string will not being parsed into Integer";

        validator.checkValidity(input, data);
    }

    @Test
    public void checkValidity2() throws Exception {
        Validator validator = new Validator();
        List<?> data = Arrays.<Object>asList("aldo", "giovanni", "giacomo");
        String input = "3";

        Integer boh = Integer.parseInt(input);

        assertEquals(validator.checkValidity(input, data).longValue(), 3);

    }

    @Test
    public void checkValidity3() throws Exception {
        Validator validator = new Validator();
        List<?> data = Arrays.<Object>asList("aldo", "giovanni", "giacomo");
        Integer input = 3;

        validator.checkValidity(input, data);

    }

    @Test
    public void checkValidity4() throws Exception {
        Validator validator = new Validator();
        String input = "3";

        validator.checkValidity(input);
    }

    @Test
    public void checkValidity5() throws Exception {
        Validator validator = new Validator();
        Integer input = 0;
        Integer max = 0;

        validator.checkValidity(input, max);

    }

    @Test
    public void checkLeaderValidity() throws Exception {
        Validator validator = new Validator();
        String choice = "Lorenzo Il Magnifico";
        List<LeaderCard> leaderCards = new ArrayList<>();
        LeaderCard lorenzo_il_magnifico = new LeaderCard("lorenzo il magnifico", null, null, true);
        leaderCards.add(lorenzo_il_magnifico);
        leaderCards.add(lorenzo_il_magnifico);

        validator.checkLeaderValidity(leaderCards, choice);

    }

}