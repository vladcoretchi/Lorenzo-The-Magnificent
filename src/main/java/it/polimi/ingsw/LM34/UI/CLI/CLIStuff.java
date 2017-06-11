package it.polimi.ingsw.LM34.UI.CLI;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by vladc on 5/27/2017.
 */
public final class CLIStuff {
    public static final IgnoreInput ignoreInput = new IgnoreInput();

    public static final Scanner readUserInput = new Scanner(System.in);
    public static final PrintWriter printToConsole = new PrintWriter(System.out, true);

    /**
     * to print colored message to console, will be used ANSI escape code
     * https://en.wikipedia.org/wiki/ANSI_escape_code
     */

    /**
     * escape sequence, needed to set or reset colors
     */
    private static final char ESCAPE_SEQUENCE = (char)27;

    /**
     * SGR sequence to print message in red
     */
    private static final String RED = "[31m";

    /**
     * SGR sequence to print all following message in default color
     */
    private static final String RESET_COLOR_TO_DEFAULT = "[0m";

    /**
     * set the errors messages in red
     */
    private static final String ERROR_MESSAGE_COLOR = ESCAPE_SEQUENCE+RED;

    /**
     * reset all following message's color to default
     */
    private static final String RESET_COLOR = ESCAPE_SEQUENCE + RESET_COLOR_TO_DEFAULT;


    private static final String DIVIDER = "========================================================================";

    private static final String SPLASH_SCREEN =
        " __       ______   .______    _______ .__   __.  ______    ______     __   __\n" +
        "|  |     /  __  \\  |   _  \\  |   ____||  \\ |  | |      /  /  __  \\   |  | |  |\n" +
        "|  |    |  |  |  | |  |_)  | |  |__   |   \\|  | `--/  /  |  |  |  |  |  | |  |\n" +
        "|  |    |  |  |  | |      /  |   __|  |  . `  |   /  /   |  |  |  |  |  | |  |\n" +
        "|  `---.|  `--'  | |  |\\  \\-.|  |____ |  |\\   |  /  /---.|  `--'  |  |  | |  `---.\n" +
        "|______| \\______/  | _| `.__||_______||__| \\__| /_______| \\______/   |__| |______|\n" +
        "\n" +
        ".___  ___.      ___       _______ .__   __.  __   _______  __    ______   ______\n" +
        "|   \\/   |     /   \\     /  _____||  \\ |  | |  | |   ____||  |  /      | /  __  \\\n" +
        "|  \\  /  |    /  ^  \\   |  |  __  |   \\|  | |  | |  |__   |  | |  ,----'|  |  |  |\n" +
        "|  |\\/|  |   /  /_\\  \\  |  | |_ | |  . `  | |  | |   __|  |  | |  |     |  |  |  |\n" +
        "|  |  |  |  /  _____  \\ |  |__| | |  |\\   | |  | |  |     |  | |  `----.|  `--'  |\n" +
        "|__|  |__| /__/     \\__\\ \\______| |__| \\__| |__| |__|     |__|  \\______| \\______/\n" +
        "                                                                                   ";

    public static final void printSplashScreen() {
        printLine(SPLASH_SCREEN);
    }

    public static final void printLine(String message) {
        printToConsole.println(message);
    }

    public static final void printError(String message) {
        printToConsole.println(CLIStuff.ERROR_MESSAGE_COLOR + message + CLIStuff.RESET_COLOR);
    }

    public static final void printFormat(String format, Object... args) {
        printToConsole.format(format, args);
    }
}
