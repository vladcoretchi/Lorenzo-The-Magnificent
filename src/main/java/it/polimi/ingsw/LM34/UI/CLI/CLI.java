package it.polimi.ingsw.LM34.UI.CLI;

import java.util.Collections;
import static it.polimi.ingsw.LM34.UI.CLI.CLIStuff.*;
import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Tower;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.UI.AbstractUI;
import it.polimi.ingsw.LM34.Utils.Utilities;
import it.polimi.ingsw.LM34.Utils.Validator;
import java.util.*;

/**
 * this class was built on {@link AbstractUI}. It implement all method body that will be used to describe and manage Cli
 */

public class CLI extends AbstractUI {

    private Integer selectionMenu(List<?> data, Optional<String> backString, Optional<String> message, Optional<String> errorMessage) {
        Integer userSelection;

        backString.ifPresent((str) -> printFormat("%1$d) %2$s\n", 0, str));
        for (int i = 0; i < data.size(); i++) {
            printFormat("%1$d) %2$s\n", i+1, data.get(i).toString());
        }

        message.ifPresent((str) -> printLine(str));
        String userSelectionString = readUserInput.nextLine();
        try {
            userSelection = Validator.checkValidity(userSelectionString, data);
        }
        catch (IncorrectInputException ex) {
            errorMessage.ifPresent((str) -> printError(str));
            userSelection = selectionMenu(data, backString, message, errorMessage);
        }

        return --userSelection;
    }

    public void show() {
        NetworkType connectionTypeChoice = connectionTypeSelection();
        if(connectionTypeChoice == NetworkType.RMI) {
            this.networkClient = new RMIClient(SERVER_IP, RMI_PORT, this);
        }
        else if (connectionTypeChoice == NetworkType.SOCKET) {
            this.networkClient = new SocketClient(SERVER_IP, SOCKET_PORT, this);
        }

        this.networkController = networkClient.getNetworkController();
        loginMenu();
    }

    /**
     * this method will be called when the console will ask to user to insert his username and password
     */
    @Override
    protected void loginMenu() {
        printLine("--- Login ---");

        printLine("Username:");
        String playerUsername = readUserInput.nextLine();

        printLine("Password:");
        String playerPassword = readUserInput.nextLine();

        networkController.login(playerUsername, playerPassword);
    }

    /**
     * Receives the login operation result
     * @param result login result
     */
    public void loginResult(Boolean result) {
        if (result)
            printLine("Access granted!");
        else {
            printError("Access denied! Wrong username or password");
            loginMenu();
        }
    }

    /**
     * this method will be called when the console will ask user what kind of connection technology he wants to use, Rmi or Socket
     * @return the user's choice
     */
    @Override
    public NetworkType connectionTypeSelection() {
        List<NetworkType> networkTypes = Arrays.asList(NetworkType.values());
        Integer selection = selectionMenu(networkTypes,
                Optional.empty(),
                Optional.of("Select the network connection type:"),
                Optional.of("Invalid selection"));

        return networkTypes.get(selection);
    }

    /**
     * this method will be called when the user will choice about which context player wish to use
     */
    @Override
    public Integer contextSelection(List<PlayerSelectableContexts> allContext)  {
        return selectionMenu(allContext,
                Optional.of("End turn"),
                Optional.of("Select the context to enter or 0 to end this turn"),
                Optional.of("Invalid selection"));
    }

    /**
     * this method will be called when player will want to play or discard some leader cards
     * @param playerLeaderCards the leader cards that player actually have in his hand
     * @param action the action to do with the leader card
     * @return the index of the selected leader card
     */
    @Override
    public Integer leaderCardAction(List<String> playerLeaderCards, LeaderCardsAction action) {
        return selectionMenu(playerLeaderCards,
                Optional.of("Back to context selection"),
                Optional.of(String.format("Select the leader card to %1$s", action.toString().toLowerCase())),
                Optional.of("Invalid selection"));
    }

