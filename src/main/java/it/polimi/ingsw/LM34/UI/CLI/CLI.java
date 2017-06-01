package it.polimi.ingsw.LM34.UI.CLI;

import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectionableContexts;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.UI.AbstractUI;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class was built on {@link AbstractUI}. It implement all method body that will be used to describe and manage Cli
 */

public class CLI extends AbstractUI {

    //TODO: implementare il thread che svuota il buffer ogni volta che l`utente ha inserito qualcosa

    public void show() {
        // variable that will remain false until the user's input will be correct
        Boolean userInputIsValid = false;

        // this cycle will call user to choose between Rmi or Socket, and repeat this question until the user's answer will be 'rmi' or 'socket', ignoring uppercase
        while(!userInputIsValid) {
            String connectionTypeChoice = connectionTypeSelection();
            if(connectionTypeChoice.equalsIgnoreCase("rmi") || connectionTypeChoice.equals("1")) {
                this.networkClient = new RMIClient(SERVER_IP, RMI_PORT, this);
                userInputIsValid = true;
            }
            else if (connectionTypeChoice.equalsIgnoreCase("socket") || connectionTypeChoice.equals("2")) {
                this.networkClient = new SocketClient(SERVER_IP, SOCKET_PORT, this);
                userInputIsValid = true;
            }
            else
                CLIStuff.printToConsole.println(CLIStuff.ERROR_MESSAGE_COLOR + "please choose rmi or socket" + CLIStuff.RESET_COLOR);
        }

        this.networkController = networkClient.getNetworkController();
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

        networkController.login(playerUsername, playerPassword);
    }

