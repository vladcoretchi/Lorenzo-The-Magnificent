package it.polimi.ingsw.LM34.UI;

import java.util.HashMap;
import java.util.List;

/**
 * This abstract class represent the generalization of a user interface. Extending this class you will be able to create CLI or GUI
 **/

public abstract class GeneralUI {
    
    /**
     * Questo metodo verrà chiamato quando la ui dovrà mostrare all'utente il form in cui scegliera se connettersi utilizzando RMI o Socket
     */
    public void showNetworkSettingsForm();
    
    /**
     * Questo metodo verrà chiamato quando la ui dovrà mostrare all'utente il menù di login
     */
    public void showLoginMenu();
    
    /**
     * Questo metodo verrà chiamato quando la ui dovrà mostrare un errore relativo al fatto che il login non è andato a buon fine
     */
    public void notifyLoginErrorMessage();
    
    /**
     * Questo metodo verrà chiamato quando la ui dovrà notificare all'utente l'inizio della partita
     */
    public void notifyGameStarted();
    
    /**
     * Questo metodo verrà chiamato quando la ui dovrà notificare che è iniziato il turno di un giocatore
     * @param nickname del giocatore che sta iniziando il turno
     * @param remainingTime tempo rimanenente al giocatore per eseguire l'azione
     */
    public void notifyOtherPlayerTurnStarted(String nickname, int remainingTime);
    
    /**
     * Questo metodo verrà chiamato quando la ui dovrà notificare che l'utente intenderebbe compiere un'azione non consentita
     * @param errorCode codice di errore, relativo in base a che azione voleva compiere l'utente
     */
    public void notifyActionNotValid(int errorCode);
    
    /**
     * Questo metodo verrà chiamato quando la ui dovrà notificare che un giocatore si è disconnesso
     * @param nickname del giocatore che si è disconnesso
     */
    public void notifyPlayerDisconnected(String nickname);
    
    /**
     * Questo metodo verrà chiamato quando la ui dovrà notificare che la partita è terminata
     * @param ranking classifica finale dei giocatori
     */
    public void notifyGameEnded(List<String> ranking);
    
}
