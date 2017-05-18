package it.polimi.ingsw.LM34.UI;//package ...;

import java.util.HashMap;

public abstract class GenericUI { //scrivere anche una classe che si occuperà di verificare se effettivamente l'input dell'utente è giusto
    protected String menuChoice;
    protected String networkChoiced;
    protected HashMap Player;

    public abstract void printSplashScreen();
    public abstract String startMenu();
    public abstract HashMap login();
    public abstract void printMenu();
    public abstract String choiceConnectionType();
    public abstract void exit();

}