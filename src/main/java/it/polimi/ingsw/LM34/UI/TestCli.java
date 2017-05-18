import interfacce.CliSplashScreen;
import interfacce.IOInterface;
import interfacce.PlayerLoginInfo;
import interfacce.CliDivider;

import java.util.HashMap;

public class TestCli extends GenericUi implements CliSplashScreen, IOInterface, PlayerLoginInfo, CliDivider {

    @Override
    public void printSplashScreen() {

        printToConsole.println(SPLASH_SCREEN);
    }

    @Override
    public String startMenu() {

        printToConsole.println("welcome! \n" +
                               "1)Login \n" +
                               "2)Exit \n");

        return menuChoice = readUserInput.nextLine();
    }

    @Override
    public HashMap login() {

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
        networkChoiced = readUserInput.nextLine();

        return networkChoiced;
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    public static void main(String[] args) {
            TestCli primaCli = new TestCli();
            primaCli.printSplashScreen();
            primaCli.login();
            primaCli.printMenu();
        }
}
