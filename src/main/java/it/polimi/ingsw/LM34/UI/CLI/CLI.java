package it.polimi.ingsw.LM34.UI.CLI;

import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.UI.AbstractUI;

import java.lang.String;
import java.util.List;

/**
 * this class was built on {@link AbstractUI}. It implement all method body that will be used to describe and manage Cli
 */

public class CLI extends AbstractUI {

    public void show() {
        // variable that will remain false until the user's input will be correct
        Boolean userInputIsValid = false;

        // this cycle will call user to choose between Rmi or Socket, and repeat this question until the user's answer will be 'rmi' or 'socket', ignoring uppercase
        while(!userInputIsValid) {
            String connectionTypeChoice = connectionTypeSelection();
            if(connectionTypeChoice.equalsIgnoreCase("rmi") || connectionTypeChoice.equals("1")) {
                networkClient = new RMIClient(SERVER_IP, RMI_PORT);
                userInputIsValid = true;
            }
            else if (connectionTypeChoice.equalsIgnoreCase("socket") || connectionTypeChoice.equals("2")) {
                networkClient = new SocketClient(SERVER_IP, SOCKET_PORT);
                userInputIsValid = true;
            }
            else
                CLIStuff.printToConsole.println(CLIStuff.ERROR_MESSAGE_COLOR + "please choose rmi or socket" + CLIStuff.RESET_COLOR);
        }

        loginMenu();
    }

    /**
     * this method will be called when the console will print on screen the Splash screen, at the beginning of it's lifecycle
     */
    @Override
    public void showSplashScreen() {
        CLIStuff.printToConsole.println(CLIStuff.SPLASH_SCREEN);
    }

    /**
     * this method will be called when the console need to separate some information to others
     */
    @Override
    public void printDivider() {
        CLIStuff.printToConsole.println(CLIStuff.DIVIDER);
    }

    /**
     * this method will be called when the console will ask to user to insert his username and password
     */
    @Override
    public void loginMenu() {
        CLIStuff.printToConsole.println("Please insert your username:");
        String playerUsername = CLIStuff.readUserInput.nextLine();

        CLIStuff.printToConsole.println("please insert your password:");
        String playerPassword = CLIStuff.readUserInput.nextLine();

        //TODO: login on server
    }

    /**
     * this method will be called when the console will ask user what kind of connection technology he wants to use, Rmi or Socket
     * @return the user's choice
     */
    @Override
    public String connectionTypeSelection() {
        CLIStuff.printToConsole.println("which technology do you wish to use to connect to the server?\n" +
                "1) RMI\n" +
                "2) Socket"
        );

        return CLIStuff.readUserInput.nextLine();
    }

    /**
     * this method will be called when, in game, user type "help", followed by a request.
     * if the request will be card's name, the helper will display all information about this card
     * if the request will be another user's name, the helper will display all information about that user
     */
    public String helper() {
        CLIStuff.printToConsole.println("type help + 'card name' to obtain all information about the searched card\n" +
                "type help + 'player name' to obtain all information about the searched player\n"+
                "currently, in-game are present x player, which names are ...\n");

        return CLIStuff.readUserInput.nextLine();
    }

    /**
     * this method will be called when the user will choice about which context player wish to use
     */
    @Override
    public Integer contextSelection(List<String> allContext)  {

        Integer userContextSelection;
        CLIStuff.printToConsole.println("in which context do you wish to enter? \n");
        allContext.forEach((context)->CLIStuff.printToConsole.println(context));
        userContextSelection = CLIStuff.readUserInput.nextInt();

        return userContextSelection;

        //TODO: return to server the user's choiced context
    }

    /**
     * this method will be called when the user will choice which tower wants to make his action and in which tower's floor
     * @param towerNumber how many towers contains a game board
     * @param towerFloor how many floors contains a tower
     */
    @Override
    public String towerSlotSelection(Integer towerNumber, Integer towerFloor) {

        Integer tower, floor;
        String towerAndItsFloor;

        CLIStuff.printToConsole.println("in which tower do you wish to bring your pawn? ");
        tower = CLIStuff.readUserInput.nextInt();

        CLIStuff.printToConsole.println("in which tower's floor do you wish to put your pawn? ");
        floor = CLIStuff.readUserInput.nextInt();

        /**
         * decrement tower and floor because user's choice is between 1 and 4, but server's range is between 0 and 3
         */
        tower--; floor--;

        towerAndItsFloor = tower.toString() + ":" + floor.toString();

        return towerAndItsFloor;

    }

    /**
     * this method will be called when user need to use some servants to improve his pawn's value
     * @param servantsAvailable user's available servants
     * @param minimumServantsRequested minimum number of servants to complete action
     * @return which servants user decide to use
     */
    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {

        Integer usedServants;

        CLIStuff.printToConsole.println("to complete this action, you need at least " + minimumServantsRequested.toString() + "servants, and max " + servantsAvailable.toString() + " servants ");
        CLIStuff.printToConsole.println("how many servants do you want to use? ");
        usedServants = CLIStuff.readUserInput.nextInt();

        return usedServants;

    }

