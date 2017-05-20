package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.UI.CLI.IOInterface;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class TestCli extends AbstractUI implements IOInterface {

    private static final String SPLASH_SCREEN = " __        ______   .______       _______ .__   __.  ________    ______\n" +
            "|  |      /  __  \\  |   _  \\     |   ____||  \\ |  | |       /   /  __  \\\n" +
            "|  |     |  |  |  | |  |_)  |    |  |__   |   \\|  | `---/  /   |  |  |  |\n" +
            "|  |     |  |  |  | |      /     |   __|  |  . `  |    /  /    |  |  |  |\n" +
            "|  `----.|  `--'  | |  |\\  \\----.|  |____ |  |\\   |   /  /----.|  `--'  |\n" +
            "|_______| \\______/  | _| `._____||_______||__| \\__|  /________| \\______/\n" +
            "\n" +
            " __   __\n" +
            "|  | |  |\n" +
            "|  | |  |\n" +
            "|  | |  |\n" +
            "|  | |  `----.\n" +
            "|__| |_______|\n" +
            "\n" +
            ".___  ___.      ___       _______ .__   __.  __   _______  __    ______   ______\n" +
            "|   \\/   |     /   \\     /  _____||  \\ |  | |  | |   ____||  |  /      | /  __  \\\n" +
            "|  \\  /  |    /  ^  \\   |  |  __  |   \\|  | |  | |  |__   |  | |  ,----'|  |  |  |\n" +
            "|  |\\/|  |   /  /_\\  \\  |  | |_ | |  . `  | |  | |   __|  |  | |  |     |  |  |  |\n" +
            "|  |  |  |  /  _____  \\ |  |__| | |  |\\   | |  | |  |     |  | |  `----.|  `--'  |\n" +
            "|__|  |__| /__/     \\__\\ \\______| |__| \\__| |__| |__|     |__|  \\______| \\______/\n" +
            "                                                                                   ";

    private static final String DIVIDER = "========================================================================";

    private String playerUsername;
    private String playerPassword;

    protected Pair<String, String> playerUsernamePassword;


    @Override
    public void printSplashScreen() {

        printToConsole.println(SPLASH_SCREEN);
    }

    @Override
    public void printDivider() {
        printToConsole.println(DIVIDER);
    }

    @Override
    public String startMenu() {
        printToConsole.println("Welcome to Lorenzo il Magnifico. " +
                "Do you wish to play using CLI or GUI? "
        );

        return readUserInput.nextLine();

    }

    @Override
    public void loginMenu() {

        printToConsole.println("Please insert your username: ");
        playerUsername = readUserInput.nextLine();

        printToConsole.println("please insert your password: ");
        playerPassword = readUserInput.nextLine();

    }

    @Override
    public String printMenu() {
        printToConsole.println("Game menu:\n" +
                "1) Start new game\n" +
                "2) Restore previous game "
        );

        return readUserInput.nextLine();
    }

    @Override
    public String choiceConnectionType() {
        printToConsole.println("which technology do you wish to use to connect to the server?\n" +
                "1) RMI\n" +
                "2) Socket\n"
        );

        return readUserInput.nextLine();
    }

    @Override
    public void printTowers() {
        //TODO: print towers one bottom others, color-coded
    }

}
