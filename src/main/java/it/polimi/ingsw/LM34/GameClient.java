package it.polimi.ingsw.LM34;

import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.UI.AbstractUI;
import it.polimi.ingsw.LM34.UI.CLI.CLI;
import it.polimi.ingsw.LM34.UI.CLI.CLIStuff;

/**
 * Created by vladc on 5/25/2017.
 */
public class GameClient {
    private static AbstractUI ui;

    public static void main(String[] args) {
        /**
         * variable that will remain false until the user's input will be correct
         */
        Boolean userInputIsValid = false;

        /**
         * this cycle will call user to choose between Cli or Gui, and repeat this question until the answer will be 'cli' or 'gui', ignoring uppercase
         */
        while(!userInputIsValid) {
            showSplashScreen();
            String viewChoice = viewSelection();

            if (viewChoice.equalsIgnoreCase("cli")) {
                ui = new CLI();
                userInputIsValid = true;
            }
            else if(viewChoice.equalsIgnoreCase("gui")) {
                //TODO
                userInputIsValid = false;
            }
            else
                CLIStuff.printToConsole.println(CLIStuff.ERROR_MESSAGE_COLOR + "Please choose cli or gui" + CLIStuff.RESET_COLOR);
        }

        ui.show();
    }

    /**
     * this method will be called when the console will start to interact to the user; it will print a menu that allow to the user to choice how Ui he wants to use between Cli or Gui
     * @return the user's choice
     */
    public static String viewSelection() {
        CLIStuff.printToConsole.println("Do you wish to play using CLI or GUI?");

        return CLIStuff.readUserInput.nextLine();
    }

    /**
     * this method will be called when the console will print on screen the Splash screen, at the beginning of it's lifecycle
     */
    public static void showSplashScreen() {
        CLIStuff.printToConsole.println(CLIStuff.SPLASH_SCREEN);
    }
}
