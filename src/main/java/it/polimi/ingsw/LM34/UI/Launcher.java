package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.UI.CLI.IOInterface;

public class Launcher implements IOInterface {

    public static Boolean setUserInputToFalse() {
        return false;
    }
    public static void main(String[] args) {

        TestCli Cli = new TestCli();

        Boolean userInputIsValid = false;
        String playerUsername;

        Cli.printSplashScreen();
        Cli.printDivider();

        while(!userInputIsValid) {

            if (Cli.startMenu().equalsIgnoreCase("cli"))
                userInputIsValid = true;
            else
                printToConsole.println("gui choice is not available, please choose cli ");
        }

        Cli.loginMenu(); //Username and password will be sent to server

        userInputIsValid = setUserInputToFalse();

        while(!userInputIsValid) {
            if(Cli.printMenu().equalsIgnoreCase("new game"))
                userInputIsValid = true;
            else
                printToConsole.println("you have 0 previous games");
        }

        userInputIsValid = setUserInputToFalse();

        while(!userInputIsValid) {
            if(Cli.choiceConnectionType().equalsIgnoreCase("rmi") || Cli.choiceConnectionType().equalsIgnoreCase("socket"))
                userInputIsValid = true;
            else
                printToConsole.println("please choose rmi or socket");
        }

        userInputIsValid = setUserInputToFalse();

    }
}