    /**
     * this method will be called when player will want to enter into market
     * @param market all market`s slot information
     */
    @Override
    public Integer marketSlotSelection(Market market) {
        List<List<Map.Entry<String,Integer>>> slotsResources = new ArrayList<>();
        market.getMarketSlots().forEach((ActionSlot as) -> {
            Map res = new HashMap<>();
            Resources resources = as.getResourcesReward().getResources();
            resources.getResources().forEach((ResourceType rt, Integer val) -> res.put(rt.toString(), val));
            if (as.getResourcesReward().getCouncilPrivilege() > 0)
                res.put("COUNCIL PRIVILEGES", as.getResourcesReward().getCouncilPrivilege());
            slotsResources.add(new ArrayList<>(res.entrySet()));
        });

        Integer resourcesLines = 0;
        for (List sr : slotsResources) {
            if (sr.size() > resourcesLines)
                resourcesLines = sr.size();
        }

        printFormat(" %1$s \n", String.join(" ", Collections.nCopies(slotsResources.size(), "__________")));
        printFormat("|%1$s|\n", String.join("|", Collections.nCopies(slotsResources.size(), "          ")));

        for (int i = 0; i < resourcesLines; i++) {
            String s;

            /*print resource value and get resources' splitted string*/
            s = "|";
            List<String[]> splittedStrings = new ArrayList<>();
            Integer resourceNameLines = 0;
            for (List<Map.Entry<String,Integer>> sr : slotsResources) {
                List<String> slotResource = new ArrayList<>();
                if (i < sr.size()) {
                    s = String.format("%1$s%2$-10s|", s, sr.get(i).getValue());
                    String splittedResourceName[] = Utilities.splitStringByLength(sr.get(i).getKey(), 10);
                    splittedStrings.add(splittedResourceName);
                    if (splittedResourceName.length > resourceNameLines)
                        resourceNameLines = splittedResourceName.length;
                }
                else {
                    s = String.format("%1$s%2$10s|", s, "");
                    splittedStrings.add(new String[0]);
                }
            }
            printLine(s);

            /*print resources names*/
            for (int j = 0; j < resourceNameLines; j++) {
                s = "|";
                for (String name[] : splittedStrings) {
                    if (j < name.length)
                        s = String.format("%1$s%2$-10s|", s, name[j]);
                    else
                        s = String.format("%1$s%2$10s|", s, "");
                }
                printLine(s);
            }

            /*print empty line*/
            printFormat("|%1$s|\n", String.join("|", Collections.nCopies(slotsResources.size(), "          ")));
        }

        printFormat("|%1$s|\n", String.join("|", Collections.nCopies(slotsResources.size(), "__________")));

        //TODO: print dice value requirements
        //TODO: player input

        return 0;

        /*Integer playerChosenFamilyMember;
        Integer playerChosenFamilyMemberValue;
        Integer playerChosenSlot;
        Boolean validUserInput;

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

        do {
            CLIStuff.printToConsole.println("which family member do you wish to put into market? ");
            playerChosenFamilyMember = readUserInput.nextInt();

            try {
                Validator.checkValidity(playerChosenFamilyMember.toString());
                validUserInput = true;
            } catch (IncorrectInputException ex) {
                CLIStuff.printToConsole.println("please select a valid family member");
                validUserInput = true;
            }
        }
        while(!validUserInput);


        playerChosenFamilyMemberValue = player.getFamilyMembers().get(playerChosenFamilyMember).getValue();

        if(playerChosenFamilyMemberValue < 1)
            servantsSelection(player.getResources().getResourceByType(ResourceType.SERVANTS), 1);

        validUserInput = false;

        do {
            CLIStuff.printToConsole.println("in which action slot do you wish to put this family member? ");
            playerChosenSlot = readUserInput.nextInt();

            try {
                Validator.checkValidity(playerChosenSlot.toString());
                validUserInput = true;
            }
            catch (IncorrectInputException ex) {
                CLIStuff.printToConsole.println("please select a valid action slot ");
            }
        }
        while(!validUserInput);

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
        Boolean validUserInput = false;

       do {
           CLIStuff.printToConsole.format("in which slot do you want to %s?", userChoice);
           selectedSlot = readUserInput.nextInt();

           try {
               Validator.checkValidity(selectedSlot.toString());
               validUserInput = true;
           }
           catch(IncorrectInputException ex) {
               CLIStuff.printToConsole.println("please select a valid slot");
           }

       }
       while(!validUserInput);

       validUserInput = false;

        do {
            CLIStuff.printToConsole.println("which family member do you wish to use? ");
            selectedFamilyMember = readUserInput.nextInt();

            try {
                Validator.checkValidity(selectedFamilyMember.toString());
                validUserInput = true;
            } catch (IncorrectInputException ex) {
                CLIStuff.printToConsole.println("please select a valid family member");
            }
        }
        while(!validUserInput);

        familyMemberValue = player.getFamilyMembers().get(selectedFamilyMember).getValue();

        tempDiceValue = familyMemberValue;

        CLIStuff.printToConsole.format("actually, you can %s for %d. Do you wish to use some servants to increase this value? ",userChoice ,familyMemberValue);
        useSomeServants = (readUserInput.nextLine().equalsIgnoreCase("yes")) ? true : false;

        if(useSomeServants) {

           do {
               CLIStuff.printToConsole.println("how many servants do you wish to use? ");
               usedServants = servantsSelection(servantsAvailable, readUserInput.nextInt());
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
        Boolean validUserInput = false;

        do {
            CLIStuff.printToConsole.println("which family member do you wish to use? ");
            selectedFamilyMember = readUserInput.nextInt();

            try {
                Validator.checkValidity(selectedFamilyMember.toString());
                validUserInput = true;
            }
            catch (IncorrectInputException ex) {
                CLIStuff.printToConsole.println("please select a valid family member");
            }
        }
        while (!validUserInput);

            chosenFamilyMemberValue = player.getFamilyMembers().get(selectedFamilyMember).getValue();

            if(chosenFamilyMemberValue < 1) {

                usedServants = servantsSelection(servantsAvailable, 1);

            }

        //print councilPalace

            validUserInput = false;

            do {
                CLIStuff.printToConsole.println("which CouncilPrivilege bonus do you wish to take? ");
                selectedCouncilPrivilegeBonus = readUserInput.nextInt();

               try {
                   Validator.checkValidity(selectedCouncilPrivilegeBonus.toString());
                   validUserInput = true;
               }
               catch (IncorrectInputException ex) {
                   CLIStuff.printToConsole.println("please select a correct CouncilPrivilege bonus ");
               }
            }
            while(!validUserInput);

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
        Boolean validUserInput = false;

       do {
           CLIStuff.printToConsole.println("in which tower do you wish to bring your family member? ");
           tower = readUserInput.nextInt();

           try {
               Validator.checkValidity(tower.toString());
               validUserInput = true;
           }
           catch (IncorrectInputException ex) {
               CLIStuff.printToConsole.println("please select a valid tower ");
           }
       }
       while(!validUserInput);

        validUserInput = false;

        do {
            CLIStuff.printToConsole.println("in which tower's floor do you wish to put your family member? ");
            floor = readUserInput.nextInt();

            try {
                Validator.checkValidity(floor.toString());
                validUserInput = true;
            }
            catch (IncorrectInputException ex) {
                CLIStuff.printToConsole.println("please select a valid tower's floor");
            }
        }
        while (!validUserInput);

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
            usedServants = readUserInput.nextInt();
        }
        while(((usedServants + servantsAvailable) < minimumServantsRequested) || usedServants > servantsAvailable);

        return usedServants;

    }

    @Override
    public void printTowers(ArrayList<Tower> towers) {

        String cardName = "support to the pope";
        Integer valueOfInstantResourceBonus = 1; //to be determine according to tower`s floor
        String typeOfIntegerResourceBonus = "military points";
        Integer diceValueRequested = 1;

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
        //TODO
    }

}