package it.polimi.ingsw.LM34.UI;

import it.polimi.ingsw.LM34.Enums.CLI.NetworkType;
import it.polimi.ingsw.LM34.Exceptions.CLI.PlayerException;
import it.polimi.ingsw.LM34.UI.TestCli;

public class UIErrorHandler extends TestCli {

    PlayerException wrongUsernameException = new PlayerException("Errore: ");
    PlayerException wrongNetworkTecnologyException = new PlayerException("Errore: ");

    TestCli testCli = new TestCli();

    public void testLogin() throws PlayerException {

        Boolean typedCorrectUsername;

        typedCorrectUsername = testCli.login().equals("pippo");

        if(!typedCorrectUsername)
            throw wrongUsernameException;

        System.out.println("tutto apposto");

    }

    public void testChoicedNetworkType() throws PlayerException {
        Boolean choicedValidNetworkTechnology;
        String networkChoiced = choiceConnectionType();

        for(NetworkType tn : NetworkType.values()) {
            System.out.println("tn: " + tn + "\n" + "scelta: " + networkChoiced + "\n");
            if(tn.toString().equalsIgnoreCase(networkChoiced)) {
                System.out.println("network corretto");
                return;
            }
        }

        throw wrongNetworkTecnologyException;
    }

    public static void main(String[] args) {

        UIErrorHandler testUIErrorHandler = new UIErrorHandler();

        try {

            testUIErrorHandler.testLogin();

        }
        catch(PlayerException wrongUsernameException) {
            System.out.println(wrongUsernameException.getErrorMessage() + "username errato, si prega di riprovare ");
        }

        try {
            testUIErrorHandler.testChoicedNetworkType();
        }
        catch(PlayerException wrongNetworkTechnologyException) {
            System.out.println(wrongNetworkTechnologyException.getErrorMessage() + "tecnologia per connettersi al network errata, si prega di riprovare ");
        }
    }
}