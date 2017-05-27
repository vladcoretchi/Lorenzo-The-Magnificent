package it.polimi.ingsw.LM34.UI.CLI;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by vladc on 5/27/2017.
 */
public final class CLIStuff {
    public static final Scanner readUserInput = new Scanner(System.in);
    public static final PrintWriter printToConsole = new PrintWriter(System.out, true);

    /**
     * to print colored message to console, will be used ANSI escape code
     * https://en.wikipedia.org/wiki/ANSI_escape_code
     */

    /**
     * escape sequence, needed to set or reset colors
     */
    public static final char ESCAPE_SEQUENCE = (char)27;

    /**
     * SGR sequence to print message in red
     */
    public static final String RED = "[31m";

    /**
     * SGR sequence to print all following message in default color
     */
    public static final String RESET_COLOR_TO_DEFAULT = "[0m";

    /**
     * set the errors messages in red
     */
    public static final String ERROR_MESSAGE_COLOR = ESCAPE_SEQUENCE+RED;

    /**
     * reset all following message's color to default
     */
    public static final String RESET_COLOR = ESCAPE_SEQUENCE + RESET_COLOR_TO_DEFAULT;


    public static final String DIVIDER = "========================================================================";

    public static final String SPLASH_SCREEN = " __        ______   .______    _______ .__   __.  ________    ______\n" +
            "|  |      /  __  \\  |   _  \\  |   ____||  \\ |  | |       /   /  __  \\\n" +
            "|  |     |  |  |  | |  |_)  | |  |__   |   \\|  | `---/  /   |  |  |  |\n" +
            "|  |     |  |  |  | |      /  |   __|  |  . `  |    /  /    |  |  |  |\n" +
            "|  `----.|  `--'  | |  |\\  \\-.|  |____ |  |\\   |   /  /----.|  `--'  |\n" +
            "|_______| \\______/  | _| `.__||_______||__| \\__|  /________| \\______/\n" +
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
}
