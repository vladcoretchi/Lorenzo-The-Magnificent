package it.polimi.ingsw.LM34.UI;

public abstract class AbstractUI { //scrivere anche una classe che si occuperà di verificare se effettivamente l'input dell'utente è giusto

    public abstract void printSplashScreen();
    public abstract void printDivider();
    public abstract String startMenu();
    public abstract void loginMenu();
    public abstract String printMenu();
    public abstract String choiceConnectionType();
    public abstract void printTowers();
}