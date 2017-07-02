package it.polimi.ingsw.LM34.UI.CLI;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Collections of escaping characters for printing in colors, ad hoc methods and... LM34 "Lorenzo il Magnifico" splash screen
 */
public final class CLIStuff {
    public static final IgnoreInput ignoreInput = new IgnoreInput();
    public static final String INCORRECT_INPUT = "Incorrect input";
    public static final Scanner readUserInput = new Scanner(System.in);
    public static final PrintWriter printToConsole = new PrintWriter(System.out, true);
    private static final String INVALID_PAWN_COLOR = "Invalid pawn color";
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
     * SGR sequences to print message in colors
     */
    private static final String GREEN = "[32m";
    private static final String YELLOW = "[33m";
    private static final String BLUE = "[36m";
    private static final String PURPLE = "[35m";
    private static final String DARK_BLUE = "[34m";

    private static final String PAWN_GREEN = "[42m";
    private static final String PAWN_YELLOW = "[43m";
    private static final String PAWN_BLUE = "[44m";
    private static final String PAWN_RED = "[41m";
    /**
     * SGR sequence to print all following message in default color
     */
    private static final String RESET_COLOR_TO_DEFAULT = "[0m";

    /**
     * set the errors messages in red
     */
    private static final String ERROR_MESSAGE_COLOR = ESCAPE_SEQUENCE + RED;

    /**
     * Color the tower name based on the development card color associated
     */
    private static final String GREEN_MESSAGE_COLOR = ESCAPE_SEQUENCE + GREEN;
    private static final String YELLOW_MESSAGE_COLOR = ESCAPE_SEQUENCE + YELLOW;
    private static final String BLUE_MESSAGE_COLOR = ESCAPE_SEQUENCE + BLUE;
    private static final String PURPLE_MESSAGE_COLOR = ESCAPE_SEQUENCE + PURPLE;
    private static final String DICE_VALUE_MESSAGE_COLOR = ESCAPE_SEQUENCE + DARK_BLUE;
    private static final String PAWN_GREEN_COLOR = ESCAPE_SEQUENCE + PAWN_GREEN;
    private static final String PAWN_BLUE_COLOR = ESCAPE_SEQUENCE + PAWN_BLUE;
    private static final String PAWN_YELLOW_COLOR = ESCAPE_SEQUENCE + PAWN_YELLOW;
    private static final String PAWN_RED_COLOR = ESCAPE_SEQUENCE + PAWN_RED;
    private static final String RED_MESSAGE_COLOR = ESCAPE_SEQUENCE + RED;

    /**
     * reset all following message's color to default
     */
    private static final String RESET_COLOR = ESCAPE_SEQUENCE + RESET_COLOR_TO_DEFAULT;

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

    private CLIStuff() {}


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

    public static final void printGreen(String message) {
        printToConsole.print(CLIStuff.GREEN_MESSAGE_COLOR + message + CLIStuff.RESET_COLOR);
    }

    public static final void printRed(String message) {
        printToConsole.print(CLIStuff.RED_MESSAGE_COLOR + message + CLIStuff.RESET_COLOR);
    }

    public static final void printYellow(String message) {
        printToConsole.print(YELLOW_MESSAGE_COLOR + message + RESET_COLOR);
    }

    public static final void printBlue(String message) {
        printToConsole.print(BLUE_MESSAGE_COLOR + message + RESET_COLOR);
    }

    private static final void printPurple(String message) {
        printToConsole.print(PURPLE_MESSAGE_COLOR + message + RESET_COLOR);
    }

    public static final void printTowerTypeColor(DevelopmentCardColor color) {
        switch(color) {
            case GREEN:
                printGreen(DevelopmentCardColor.GREEN.getDevType()+"\n");
                break;
            case BLUE:
                printBlue(DevelopmentCardColor.BLUE.getDevType()+"\n");
                break;
            case YELLOW:
                printYellow(DevelopmentCardColor.YELLOW.getDevType()+"\n");
                break;
            case PURPLE:
                printPurple(DevelopmentCardColor.PURPLE.getDevType()+"\n");
                break;
            default:
                    printError("Invalid development card color");
        }
    }

    public static final void printDiceValue(String message) {
        printToConsole.print("DiceValue " + CLIStuff.DICE_VALUE_MESSAGE_COLOR + message + " " + CLIStuff.RESET_COLOR);
    }

    public static final void printPawn(PawnColor pawnColor, DiceColor diceColor) {
        switch(pawnColor) {
            case GREEN:
                printToConsole.print(CLIStuff.PAWN_GREEN_COLOR + " "  + diceColor.toString() + " " + CLIStuff.RESET_COLOR + "   ");
                break;
            case RED:
                printToConsole.print(CLIStuff.PAWN_RED_COLOR + " " + diceColor.toString() + " " + CLIStuff.RESET_COLOR + "   ");
                break;
            case YELLOW:
                printToConsole.print(CLIStuff.PAWN_YELLOW_COLOR + " " + diceColor.toString() + " " + CLIStuff.RESET_COLOR + "   ");
                break;
            case BLUE:
                printToConsole.print(CLIStuff.PAWN_BLUE_COLOR + " " + diceColor.toString() + " "  + CLIStuff.RESET_COLOR + "   ");
                break;
            default:
                    printError("Invalid pawn color");
        }
    }

    public static final void printPlayer(PawnColor pawnColor, String name) {
        switch(pawnColor) {
            case GREEN:
                printToConsole.print(CLIStuff.PAWN_GREEN_COLOR + " "  + name + " " + CLIStuff.RESET_COLOR + "   ");
                break;
            case RED:
                printToConsole.print(CLIStuff.PAWN_RED_COLOR + " " + name + " " + CLIStuff.RESET_COLOR + "   ");
                break;
            case YELLOW:
                printToConsole.print(CLIStuff.PAWN_YELLOW_COLOR + " " + name + " " + CLIStuff.RESET_COLOR + "   ");
                break;
            case BLUE:
                printToConsole.print(CLIStuff.PAWN_BLUE_COLOR + " " + name + " "  + CLIStuff.RESET_COLOR + "   ");
                break;
            default:
                printError(INVALID_PAWN_COLOR);
        }
    }

    public static final void printPlayerName(String phrase, PawnColor color) {
        switch (color) {
            case GREEN:
                printGreen(phrase);
                break;
            case BLUE:
                printBlue(phrase);
                break;
            case YELLOW:
                printYellow(phrase);
                break;
            case RED:
                printRed(phrase);
                break;
            default:
                printError(INVALID_PAWN_COLOR);
        }
    }
}
