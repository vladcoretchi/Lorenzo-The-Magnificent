package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
}