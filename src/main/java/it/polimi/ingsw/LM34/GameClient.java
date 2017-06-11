package it.polimi.ingsw.LM34;

import it.polimi.ingsw.LM34.UI.CLI.CLI;
import it.polimi.ingsw.LM34.UI.GUI.GUI;
import it.polimi.ingsw.LM34.UI.UIInterface;

import static it.polimi.ingsw.LM34.UI.CLI.CLIStuff.*;

public class GameClient {
    private static UIInterface ui;

    public static void main(String[] args) {
        printSplashScreen();

        /*variable that will remain false until the user's input will be correct*/
        Boolean userInputIsValid = false;
        while(!userInputIsValid) {
            String viewChoice = viewSelection();

            if (viewChoice.equalsIgnoreCase("cli")) {
                ui = new CLI();
                userInputIsValid = true;
            }
            else if(viewChoice.equalsIgnoreCase("views")) {
                ui = new GUI();
                userInputIsValid = true;
            }
            else
                printError("Invalid selection!");
        }

        ui.show();
    }

    /**
     * this method will be called when the console will start to interact to the user; it will print a menu that allow to the user to choice how Ui he wants to use between Cli or Gui
     * @return the user's choice
     */
    public static String viewSelection() {
        printLine("Do you wish to play using CLI or GUI?");

        return readUserInput.nextLine();
    }
}
