package it.polimi.ingsw.LM34.UI.CLI;

import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.UI.AbstractUI;

import java.lang.String;

/**
 * this class was built on {@link AbstractUI}. It implement all method body that will be used to describe and manage Cli
 */

public class CLI extends AbstractUI {

    public void show() {
        // variable that will remain false until the user's input will be correct
        Boolean userInputIsValid = false;

        // this cycle will call user to choose between Rmi or Socket, and repeat this question until the user's answer will be 'rmi' or 'socket', ignoring uppercase
        while(!userInputIsValid) {
            String connectionTypeChoice = connectionTypeSelection();
            if(connectionTypeChoice.equalsIgnoreCase("rmi") || connectionTypeChoice.equals("1")) {
                networkClient = new RMIClient(SERVER_IP, RMI_PORT);
                userInputIsValid = true;
            }
            else if (connectionTypeChoice.equalsIgnoreCase("socket") || connectionTypeChoice.equals("2")) {
                networkClient = new SocketClient(SERVER_IP, SOCKET_PORT);
                userInputIsValid = true;
            }
            else
                CLIStuff.printToConsole.println(CLIStuff.ERROR_MESSAGE_COLOR + "please choose rmi or socket" + CLIStuff.RESET_COLOR);
        }

        loginMenu();
    }

    /**
     * this method will be called when the console will print on screen the Splash screen, at the beginning of it's lifecycle
     */
    @Override
    public void showSplashScreen() {
        CLIStuff.printToConsole.println(CLIStuff.SPLASH_SCREEN);
    }

    /**
     * this method will be called when the console need to separate some information to others
     */
    @Override
    public void printDivider() {
        CLIStuff.printToConsole.println(CLIStuff.DIVIDER);
    }

    /**
     * this method will be called when the console will ask to user to insert his username and password
     */
    @Override
    public void loginMenu() {
        CLIStuff.printToConsole.println("Please insert your username:");
        String playerUsername = CLIStuff.readUserInput.nextLine();

        CLIStuff.printToConsole.println("please insert your password:");
        String playerPassword = CLIStuff.readUserInput.nextLine();

        //TODO: login on server
    }

    /**
     * this method will be called when the console will ask user what kind of connection technology he wants to use, Rmi or Socket
     * @return the user's choice
     */
    @Override
    public String connectionTypeSelection() {
        CLIStuff.printToConsole.println("which technology do you wish to use to connect to the server?\n" +
                "1) RMI\n" +
                "2) Socket"
        );

        return CLIStuff.readUserInput.nextLine();
    }

    /**
     * this method will be called when, in game, user type "help", followed by a request.
     * if the request will be card's name, the helper will display all information about this card
     * if the request will be another user's name, the helper will display all information about that user
     */
    public String helper() {
        CLIStuff.printToConsole.println("type help + 'card name' to obtain all information about the searched card\n" +
                                        "type help + 'player name' to obtain all information about the searched player\n"+
                                        "currently, in-game are present x player, which names are ...\n");

        return CLIStuff.readUserInput.nextLine();
    }

    /**
     * this method will be called when the console will print towers on screen
     */
    @Override
    public void printTowers() {
        CLIStuff.printToConsole.println(" ___________ ...........   ___________ ...........   ___________ ...........   ___________ ...........\n" +
                "|           |    2     .  |           |    2     .  |           |    2     .  |           |     2    .\n" +
                "| marble    |  woods   .  |           |  stones  .  |           | military .  |           |   coins  .\n" +
                "| pit       |          .  |           |          .  |           | points   .  |           |          .\n" +
                "|           |...........  |           |...........  |           |...........  |           |...........          \n" +
                "|___________|     7       |___________|     7       |___________|     7       |___________|     7\n" +
                " ___________ ...........   ___________ ...........   ___________ ...........   ___________ ...........\n" +
                "|           |    1     .  |           |    1     .  |           |    1     .  |           |     1    .\n" +
                "| manor     |  woods   .  |           |  stones  .  |           | military .  |           |   coins  .\n" +
                "| house     |          .  |           |          .  |           | points   .  |           |          .\n" +
                "|           |...........  |           |...........  |           |...........  |           |...........          \n" +
                "|___________|     5       |___________|     5       |___________|     5       |___________|     5\n" +
                " \n" +
                " ___________ ...........   ___________ ...........   ___________ ...........   ___________ ...........\n" +
                "|           |          .  |           |          .  |           |          .  |           |          .\n" +
                "| fortified |          .  |           |          .  |           |          .  |           |          .\n" +
                "| town      |          .  |           |          .  |           |          .  |           |          .\n" +
                "|           |...........  |           |...........  |           |...........  |           |...........          \n" +
                "|___________|     3       |___________|     3       |___________|     3       |___________|     3\n" +
                " \n" +
                " ___________ ...........   ___________ ...........   ___________ ...........   ___________ ...........\n" +
                "|           |          .  |           |          .  |           |          .  |           |          .\n" +
                "| trading   |          .  |           |          .  |           |          .  |           |          .\n" +
                "| town      |          .  |           |          .  |           |          .  |           |          .\n" +
                "|           |...........  |           |...........  |           |...........  |           |...........          \n" +
                "|___________|     1       |___________|     1       |___________|     1       |___________|     1");

        //TODO: print towers color-coded
    }

}

