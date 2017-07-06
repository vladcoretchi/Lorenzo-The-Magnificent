package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ValidatorTest {

    /**
     * this method will check if a non-Integer String effectively won't be cast to Integer
     * @throws IncorrectInputException if the input string cannot be cast to Integer
     */
    @Test(expected = IncorrectInputException.class)
    public void checkValidity() throws IncorrectInputException {
        List<?> data = Arrays.<Object>asList("aldo", "giovanni", "giacomo");
        String input = "this string will not being parsed into Integer";

        Validator.checkValidity(input, data);
    }

    /**
     * this test will check if a String that contains a number will be correctly casted into an Integer
     * @throws Exception IncorrectInputException if the String won't be cast to Integer
     */
    @Test
    public void checkValidity2() throws Exception {
        List<?> data = Arrays.<Object>asList(PawnColor.BLUE, PawnColor.GREEN, PawnColor.RED);
        Integer inputInInteger = 2;

        Validator.checkValidity(inputInInteger, data);
    }

    /**
     * this test will check if input will be >= 0 and <= Player List's size
     * @throws Exception IncorrectInputException will be thrown if input will be < 0 or > Player List's size
     */
    @Test
    public void checkValidity3() throws Exception {
        Player player = new Player("luca", PawnColor.GREEN, new PersonalBoard());
        Player player2 = new Player("rolando", PawnColor.GREEN, new PersonalBoard());
        Player player3 = new Player("alessandro", PawnColor.GREEN, new PersonalBoard());
        List<?> data = Arrays.<Object>asList(player, player2, player3);
        Integer input = 2;

        Validator.checkValidity(input, data);
    }

    /**
     * this test will check if a String that contains a number will be correctly casted into an Integer
     * @throws Exception IncorrectInputException if the String won't be casted into Integer
     */
    @Test
    public void checkValidity4() throws Exception {
        String input = "3";

        Validator.checkValidity(input);
    }

    /**
     * this test will check if input will be > 0 and < max
     * @throws Exception IncorrectInputException if input will be < 0 or > max
     */
    @Test
    public void checkValidity5() throws Exception {
        Integer input = 0;
        Integer max = 0;

        Validator.checkValidity(input, max);
    }
}