    /**
     * this method will be called when the console will print gameBoard on screen
     */
    @Override
    public void printGameBoard() {

        CLIStuff.printToConsole.println(" ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______\n" +
                "|      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |\n" +
                "|  0   |  1   |  2   |  3   |  4   |  5   |  6   |  7   |  8   |  9   |  10  |  11  |  12  |  13  |  14  |  15  |  16  |  17  |  18  |  19  | 20   |                                                                                                                                      \n" +
                "|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|                                                                                                                         \n" +
                "|      |                                                                                                                                    |      |\n" +
                "| 99   |    ___________ ...........   ___________ ...........   ____________ ...........   ___________ ...........      1°—>5 2°—>2         | 21   |\n" +
                "|______|   |           |    2     .  |           |    2     .  |            |    2     .  |           |     2    .      __________          |______|\n" +
                "|      |   | marble    |  woods   .  | ambassador|  stones  .  | military   | military .  | support to|   coins  .     |    25    |         |      |\n" +
                "| 98   |   | pit       |          .  |           |          .  | academy    | points   .  | the pope  |          .     |__________|         | 22   |\n" +
                "|______|   |           |...........  |           |...........  |            |...........  |           |...........     |    24    |         |______|\n" +
                "|      |   |___________|    7        |___________|    7        |____________|    7        |___________|     7          |__________|         |      |\n" +
                "| 97   |    ___________ ...........   ___________ ...........   ____________ ...........   ___________ ...........     |    23    |         | 23   |\n" +
                "|______|   |           |    1     .  |           |    1     .  |            |    1     .  |           |     1    .     |__________|         |______|\n" +
                "|      |   | manor     |  woods   .  | paramour  |  stones  .  | stronghold | military .  | improving |   coins  .     |    22    |         |      |\n" +
                "| 96   |   | house     |          .  |           |          .  |            | points   .  | the roads |          .     |__________|         | 24   |\n" +
                "|______|   |           |...........  |           |...........  |            |...........  |           |...........     |    21    |         |______|\n" +
                "|      |   |___________|    5        |___________|    5        |____________|    5        |___________|     5          |__________|         |      |\n" +
                "| 95   |                                                                                                               |    20    |         | 25   |\n" +
                "|______|    ___________ ...........   ___________ ...........   ____________ ...........   ___________ ...........     |__________|         |______|\n" +
                "|      |   |           |          .  |           |          .  |            |          .  |           |          .     |    19    |         |      |\n" +
                "| 94   |   | fortified |          .  | governor  |          .  |stonemason’s|          .  | repairing |          .     |__________|         | 26   |\n" +
                "|______|   | town      |          .  |           |          .  | guild      |          .  | the       |          .     |    18    | 6       |______|\n" +
                "|      |   |           |...........  |           |...........  |            |...........  | cathedral |...........     |__________|         |      |\n" +
                "| 93   |   |___________|    3        |___________|    3        |____________|    3        |___________|     3          |    17    |         | 27   |\n" +
                "|______|                                                                                                               |__________|         |______|\n" +
                "|      |    ___________ ...........   ___________ ...........   ___________ ...........    ___________ ...........     |    16    |         |      |\n" +
                "| 92   |   |           |          .  |           |          .  |           |          .   |           |          .     |__________|         | 28   |\n" +
                "|______|   | trading   |          .  | royal     |          .  | sculptor’s|          .   | support to|          .     |    15    |         |______|\n" +
                "|      |   | town      |          .  | messenger |          .  | guild     |          .   | the       |          .     |__________|         |      |\n" +
                "| 91   |   |           |...........  |           |...........  |           |...........   | cardinal  |...........     |    14    |         | 29   |\n" +
                "|______|   |___________|    1        |___________|    1        |___________|     1        |___________|     1          |__________|         |______|\n" +
                "|      |                                                                                                               |    13    |         |      |\n" +
                "| 90   |                                                                                                               |__________|         | 30   |\n" +
                "|______|                                                       ___________________________________       1° ___        |    12    | 5       |______|\n" +
                "|      |                                                      |             1°=>2°=>3°=>4°=>=>=>  |        |   |       |__________|         |      |\n" +
                "| 89   |                                                      |                                   |        |   |       |    11    |         | 31   |\n" +
                "|______|                                                      |  1C.P                             |        |___|       |__________|         |______|\n" +
                "|      |                                                      |     1 coin                        |                    |    10    |         |      |\n" +
                "| 88   |                                                      |                                   |      2° ___        |__________|         | 32   |\n" +
                "|______|                                                      |                                   |        |   |       |     9    |         |______|\n" +
                "|      |                                                      |_________________1_________________|        |   |       |__________|         |      |\n" +
                "| 87   |                                                                                                   |___|       |     8    |         | 33   |\n" +
                "|______|                                                                                                               |__________|         |______|\n" +
                "|      |                                                       ____________________________________      3° ___        |     7    | 4       |      |\n" +
                "| 86   |                                                      |                                    |       |   |       |__________|         | 34   |\n" +
                "|______|                                                      | 1C.P => 1W+1S/2SV/2CS/2MP/1FP      |       |   |       |     6    |         |______|\n" +
                "|      |                                                      |____________________________________|       |___|       |__________|         |      |\n" +
                "| 85   |                                                                                                               |     5    |         | 35   |\n" +
                "|______|                                                                                                 4° ___        |__________|         |______|\n" +
                "|      |                                                                                                   |   |       |     4    |         |      |\n" +
                "| 84   |                                                                                                   |   |       |__________|         | 36   |\n" +
                "|______|     |                                                                                             |___|       |     3    | 3       |______|\n" +
                "|      |   __|__                                                                                                       |__________|         |      |\n" +
                "| 83   |     |                                                                                                         |     2    |         | 37   |\n" +
                "|______|     |                                                                                                         |__________|         |______|\n" +
                "|      |                                                                                                               |     1    |         |      |\n" +
                "| 82   |    0    1    2    3   4    5    7    9    11   13   15   17   19   22   25   30                               |__________|         | 38   |\n" +
                "|______|  ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____ ____                              |     0    | 2       |______|\n" +
                "|      | |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |    |                             |__________|         |      |\n" +
                "| 81   | | 0  | 1  | 2  | 3  | 4  | 5  | 6  | 7  | 8  | 9  | 10 | 11 | 12 | 13 | 14 | 15 |                                                  | 39   |\n" +
                "|______| |____|____|____|____|____|____|____|____|____|____|____|____|____|____|____|____|                                                  |______|\n" +
                "|      |                                                                                                                                    |      |\n" +
                "| 80   |                                                                                                                                    | 40   |\n" +
                "|______|                                                                                                                                    |______|\n" +
                "|      |                                                                                                                                    |      |\n" +
                "| 79   |                                                                                                                                    | 41   |\n" +
                "|______|                                                                                                                                    |______|\n" +
                "|      |                                                                                                                                    |      |\n" +
                "| 78   |                                                                                                                                    | 42   |\n" +
                "|______|                                                                                                                                    |______|\n" +
                "|      |   ____________   _________3+__________                                 _________ __________ _____4____ _____4____                  |      |\n" +
                "| 77   |  |            | |                     |                               |         |          |          |          |                 | 43   |\n" +
                "|______|  | production | | production          |                               |    5    |    5     |  3 M.P   |   1 C.P  |                 |______|\n" +
                "|      |  |            | |   -3 to dice value  |                               |  coins  | servants |  2 coins |    !=    |                 |      |\n" +
                "| 76   |  |______1_____| |__________1__________|                               |         |          |          |   1 C.P  |                 | 44   |\n" +
                "|______|                                                                       |____1____|_____1____|_____1____|_____1____|                 |______|\n" +
                "|      |                                                                                                                                    |      |\n" +
                "| 75   |                                                                                                                                    | 45   |\n" +
                "|______|                                                                                                                                    |______|\n" +
                "|      |   ____________   _________3+__________                                                                                             |      |\n" +
                "| 74   |  |            | |                     |                                __________ __________ __________                            | 46   |\n" +
                "|______|  | harvest    | | harvest             |                               |          |          |          |                           |______|\n" +
                "|      |  |            | |  -3 to dice value   |                               |  black   |  white   |  orange  |                           |      |\n" +
                "| 73   |  |_____1______| |__________1__________|                               |  pawn    |  pawn    |  pawn    |                           | 47   |\n" +
                "|______|                                                                       |          |          |          |                           |______|\n" +
                "|      |                                                                       |__________|__________|__________|                           |      |\n" +
                "| 72   |                                          1 Servant = +1 dice value                                                                 | 48   |\n" +
                "|______|                                                                                                                                    |______|\n" +
                "|      |                                                                                                                                    |      |\n" +
                "| 71   |                                                                                                                                    | 49   |\n" +
                "|______|______ _______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ _____|______|\n" +
                "|      |      |       |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |     |      |\n" +
                "| 70   | 69   | 68    | 67   | 66   | 65   | 64   | 63   | 62   |  61  | 60   | 59   | 58   | 57   | 56   | 55   | 54   | 53   | 52   | 51  | 50   |\n" +
                "|______|______|_______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|_____|______|\n" +
                "\n");

        //TODO: manage pawns into tower's floor

        //TODO: print towers color-coded
        //TODO: rename CliStuff, and move gameBoard into constant

    }

}