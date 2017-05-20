package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.UI.CLI.IOInterface;
import java.io.FileInputStream;
import java.util.HashMap;
import it.polimi.ingsw.LM34.UI.CLI.PlayerLoginInfo;
import java.io.InputStream;

public class Launcher extends TestCli implements IOInterface {

    public static void main(String[] args) {

        String conntectionType;

        TestCli CLI = new TestCli();

        CLI.printSplashScreen();
        CLI.printSeparator();

        while(true) {
            if(CLI.startMenu().equalsIgnoreCase("cli"))
                break;
            else if(CLI.startMenu().equalsIgnoreCase(("gui")))
                System.err.println("this feature may be not available for non premium user \n"); //anche i .err sono da ridefinire in IOInterface
            else
                System.err.println("please choose cli or gui");
        }

        while(true) {
            CLI.loginMenu();
            if(PlayerLoginInfo.PLAYER_LOGIN_INFO.get("username").equals("giulio"))
                break;
            else
                System.err.println("please login as giulio \n");
        }

        while(true) {
            conntectionType = CLI.choiceConnectionType();

            if(conntectionType.equalsIgnoreCase("cli") || conntectionType.equalsIgnoreCase("rmi"))
                break;
            else
                System.err.println("please choose rmi or socket");
        }

        CLI.printSeparator();

        CLI.printTowers();

        //testare junit con le classi che gia ci sono


        //probabilmente anche i comandi andranno importati in modo statico

        //creare un controller che, una volta creata la partita, si interfacci col model.

        //immaginiamo di essere gia in partita

        //essendo in partita, devo interfacciarmi col model, deserializzandolo

        // GameController GameSituationController = new GameController();
        //verificare se è meglio gestire tutto direttamente nel launcher o la parte relativa alla partita relegarla al controller

        //immaginando di essere in partita, in un contesto dove l'utente decide di incrementare il
        //valore dei dadi usando i servitori

        //se il giocatore ha detto di si, quindi ha compiuto l'azione, allora il turno termina

        //testare una partita con dati fittizzi, in modo da verificare come verranno stampati su console
        //fare il .jar, in modo da generare la console
        //provare a vedere se si riesce a stampare le torri, e se in qualche modo si riesce a avere una dimensione
        //minima della console

    }
}

