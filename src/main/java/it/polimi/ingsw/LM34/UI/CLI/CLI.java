package it.polimi.ingsw.LM34.UI.CLI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Dice;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.Client.AbstractClient;
import it.polimi.ingsw.LM34.Network.Client.ClientNetworkController;
import it.polimi.ingsw.LM34.Network.Client.RMI.RMIClient;
import it.polimi.ingsw.LM34.Network.Client.Socket.SocketClient;
import it.polimi.ingsw.LM34.Network.PlayerAction;
import it.polimi.ingsw.LM34.UI.UIInterface;
import it.polimi.ingsw.LM34.Utils.Configurator;
import it.polimi.ingsw.LM34.Utils.Utilities;
import it.polimi.ingsw.LM34.Utils.Validator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static it.polimi.ingsw.LM34.UI.CLI.CLIStuff.*;

/**
 * this class was built on {@link UIInterface}. It implement all method body that will be used to describe and manage Cli
 */

public class CLI implements UIInterface {
    private AbstractClient networkClient;
    private ClientNetworkController networkController;

    private List<Tower> towersSpaces;
    private WorkingArea productionArea;
    private WorkingArea harvestArea;
    private Market market;
    private CouncilPalace palace;
    private List<ExcommunicationCard> excommunicationCards;
    private List<Player> players;
    private List<Dice> dices;

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
    public void loginMenu() {
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

    @Override
    public void setExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
        this.excommunicationCards = excommunicationCards;
    }

    @Override
    public void updateTowers(List<Tower> towers) {
        this.towersSpaces = towers;
    }

    @Override
    public void updateCouncilPalace(CouncilPalace councilPalace) {
        this.palace = councilPalace;
    }

    @Override
    public void updateMarket(Market market) {
        this.market = market;
    }

    @Override
    public void updateProductionArea(WorkingArea productionArea) {
        this.productionArea = productionArea;

    }

    @Override
    public void updateHarvestArea(WorkingArea harvestArea) {
        this.harvestArea = harvestArea;
    }

    @Override
    public void updatePlayersData(List<Player> players) {
        players = new ArrayList<>();
        this.players = players;
    }

    @Override
    public void updateDiceValues(List<Dice> dicesValues) {
        this.dices = dicesValues;
    }

    @Override
    public void endGame(List<Player> players) {
        Integer maxVictoryPointsScored = 0;
        Integer playerPoints = 0;
        String winnerName= new String();
        players.forEach(player -> {
            System.out.println(player.getPlayerName() + " has totalized " + player.getResources().getResourceByType(ResourceType.VICTORY_POINTS) + " victory points.");
        });

        for(Player player : players) {
            playerPoints = player.getResources().getResourceByType(ResourceType.VICTORY_POINTS);
            if (playerPoints > maxVictoryPointsScored) {
                maxVictoryPointsScored = playerPoints;
                winnerName = player.getPlayerName();

            }
        }
        System.out.println("And... the winner is " + winnerName);
    }

    @Override
    public PlayerAction turnMainAction(Optional<Boolean> lastActionValid) {
        return null;
    }

