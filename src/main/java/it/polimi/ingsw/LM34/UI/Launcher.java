package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.UI.CLI.IOInterface;

import java.util.StringTokenizer;

/**
 * this class will be the first called at the beginning at the game, and will manage all user's choice, repeating questions until user's answer will be correct
 * this class implements {@link IOInterface}, the interface that remap I/O streams
 */

public class Launcher implements IOInterface {

    /**
     * to print colored message to console, will be used ANSI escape code
     * https://en.wikipedia.org/wiki/ANSI_escape_code
     */

    /**
     * escape sequence, needed to set or reset colors
     */
    public static final char ESCAPE_SEQUENCE = (char)27;

    /**
     * SGR sequence to print message in red
     */
    public static final String RED = "[31m";

    /**
     * SGR sequence to print all following message in default color
     */
    public static final String RESET_COLOR_TO_DEFAULT = "[0m";

    /**
     * set the errors messages in red
     */
    public static final String ERROR_MESSAGE_COLOR = ESCAPE_SEQUENCE+RED;

    /**
     * reset all following message's color to default
     */
    public static final String RESET_COLOR = ESCAPE_SEQUENCE+RESET_COLOR_TO_DEFAULT;

    public static String HELPER_CARD_NAME;

    public static void main(String[] args) {

        /**
         * instance of {@TestCli}, needed to use composition
         */
        TestCli Cli = new TestCli();

        System.out.println(ERROR_MESSAGE_COLOR+"boh"+RESET_COLOR);

        /**
         * variable that will remain false until the user's input will be correct
         */
        Boolean userInputIsValid = false;

        String playerUsername;

        Cli.printSplashScreen();
        Cli.printDivider();

        /**
         * this cycle will call user to choose between Cli or Gui, and repeat this question until the answer will be 'cli' or 'gui', ignoring uppercase
         */
        while(!userInputIsValid) {

            if (Cli.startMenu().equalsIgnoreCase("cli") || Cli.startMenu().equalsIgnoreCase("gui"))
                userInputIsValid = true;
            else
                printToConsole.println(ERROR_MESSAGE_COLOR+ "Please choose cli or gui" +RESET_COLOR);
        }

        /**
         * username and password will be sent to server
         */
        Cli.loginMenu();

        /**
         * when the previous cycle is over, userInputIsValid will be set to false, to allow the following cycle to verify the next user's answer
         */
        userInputIsValid = false;

        /**
         * this cycle will call user to start new game or restore previous game.
         */
        while(!userInputIsValid) {
            if(Cli.printMenu().equalsIgnoreCase("new game"))
                userInputIsValid = true;
            else
                printToConsole.println(ERROR_MESSAGE_COLOR+"you have 0 previous games"+RESET_COLOR);
        }

        userInputIsValid = false;

        /**
         * this cycle will call user to choose between Rmi or Socket, and repeat this question until the user's answer will be 'rmi' or 'socket', ignoring uppercase
         */
        while(!userInputIsValid) {
            if(Cli.choiceConnectionType().equalsIgnoreCase("rmi") || Cli.choiceConnectionType().equalsIgnoreCase("socket"))
                userInputIsValid = true;
            else
                printToConsole.println(ERROR_MESSAGE_COLOR+"please choose rmi or socket"+RESET_COLOR);
        }

        userInputIsValid = false;

        /**
         * this cycle will check if user will type 'help', follwed by a card`s name. In this case, it will return all information about searched card.
         */
        while(!userInputIsValid) {
            StringTokenizer stringDividedIntoTokens = new StringTokenizer(Cli.helper());

            HELPER_CARD_NAME = stringDividedIntoTokens.nextToken();

            if(HELPER_CARD_NAME.equalsIgnoreCase("help")) {
                HELPER_CARD_NAME = stringDividedIntoTokens.nextToken();
                printToConsole.println(HELPER_CARD_NAME); //only for debug. On production, HELPER_CARD_NAME will be sent to server
                userInputIsValid = true;
            }

            else
                printToConsole.println(ERROR_MESSAGE_COLOR+"to use helper, command must start with 'help'"+RESET_COLOR);
        }

        userInputIsValid = false;

        Cli.printTowers();

    }
}

