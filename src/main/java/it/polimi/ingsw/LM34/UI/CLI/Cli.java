package it.polimi.ingsw.LM34.ui;

import it.polimi.ingsw.LM34.UI.GeneralUI;
import it.polimi.ingsw.LM34.UI.AbstractUserInterface;
import org.json.*;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/**
 * Questa classe implementa {@link GeneralUserInterface} e gestisce l'interfaccia da linea di comando
 */
public class Cli extends GeneralUI {
    
    /**
     * Constants
     */
    
    private static final String PLAYER_NOT_FOUND = "player %s not found";
    
    private static final String SPLASH_SCREEN = " __        ______   .______    _______ .__   __.  ________    ______      __   __\n" +
    "|  |      /  __  \\  |   _  \\  |   ____||  \\ |  | |       /   /  __  \\    |  | |  |     \n" +
    "|  |     |  |  |  | |  |_)  | |  |__   |   \\|  | `---/  /   |  |  |  |   |  | |  |     \n" +
    "|  |     |  |  |  | |      /  |   __|  |  . `  |    /  /    |  |  |  |   |  | |  |     \n" +
    "|  `----.|  `--'  | |  |\\  \\-.|  |____ |  |\\   |   /  /----.|  `--'  |   |  | |  `----.\n" +
    "|_______| \\______/  | _| `.__||_______||__| \\__|  /________| \\______/    |__| |_______|\n" +
    "                                                                                          \n" +
    ".___  ___.      ___       _______ .__   __.  __   _______  __    ______   ______ \n" +
    "|   \\/   |     /   \\     /  _____||  \\ |  | |  | |   ____||  |  /      | /  __  \\\n" +
    "|  \\  /  |    /  ^  \\   |  |  __  |   \\|  | |  | |  |__   |  | |  ,----'|  |  |  |\n" +
    "|  |\\/|  |   /  /_\\  \\  |  | |_ | |  . `  | |  | |   __|  |  | |  |     |  |  |  |\n" +
    "|  |  |  |  /  _____  \\ |  |__| | |  |\\   | |  | |  |     |  | |  `----.|  `--'  |\n" +
    "|__|  |__| /__/     \\__\\ \\______| |__| \\__| |__| |__|     |__|  \\______| \\______/\n" +
    "                                                                                          ";
    
    private void printSplashScreen() {
        System.out.println(SPLASH_SCREEN);
    }

    @Override
    public void showNetworkSettingsForm() {
        
    }

    @Override
    public void showLoginMenu() {
        
        System.out.println("Hi, please insert your username and password to login ");

    }

    @Override
    public void notifyLoginErrorMessage() {
        printer.print("This nickname is not valid or already in use. Please try another one. "); //TODO: PRINT IN RED, LIKE ANY OTHERS ERROR MESSAGES
    }

    @Override
    public void notifygameStarted();

    @Override
    public void showPlayerInfo(String nickname);




}
