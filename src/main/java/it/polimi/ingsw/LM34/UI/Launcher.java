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

    public static String cliOrGui;
    public static String newOrOldGame;
    public static String rmiOrSocket;

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
            cliOrGui = Cli.startMenu();
            if (cliOrGui.equalsIgnoreCase("cli") || cliOrGui.equalsIgnoreCase("gui"))
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
            newOrOldGame = Cli.printMenu();
            if(newOrOldGame.equalsIgnoreCase("new game") || newOrOldGame.equals("1"))
                userInputIsValid = true;
            else if(newOrOldGame.equalsIgnoreCase("previous game") || newOrOldGame.equals("2"))
                printToConsole.println(ERROR_MESSAGE_COLOR+"you have 0 previous games"+RESET_COLOR);
            else
                printToConsole.println(ERROR_MESSAGE_COLOR+"please choose new game or previous game"+RESET_COLOR);
        }

        userInputIsValid = false;

        /**
         * this cycle will call user to choose between Rmi or Socket, and repeat this question until the user's answer will be 'rmi' or 'socket', ignoring uppercase
         */
        while(!userInputIsValid) {
            rmiOrSocket = Cli.choiceConnectionType();
            if(rmiOrSocket.equalsIgnoreCase("rmi") || rmiOrSocket.equalsIgnoreCase("socket") || rmiOrSocket.equals("1") || rmiOrSocket.equals("2"))
                userInputIsValid = true;
            else
                printToConsole.println(ERROR_MESSAGE_COLOR+"please choose rmi or socket"+RESET_COLOR);
        }

        userInputIsValid = false;

        /**
         * this cycle will check if user will type 'help', followed by a card`s name. In this case, it will return all information about searched card.
         */
        while(!userInputIsValid) {
            StringTokenizer stringDividedIntoTokens = new StringTokenizer(Cli.helper());

            /**
             * it'll take the first token of user's input. This token should be "help"
             */
            HELPER_CARD_NAME = stringDividedIntoTokens.nextToken();

            if(HELPER_CARD_NAME.equalsIgnoreCase("help")) {

                /**
                 * this cycle will remove any of useless initial space in user's input
                 */
                while(stringDividedIntoTokens.hasMoreTokens()) {

                    HELPER_CARD_NAME += stringDividedIntoTokens.nextToken();

                    /**
                     * this will maintain the original spaces between words in card's name
                     */
                    HELPER_CARD_NAME += " ";
                }

                /**
                 * this will remove the word "help" at beginning of user's input. By this way, the server will receive
                 * only the useful information
                 */
                HELPER_CARD_NAME = HELPER_CARD_NAME.substring(4);

                /**
                 * this regex will check if the user's input will contain at least 1 character
                 */
                if(HELPER_CARD_NAME.matches(".*[a-zA-Z].*")) {

                    printToConsole.println(HELPER_CARD_NAME);//only for debug. In stable version, HELPER_CARD_GAME will be sent to server
                    userInputIsValid = true;
                }
                else
                    printToConsole.println("card's name cannot be left blank");
            }

            else
                printToConsole.println(ERROR_MESSAGE_COLOR+"to use helper, command must start with 'help'"+RESET_COLOR);
        }

        userInputIsValid = false;

    }
}

