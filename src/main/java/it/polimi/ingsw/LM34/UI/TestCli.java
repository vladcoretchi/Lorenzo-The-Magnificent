package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.UI.CLI.IOInterface;

import org.json.*;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.String;

/**
 * this class was built on {@link AbstractUI}. It implement all method body that will be used to describe and manage Cli
 * this class also implements {@link IOInterface}, the interface that remap I/O streams
 */

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

    private static final String PATH_TO_CONFIG_JSON = "./src/main/resources/configurations/config.json";

    /**
     * this method will be called when the console will print on screen the Splash screen, at the beginning of it's lifecycle
     */
    @Override
    public void printSplashScreen() {

        printToConsole.println(SPLASH_SCREEN);
    }

    /**
     * this method will be called when the console need to separate some information to others
     */

    @Override
    public void printDivider() {
        printToConsole.println(DIVIDER);
    }

    /**
     * this method will be called when the console will start to interact to the user; it will print a menu that allow to the user to choice how Ui he wants to use between Cli or Gui
     * @return the user's choice
     */
    @Override
    public String startMenu() {
        printToConsole.println("Welcome to Lorenzo il Magnifico. " +
                "Do you wish to play using CLI or GUI? "
        );

        return readUserInput.nextLine();

    }

    /**
     * this method will be called when the console will ask to user to insert his username and password
     */

    @Override
    public void loginMenu() {

        printToConsole.println("Please insert your username: ");
        playerUsername = readUserInput.nextLine();

        printToConsole.println("please insert your password: ");
        playerPassword = readUserInput.nextLine();

    }

    /**
     * this method will be called when the console will ask user if he wants to start new game or continue previous game
     * @return the user's choice
     */

    @Override
    public String printMenu() {
        printToConsole.println("Game menu:\n" +
                "1) Start new game\n" +
                "2) Restore previous game "
        );

        return readUserInput.nextLine();
    }

    /**
     * this method will be called when the console will ask user what kind of connection technology he wants to use, Rmi or Socket
     * @return the user's choice
     */
    @Override
    public String choiceConnectionType() {
        printToConsole.println("which technology do you wish to use to connect to the server?\n" +
                "1) RMI\n" +
                "2) Socket\n"
        );

        return readUserInput.nextLine();
    }

    /**
     * this method will be called when, in game, user type "help", followed by a request.
     * if the request will be card's name, the helper will display all information about this card
     */
    @Override
    public void helper(String userSearchedItem) {
        String allLinesRead = "";
        try {
        BufferedReader bufferedReader = new BufferedReader((new FileReader(PATH_TO_CONFIG_JSON)));
        StringBuilder stringBuilder = new StringBuilder();
        String lineRead = bufferedReader.readLine();
        while(lineRead != null) {
            stringBuilder.append(lineRead);
            lineRead = bufferedReader.readLine();
        }
        allLinesRead = stringBuilder.toString();
        bufferedReader.close();
    } catch (IOException ex) {
        ex.printStackTrace();
    }

    JSONObject allLineReadsToJson = new JSONObject(allLinesRead);

    }

    /**
     * this method will be called when the console will print towers on screen
     */

    @Override
    public void printTowers() {
        //TODO: print towers one bottom others, color-coded
    }

}