    @Override
    public PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid) {
        return null;
    }

    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        return null;
    }

    /**
     * this method will be called when the console will ask user what kind of connection technology he wants to use, Rmi or Socket
     * @return the user's choice
     */
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

        String input = new String();
        Integer choice = 0;

        try {
            input = readUserInput.nextLine();
            choice = Integer.parseInt(input);
            Validator.checkValidity(choice.toString());
        }
        catch (Exception e) {
            marketSlotSelection(market);
        }

        return choice;

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
    public Integer councilPalace(Player player) {
        String input = new String();
        Integer selectedFamilyMember = 0;
        Integer chosenFamilyMemberValue;
        Integer servantsAvailable = player.getResources().getResourceByType(ResourceType.SERVANTS);
        Integer usedServants;
        Integer selectedCouncilPrivilegeBonus = 0;
        Boolean validUserInput = false;

        do {
            CLIStuff.printToConsole.println("which family member do you wish to use? ");
            player.getFamilyMembers().forEach(f -> System.out.println(f.getDiceColorAssociated()));

            input = readUserInput.nextLine();
            try {
                Validator.checkValidity(input);
                selectedFamilyMember = Integer.parseInt(input);
                Validator.checkValidity(selectedFamilyMember.toString(), player.getFamilyMembers());
                validUserInput = true;
            }
            catch (IncorrectInputException ex) {
                CLIStuff.printToConsole.println("please select a valid family member");
            }
        }
        while (!validUserInput);

            chosenFamilyMemberValue = player.getFamilyMembers().get(selectedFamilyMember).getValue();

            //if(chosenFamilyMemberValue < 1) {

                usedServants = servantsSelection(servantsAvailable, 1);


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
        String input = new String();
        Integer usedServants = 0;

        do {
            CLIStuff.printToConsole.println("to complete this action, you need at least " + minimumServantsRequested.toString() + "servants, and max " + servantsAvailable.toString() + " servants ");
            CLIStuff.printToConsole.println("how many servants do you want to use? ");

            try {
                input = readUserInput.nextLine();
                usedServants = Integer.parseInt(input);
                Validator.checkValidity(usedServants.toString());
            } catch (Exception e) {
                servantsSelection(servantsAvailable, minimumServantsRequested);
            }
        }
        while((usedServants < minimumServantsRequested) || usedServants > servantsAvailable);

        Configurator.print("used servants" + usedServants);
        return usedServants;
    }

    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        String input = new String();
        Integer choice = 0;
        choices.forEach(c -> {
            System.out.println("Resources required: " + c.getLeft().getResources().toString() + "---> Resources provided:" + c.getRight().getResources().getResources().toString() + ", CouncilPrivilege provided: " + c.getRight().getCouncilPrivilege());
        });

        try {
            input = readUserInput.nextLine();
            choice = Integer.parseInt(input);
            Validator.checkValidity(choice.toString());
        }
        catch (Exception e) {
            resourceExchangeSelection(choices);
        }

        return choice;
    }

    @Override
    public Pair<String, LeaderCardsAction>  leaderCardSelection(List<LeaderCard> leaderCards) {
        LeaderCardsAction actionChoiced;
        String input = new String();
        Pair<String, LeaderCardsAction> pair;
        Integer choice = 0;

        System.out.println("Choice to Play or Discard Leader");
        input = readUserInput.nextLine();
        //TODO: complete this
        if(input.equalsIgnoreCase(LeaderCardsAction.PLAY.toString()))
            actionChoiced = LeaderCardsAction.PLAY;
        else if (input.equalsIgnoreCase(LeaderCardsAction.DISCARD.toString()))
        actionChoiced = LeaderCardsAction.DISCARD;
        else
            leaderCardSelection(leaderCards);

        leaderCards.forEach(l -> System.out.println("Leader name: " + l.getName()));

        try {
            input = readUserInput.nextLine();
            Validator.checkLeaderValidity(leaderCards, input);
        }
        catch (Exception e) {
            leaderCardSelection(leaderCards);
        }

        return new ImmutablePair(leaderCards, input);
    }

    @Override
    public Boolean churchSupport() {
        String input = new String();
        Boolean choice = false;
        System.out.println("Do you want to Support the Church?");

        input = readUserInput.nextLine();

        if(input.equalsIgnoreCase("true"))
            choice = true;
        else if (input.equalsIgnoreCase("false"))
            choice = false;
        else
            churchSupport();

        return choice;
    }

    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        String input = new String();
        Integer choice = 0;
        System.out.println("Choose the reward you desire:");
        availableBonuses.forEach(b -> System.out.println(b.getResources()));
        try {
            input = readUserInput.nextLine();
            choice = Integer.parseInt(input);
            Validator.checkValidity(choice.toString());
        }
        catch (Exception e) {
            selectCouncilPrivilegeBonus(availableBonuses);
        }

        return choice;
    }

    public void printTowers(List<Tower> towers) {

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
    public void printGameBoard() {
        //TODO: remove
    }

    public static void main (String[] args) {
        Configurator.loadConfigs();
        List<Tower> towers = Configurator.getTowers();
        Market market = Configurator.getMarket();
        CLI cli = new CLI();
        cli.market = market;
        //cli.printTowers(towers);
        //cli.marketSlotSelection(market);
        //cli.selectCouncilPrivilegeBonus(Configurator.getCouncilPrivilegeRewards());
        /*List<Pair<Resources, ResourcesBonus>> resExc = new ArrayList<>();
        resExc.add(new ImmutablePair(new Resources(3,4,3,9), new ResourcesBonus(new Resources(3,3,3,2), 3)));
        //cli.resourceExchangeSelection(resExc);*/
        //cli.leaderCardSelection(Configurator.getLeaderCards(4), LeaderCardsAction.DISCARD);
        //cli.churchSupport();
        //cli.servantsSelection(4,2);
        /*List<Player> players = new ArrayList<>();
        Player giacomo = new Player("giacomo", BLUE, new PersonalBoard());
        giacomo.addResources(new Resources(4,5,1,2));*/
        Player antonio = new Player("antonio", PawnColor.RED, new PersonalBoard());
        antonio.addResources(new Resources(4,5,1,2, 7,4 ,9));
        /*players.add(giacomo); players.add(antonio);*/
        //cli.endGame(players);
        cli.councilPalace(antonio);
    }
}