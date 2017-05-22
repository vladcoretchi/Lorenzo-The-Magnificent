package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.UI.CLI.IOInterface;

/**
 * this class will be the first called at the beginning at the game, and will manage all user's choice, repeating questions until user's answer will be correct
 * this class implements {@link IOInterface}, the interface that remap I/O streams
 */

public class Launcher implements IOInterface {

    public static void main(String[] args) {

        /**
         * instance of {@TestCli}, needed to use composition
         */
        TestCli Cli = new TestCli();

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
                printToConsole.println("please choose between Cli or Gui ");
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
                printToConsole.println("you have 0 previous games");
        }

        userInputIsValid = false;

        /**
         * this cycle will call user to choose between Rmi or Socket, and repeat this question until the user's answer will be 'rmi' or 'socket', ignoring uppercase
         */
        while(!userInputIsValid) {
            if(Cli.choiceConnectionType().equalsIgnoreCase("rmi") || Cli.choiceConnectionType().equalsIgnoreCase("socket"))
                userInputIsValid = true;
            else
                printToConsole.println("please choose rmi or socket");
        }

        userInputIsValid = false;

    }
}

