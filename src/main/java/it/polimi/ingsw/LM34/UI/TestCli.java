package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.UI.CLI.CliSplashScreen;
import it.polimi.ingsw.LM34.UI.CLI.PlayerLoginInfo;
import it.polimi.ingsw.LM34.UI.CLI.CliDivider;
import it.polimi.ingsw.LM34.UI.CLI.IOInterface;

import java.util.HashMap;

public class TestCli extends GenericUI implements CliSplashScreen, IOInterface, PlayerLoginInfo, CliDivider {

    @Override
    public void printSplashScreen() { //creare una classe che verra` importata staticamente che conterra` tutte le costanti

        printToConsole.println(SPLASH_SCREEN);
    }
    @Override
    public void printSeparator() {
        printToConsole.println("\n========================================================================== \n");
    }

    @Override
    public String startMenu1() {

        printToConsole.println("welcome! \n" +
                "1)Login \n" +
                "2)Exit \n");

        return menuChoice = readUserInput.nextLine();
    }

    @Override
    public HashMap loginMenu() {

        printToConsole.println("Please insert your username: ");
        PLAYER_LOGIN_INFO.put("username", readUserInput.nextLine());

        printToConsole.println("please insert your password: ");
        PLAYER_LOGIN_INFO.put("password", readUserInput.nextLine());

        return PLAYER_LOGIN_INFO;

        //  password = readUserPassword.readLine("Please insert your password: "); //da verificare, probabilmente sarà necessario gestire anche la password usando readUserInput
    }

    @Override
    public void printMenu() {
        printToConsole.println("Game menu: " +
                "1) Start new game \n" +
                "2) Restore previous game \n" +
                "3) Exit \n"
        );

        menuChoice = readUserInput.nextLine();
    }

    @Override
    public String choiceConnectionType() {
        printToConsole.println("which technology do you wish to use to connect to the server? \n" +
                "1) RMI \n" +
                "2) Socket \n"
        );
        networkChoiced = readUserInput.nextLine(); //da mettere come costante in un importo statico, cosi come username

        return networkChoiced;
    }

    @Override
    public String startMenu() {
        printToConsole.println("Welcome to Lorenzo il Magnifico. \n" +
                "Do you wish to play using CLI or GUI? \n");
        return(readUserInput.nextLine());
    }
    @Override
    public void printTowers() {
        printToConsole.println("|  |\n" +
                "|  |\n" +
                "|  |\n" +
                "|  |\n" +
                "|  |\n" +
                "|  |\n" +
                "|  |\n" +
                "|  |\n" +
                "|  |\n" +
                "\nTower 1");

    }
    @Override
    public void exit() {
        System.exit(0);
    } //non molto consigliata, probabilmente da rimuovere e far terminare il programma in altro modo

    /*public static void main(String[] args) {
            TestCli primaCli = new TestCli();
            primaCli.printSplashScreen();
            primaCli.loginMenu();
            primaCli.printMenu();
        }*/
}
