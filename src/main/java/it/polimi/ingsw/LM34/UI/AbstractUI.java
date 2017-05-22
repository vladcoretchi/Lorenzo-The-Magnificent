package it.polimi.ingsw.LM34.UI;

/**
 * this abstract class represent all prototype of cli methods, and will be implemented in {@link TestCli}
 */
public abstract class AbstractUI {

    public abstract void printSplashScreen();
    public abstract void printDivider();
    public abstract String startMenu();
    public abstract void loginMenu();
    public abstract String printMenu();
    public abstract String choiceConnectionType();
    public abstract void printTowers();
}