    /**
     * Receives the login operation result
     * @param result login result
     */
    public void loginResult(Boolean result) {
        if (result) {
            CLIStuff.printToConsole.println("Access granted!");
        } else {
            CLIStuff.printToConsole.println("Access denied! Wrong username or password.");
            loginMenu();
        }
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
    public Integer contextSelection(List<PlayerSelectionableContexts> allContext)  {

        String userContextSelection;

        Integer userSelection = 0;

        allContext.forEach((context) -> context.toString().replace("_CONTEXT", "").replace("_", " "));

        allContext.forEach((context)-> CLIStuff.printToConsole.println(context));

        Boolean isANumber = false;

        do {

            try {
                CLIStuff.printToConsole.println("in which context do you wish to enter? \n"+
                        "type 1 to enter into the first context, type 2 to enter into the second context, ...\n" +
                        "type 0 to exit");

                userContextSelection = CLIStuff.readUserInput.nextLine();
                userSelection = Integer.parseInt(userContextSelection);

                if(userSelection >= 0 && userSelection <= allContext.size())
                    isANumber = true;

            } catch (NumberFormatException ex) {
                CLIStuff.printToConsole.println("please select a valid context ");
            }
        }
        while(!isANumber);

        return --userSelection;

    }

    /**
     * this method will be called when player will want to play or discard some leader cards
     * @param playerLeaderCards the leader cards that player actually have in his hand
     * @param action the action the player will be. It can be discard (in case player will want to discard some leader cards) or play (in case player will want to play some leader cards)
     * @return a list that contains which leader cards player have chosen to discard/play
     */
    @Override
    public ArrayList<String> playerLeaderCardsAction(List<String> playerLeaderCards, String action) {

        String cardsInfluencedByAction;
        Integer numberOfInfluencedCards;
        ArrayList<String> listOfInfluencedCards = new ArrayList<>();

        //print all player`s leader cards
        playerLeaderCards.stream().forEach((leaderCard) -> {CLIStuff.printToConsole.println(playerLeaderCards);});

        CLIStuff.printToConsole.format("which leader do you wish to %s? %n" +
                "to select, please insert card's number, eventually separated by comma in case of multiple choice", action);
        cardsInfluencedByAction = CLIStuff.readUserInput.nextLine();


        Pattern pattern = Pattern.compile("[0-9]+");

        Matcher matcher = pattern.matcher(cardsInfluencedByAction);

        while(matcher.find()){

            numberOfInfluencedCards = Integer.parseInt(matcher.group());

            listOfInfluencedCards.add(playerLeaderCards.get(numberOfInfluencedCards));
        }

        return listOfInfluencedCards;

    }

    /**
     * this method will be called when player will want to enter into market
     * @param market all market`s slot information
     */
    @Override
    public HashMap<Integer, Integer> market(Market market, Player player) {

        Integer playerChosenFamilyMember;
        Integer playerChosenFamilyMemberValue;
        Integer playerChosenSlot;

        HashMap<Integer, Integer> playerChosenSlotAndPawn = new HashMap<>();

        CLIStuff.printToConsole.println("welcome to the market "); //if not free, market will printed in red

        ArrayList<ActionSlot> marketSlots = market.getMarketSlots();

        for (ActionSlot slot : marketSlots) {
            CLIStuff.printToConsole.format("_________ ");
        }

        CLIStuff.printToConsole.format("%n");

        for (ActionSlot slot : marketSlots) {
            CLIStuff.printToConsole.format("|         |");
        }

        CLIStuff.printToConsole.format("%n");

        for (ActionSlot slot : marketSlots) {

            Set<ResourceType> key = slot.getResourcesReward().getResources().getResources().keySet();

            Integer value = slot.getResourcesReward().getResources().getResources().get(key);

            CLIStuff.printToConsole.format("|   %d     |", value);
        }

        CLIStuff.printToConsole.println("%n");

        for (ActionSlot slot : marketSlots) {

            Set<ResourceType> key = slot.getResourcesReward().getResources().getResources().keySet();

            CLIStuff.printToConsole.format("|  %s  |", key.toString());

        }

        CLIStuff.printToConsole.println("\n");

        for (ActionSlot slot : marketSlots) {

            CLIStuff.printToConsole.println("_________ ");
        }

        CLIStuff.printToConsole.println("which family member do you wish to put into market? ");
        playerChosenFamilyMember = CLIStuff.readUserInput.nextInt();

        playerChosenFamilyMemberValue = player.getFamilyMembers().get(playerChosenFamilyMember).getValue();

        if(playerChosenFamilyMemberValue < 1)
            servantsSelection(player.getResources().getResourceByType(ResourceType.SERVANTS), 1);

        CLIStuff.printToConsole.println("in which action slot do you wish to put this family member? ");
        playerChosenSlot = CLIStuff.readUserInput.nextInt();

        //check if action slot selected is available

        playerChosenSlotAndPawn.put(playerChosenFamilyMember, playerChosenSlot);

        return playerChosenSlotAndPawn;


        /*CLIStuff.printToConsole.println(
                 "_________ __________ _____4____ _____4____\n" +
                "|         |          |          |          |\n" +
                "|   5     |    5     |  3 M.P   |   1 C.P  |\n" +
                "|  coins  | servants |  2 coins |   !=     |\n" +
                "|         |          |          |   1 C.P  |\n" +
                "|____1____|_____1____|_____1____|_____1____|"
        );*/
    }

    @Override
    public void workingArea(String workingAreaChoice, Player player) {

        Integer selectedSlot;
        Integer familyMemberValue;
        Integer servantsAvailable  = player.getResources().getResourceByType(ResourceType.SERVANTS);;
        Integer selectedFamilyMember;
        Integer tempDiceValue;
        Boolean useSomeServants;
        Integer usedServants = 0;
        String userChoice = (workingAreaChoice.equalsIgnoreCase("production")) ? "produce" : "harvest";

        CLIStuff.printToConsole.format("in which slot do you want to %s?", userChoice);
        selectedSlot = CLIStuff.readUserInput.nextInt();

        CLIStuff.printToConsole.println("which family member do you wish to use? ");
        selectedFamilyMember = CLIStuff.readUserInput.nextInt();

        familyMemberValue = player.getFamilyMembers().get(selectedFamilyMember).getValue();

        tempDiceValue = familyMemberValue;

        CLIStuff.printToConsole.format("actually, you can %s for %d. Do you wish to use some servants to increase this value? ",userChoice ,familyMemberValue);
        useSomeServants = (CLIStuff.readUserInput.nextLine().equalsIgnoreCase("yes")) ? true : false;

        if(useSomeServants) {

           do {
               CLIStuff.printToConsole.println("how many servants do you wish to use? ");
               usedServants = servantsSelection(servantsAvailable, CLIStuff.readUserInput.nextInt());
           }
           while(servantsAvailable < usedServants);
        }

        tempDiceValue += usedServants;

        //print harvest or production area, depending of user`s choice

    }

    /**
     * this method will be called when player will enter into Council Palace
     * @param player all player`s information
     * @return which bonus the player has chosen
     */
    @Override
    public Integer councilPalace(Player player) {

        Integer selectedFamilyMember;
        Integer chosenFamilyMemberValue;
        Integer servantsAvailable = player.getResources().getResourceByType(ResourceType.SERVANTS);
        Integer usedServants;
        Integer selectedCouncilPrivilegeBonus = 0;

            CLIStuff.printToConsole.println("which family member do you wish to use? ");
            selectedFamilyMember = CLIStuff.readUserInput.nextInt();

            chosenFamilyMemberValue = player.getFamilyMembers().get(selectedFamilyMember).getValue();

            if(chosenFamilyMemberValue < 1) {

                usedServants = servantsSelection(servantsAvailable, 1);

            }

        //print councilPalace

            do {
                CLIStuff.printToConsole.println("which CouncilPrivilege bonus do you wish to take? ");
                selectedCouncilPrivilegeBonus = CLIStuff.readUserInput.nextInt();
            }
            while(selectedCouncilPrivilegeBonus < 1 || selectedCouncilPrivilegeBonus > 5);

        return selectedCouncilPrivilegeBonus;

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

        CLIStuff.printToConsole.println("in which tower do you wish to bring your family member? ");
        tower = CLIStuff.readUserInput.nextInt();

        CLIStuff.printToConsole.println("in which tower's floor do you wish to put your family member? ");
        floor = CLIStuff.readUserInput.nextInt();

         // decrement tower and floor because user's choice is between 1 and 4, but server's range is between 0 and 3

        tower--; floor--;

        towerAndItsFloor = tower.toString() + ":" + floor.toString();

        return towerAndItsFloor;

    }

    /**
     * this method will be called when user need to use some servants to improve his pawn's value
     * @param servantsAvailable user's available servants
     * @param minimumServantsRequested minimum number of servants to complete action
     * @return how many servants user has decided to use
     */
    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {

        Integer usedServants;

        do {

            CLIStuff.printToConsole.println("to complete this action, you need at least " + minimumServantsRequested.toString() + "servants, and max " + servantsAvailable.toString() + " servants ");
            CLIStuff.printToConsole.println("how many servants do you want to use? ");
            usedServants = CLIStuff.readUserInput.nextInt();
        }
        while(((usedServants + servantsAvailable) < minimumServantsRequested) || usedServants > servantsAvailable);

        return usedServants;

    }

    @Override
    public void printTowers(ArrayList<Tower> towers) {

        String cardName = "support to the pope";
        Integer valueOfInstantResourceBonus = 2; //to be determine according to tower`s floor
        String typeOfIntegerResourceBonus = "military points";
        Integer diceValueRequested = 7;

        for(Tower tower : towers) {

            CLIStuff.printToConsole.format("___________ ...........   ___________ ...........   ____________ ...........   ___________ ...........%n");
            CLIStuff.printToConsole.format("|           |    %d     .  |           |    %d     .  |            |    %d     .  |           |     %d    . %n",valueOfInstantResourceBonus ,valueOfInstantResourceBonus ,valueOfInstantResourceBonus ,valueOfInstantResourceBonus);
            CLIStuff.printToConsole.format("| %s    |  %s   .  | %s|  %s  .  | %s   | %s .  | %s |   %s  .%n",cardName ,typeOfIntegerResourceBonus ,cardName ,typeOfIntegerResourceBonus ,cardName ,typeOfIntegerResourceBonus ,cardName ,typeOfIntegerResourceBonus);
            CLIStuff.printToConsole.format("|           |...........  |           |...........  |            |...........  |           |...........%n");
            CLIStuff.printToConsole.format("|___________|    %d        |___________|    %d        |____________|    %d        |___________|     %d ",diceValueRequested ,diceValueRequested ,diceValueRequested ,diceValueRequested);
        }
    }

    /**
     * this method will be called when the console will print gameBoard on screen
     */
    @Override
    public void printGameBoard() {

        CLIStuff.printToConsole.println(" ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______ ______\n" +
                "|      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |      |\n" +
                "|  0   |  1   |  2   |  3   |  4   |  5   |  6   |  7   |  8   |  9   |  10  |  11  |  12  |  13  |  14  |  15  |  16  |  17  |  18  |  19  | 20   |\n" +
                "|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|______|\n" +
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