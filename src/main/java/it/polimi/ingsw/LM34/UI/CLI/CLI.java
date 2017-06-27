package it.polimi.ingsw.LM34.UI.CLI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Enums.Model.WorkingAreaType;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;
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
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.UI.CLI.CLIStuff.*;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * this class was built on {@link UIInterface}. It implement all method body that will be used to describe and manage Cli
 */

public class CLI implements UIInterface {
    private AbstractClient networkClient;
    private ClientNetworkController networkController;

    private List<Tower> towers;
    private WorkingArea productionArea;
    private WorkingArea harvestArea;
    private Market market;
    private CouncilPalace palace;
    private List<ExcommunicationCard> excommunicationCards;
    private List<Player> players;
    private List<Dice> dices;
    private List<PlayerSelectableContexts> selectableContexts;

    public CLI() {
        dices = new ArrayList<>();
        players= new ArrayList<>();
        excommunicationCards = new ArrayList<>();
        towers = new ArrayList<>();
        selectableContexts = new ArrayList<>();
        for(PlayerSelectableContexts c : PlayerSelectableContexts.values())
            selectableContexts.add(c);

    }
    private Integer selectionMenu(List<?> data, Optional<String> backString, Optional<String> message, Optional<String> errorMessage) {
        List<String> selectableContexts = new ArrayList<>();
        Boolean validUserInput = false;
        Integer choice = 0;
        String input;

        CLIStuff.printToConsole.println("Choose the context you would like to enter");
        selectableContexts.forEach(s -> printFormat(s.toString()));

        do {
            input = readUserInput.nextLine();
            try {
                Validator.checkValidity(input, selectableContexts);
                validUserInput = true;
                choice = Integer.parseInt(input);
            }  catch (Exception e) {
                LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
                printError("Incorrect value");
            }
        }
        while(!validUserInput);

        //TODO
        /*backString.ifPresent((str) -> printFormat("%1$d) %2$s\n", 0, str));
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

        return --userSelection;*/
        return null; //TODO
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
        this.towers = towers;
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

        /***Shows the points scored by all players***/
        players.forEach(player ->
            printFormat("%1$s has scored %2$s victory points", player.getPlayerName(),
                    player.getResources().getResourceByType(ResourceType.VICTORY_POINTS))
        );

        /***And now declare the winner***/
        for(Player player : players) {
            playerPoints = player.getResources().getResourceByType(ResourceType.VICTORY_POINTS);
            if (playerPoints > maxVictoryPointsScored) {
                maxVictoryPointsScored = playerPoints;
                winnerName = player.getPlayerName();
            }
        }
        printFormat("And... the winner is %1$s\n",winnerName);
    }

    //TODO
    @Override
    public PlayerAction turnMainAction(Optional<Boolean> lastActionValid) {
        //TODO: return
        return null;
    }

    //TODO
    @Override
    public PlayerAction turnSecondaryAction(Optional<Boolean> lastActionValid) {
        return null;
    }

    @Override
    public Integer familyMemberSelection(List<FamilyMember> familyMembers) {
        Boolean validUserInput = false;
        Integer selectedFamilyMember = 0;
        String input;

         do {
             familyMembers.forEach(fm -> printFormat("%1$s: %2$d\n", fm.getDiceColorAssociated(), fm.getValue()));
            CLIStuff.printToConsole.println("which family member do you wish to use? ");
            input = readUserInput.nextLine();

            try {
                Validator.checkValidity(input, familyMembers);
                validUserInput = true;
                selectedFamilyMember = Integer.parseInt(input);
            }  catch (Exception e) {
                LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
                    printError("Please select a valid family member");
            }
        }
        while(!validUserInput);

         return selectedFamilyMember;
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
    //TODO
    public Integer contextSelection(List<PlayerSelectableContexts> allContext)  {
        return selectionMenu(allContext,
                Optional.of("End turn"),
                Optional.of("Select the context to enter or 0 to end this turn"),
                Optional.of("Invalid selection"));
    }

    /**
     * this method will be called when player will want to enter into market
     */
    public void marketSlotSelection() {
        String input;
        Integer selectedSlot;
        Boolean validUserInput = false;
        /***Show the market***/
        printSlots(market.getActionSlots());

        printToConsole.println("in which slot do you want to place one of your pawn?");
        do {
            input = readUserInput.nextLine();

            try {
                Validator.checkValidity(input, market.getActionSlots());
                validUserInput = true;
                selectedSlot = Integer.parseInt(input);
            }
            catch (IncorrectInputException e) {
                LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
                printError(INCORRECT_INPUT);
            }
        } while(!validUserInput);

       //TODO: send to server (market, selectedSlot)
    }

    public void workingAreaSlotSelection() {
        String input;
        Integer selectedSlot;
        Boolean validUserInput = false;
        WorkingAreaType selectedArea = WorkingAreaType.PRODUCTION;

        do {
            CLIStuff.printToConsole.println("Choose one of the working areas:");
            printFormat("1) %2$s\n", WorkingAreaType.PRODUCTION);
            printFormat("2) %1$s\n", WorkingAreaType.HARVEST);
            input = readUserInput.nextLine();

            if(input.equalsIgnoreCase(WorkingAreaType.PRODUCTION.toString())) {
                selectedArea = WorkingAreaType.PRODUCTION;
                validUserInput = true;
            }
            else if(input.equalsIgnoreCase(WorkingAreaType.HARVEST.toString())) {
                selectedArea = WorkingAreaType.HARVEST;
                validUserInput = true;
            }else
                printError(INCORRECT_INPUT);
        } while(!validUserInput);

        validUserInput = false;

       do {
           CLIStuff.printToConsole.format("in which slot of the %1$s area do you want to %s?", selectedArea.toString());
           selectedSlot = readUserInput.nextInt();

           try {
               Validator.checkValidity(selectedSlot.toString(), market.getActionSlots());
               validUserInput = true;
           }
           catch (IncorrectInputException e) {
               LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
               printError(INCORRECT_INPUT);
           }
       } while(!validUserInput);

        //TODO: send option to server: (selectedArea, selectedSlot)
    }

    /**
     * this method will be called when player will enter into Council Palace
     */
    public void councilPalaceSelection() {
        Boolean decision;
        String input;
        Boolean validUserInput = false;

        /***SHOW COUNCIL PALACE***/
        List<ActionSlot> slots = new ArrayList<>();
        slots = palace.getActionSlots();
        printSlots(slots);

        do {
            CLIStuff.printToConsole.println("Do you want to place one of your pawns in the Council Palace?");
            input = readUserInput.nextLine();

            if (input.equalsIgnoreCase("yes")) {
                decision = true;
                validUserInput = true;
                //TODO: send to server councilPalaceSelection if true
            } else if (input.equalsIgnoreCase("no")) {
                decision = false;
            validUserInput = true;
            //TODO: go back to menu selection
            }else {
                printError("Invalid choice");
                councilPalaceSelection();
            }
            } while(!validUserInput);
    }

    private void printSlots(List<ActionSlot> slots) {
        List<List<Map.Entry<String,Integer>>> slotsResources = new ArrayList<>();
        List<FamilyMember> pawnsInserted = new ArrayList<>();
        List<Integer> diceValues = new ArrayList<>();

        slots.forEach((ActionSlot as) -> {
            diceValues.add(as.getDiceValue());
            /***PAWN COLOR INSERTED IN SLOT***/
            pawnsInserted.add(as.getFamilyMember());
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

        for(Integer diceValue : diceValues)
            printDiceValue(diceValue.toString());
        printLine("");

        for(FamilyMember pawn : pawnsInserted)
            printPawn(pawn.getFamilyMemberColor(), pawn.getDiceColorAssociated());

        printFormat(" \n%1$s \n", String.join(" ", Collections.nCopies(slotsResources.size(), "__________")));
        printFormat("|%1$s|\n", String.join("|", Collections.nCopies(slotsResources.size(), "          ")));

        for (int i = 0; i < resourcesLines; i++) {
            String s;

            /*print resource value and get resources' splitted string*/
            s = "|";
            List<String[]> splittedStrings = new ArrayList<>();
            Integer resourceNameLines = 0;
            for (List<Map.Entry<String,Integer>> sr : slotsResources) {
                if (i < sr.size()) {
                    s = String.format("%1$s%2$-10s|", s, sr.get(i).getValue());
                    String[] splittedResourceName = Utilities.splitStringByLength(sr.get(i).getKey(), 10);
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
                for (String[] name : splittedStrings) {
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
    }

    /**
     * this method will be called when the user will choice which tower wants to make his action and in which tower's floor
     * @param towerNumber how many towers contains a game board
     * @param towerFloor how many floors contains a tower
     */
    public String towerSlotSelection() {
        Integer tower;
        Integer floor;
        String towerAndItsFloor;
        Boolean validUserInput = false;

        /***SHOW THE TOWERS***/
        printTowers(towers);

        /***Let the player choice the tower and the level***/
        do {
           CLIStuff.printToConsole.println("In which tower do you wish to bring your family member? ");
           tower = readUserInput.nextInt();

           try {
               Validator.checkValidity(tower.toString(), towers);
               validUserInput = true;
           }
           catch (IncorrectInputException ex) {
               LOGGER.log(Level.INFO, getClass().getSimpleName(), ex);
               printError("please select a valid tower ");
           }
        } while(!validUserInput);

        validUserInput = false;

        do {
            CLIStuff.printToConsole.println("in which tower's floor do you wish to put your family member? ");
            floor = readUserInput.nextInt();

            try {
                Validator.checkValidity(floor.toString(), towers.get(tower).getTowerSlots());
                validUserInput = true;
            }
            catch (IncorrectInputException ex) {
                LOGGER.log(Level.INFO, getClass().getSimpleName(), ex);
                printError("please select a valid tower's floor");
            }
        } while (!validUserInput);

         // decrement tower and floor because user's choice is between 1 and 4, but server's range is between 0 and 3

        tower--; floor--;

        towerAndItsFloor = towers.get(tower).getCardColor().toString() + floor.toString(); //TODO: verify the convention works

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
        String input;
        Integer usedServants = 0;

        printFormat("to complete this action, you need at least %1$d servants (you have %2$d servants)\n",minimumServantsRequested, servantsAvailable);
        CLIStuff.printToConsole.println("How many servants do you wish to use?");

        try {
            input = readUserInput.nextLine();
            if(input.isEmpty())
                contextSelection(selectableContexts);
            usedServants = Integer.parseInt(input);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
            printError("Incorrect number of servants");
            servantsSelection(servantsAvailable, minimumServantsRequested);
        }

        return usedServants;
    }

    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        String input;
        Integer choice = 0;
        choices.forEach(c ->
            printFormat("resources required: %1$s ---> Resources provided: %2$s, CouncilPrivileges provided: %3$d\n",
                    c.getLeft().getResources().toString(), c.getRight().getResources().getResources().toString(), c.getRight().getCouncilPrivilege())
        );

        try {
            input = readUserInput.nextLine();
            choice = Integer.parseInt(input);
            Validator.checkValidity(choice.toString());
        }
        catch (Exception e) {
            printError("The resources exchange choosed is not valid");
            resourceExchangeSelection(choices);
        }

        return choice;
    }

    @Override
    public Pair<String, LeaderCardsAction>  leaderCardSelection(List<LeaderCard> leadersOwned) {
        LeaderCardsAction actionChoiced;
        String input;

        printFormat("Choose to Play, Copy or Discard Leader\n");
        input = readUserInput.nextLine();
        if(input.equalsIgnoreCase(LeaderCardsAction.PLAY.toString()))
            actionChoiced = LeaderCardsAction.PLAY;
        else if (input.equalsIgnoreCase(LeaderCardsAction.COPY.toString()))
            actionChoiced = LeaderCardsAction.COPY;
        else if (input.equalsIgnoreCase(LeaderCardsAction.DISCARD.toString()))
            actionChoiced = LeaderCardsAction.DISCARD;
        else {
            printError("Invalid action");
            leaderCardSelection(leadersOwned);
        }

        leadersOwned.forEach(l -> printFormat("%1$s\n" ,l.getName()));

        try {
            input = readUserInput.nextLine();
            Validator.checkLeaderValidity(leadersOwned, input);
        }
        catch (IncorrectInputException e) {
            printError("The leader choosed is not valid");
            leaderCardSelection(leadersOwned);
        }

        return new ImmutablePair(leadersOwned, input);
    }

    @Override
    public Boolean churchSupport() {
        String input = new String();
        Boolean choice = false;
        printFormat("Do you want to Support the Church (Yes or No)?\n");

        input = readUserInput.nextLine();

        if(input.equalsIgnoreCase("yes"))
            choice = true;
        else if (input.equalsIgnoreCase("no"))
            choice = false;
        else {
            printError("Invalid choice");
            churchSupport();
        }
        return choice;
    }

    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        String input;
        Integer choice = 0;
        printFormat("Choose the reward you desire:\n");
        availableBonuses.forEach(b -> printFormat("%1$s\n", b.getResources()));
        try {
            input = readUserInput.nextLine();
            choice = Integer.parseInt(input);
            Validator.checkValidity(choice.toString());
        }
        catch (Exception e) {
            printError("The selected reward is not valid");
            selectCouncilPrivilegeBonus(availableBonuses);
        }
        return choice;
    }

    public void printTowers(List<Tower> towers) {

        for(Tower tower : towers) {
            List<List<Map.Entry<String,Integer>>> slotsResources = new ArrayList<>();
            List<Integer> diceValues = new ArrayList<>();
            List<FamilyMember> pawnsInserted = new ArrayList<>();
            List<String> cardNames = new ArrayList<>();
            tower.getTowerSlots().forEach((TowerSlot as) -> {
                Map res = new HashMap<>();
                /***DICE VALUE***/
                diceValues.add(as.getDiceValue());
                /***PAWN COLOR INSERTED IN SLOT***/
                pawnsInserted.add(as.getFamilyMember());
                cardNames.add(as.getCardStored().getName());
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

            //List<String> cards = new ArrayList<>();

            printTowerTypeColor(tower.getCardColor());

            //tower.getTowerSlots().forEach(t -> cards.add(t.getCardStored().getName()));

            printLine("");
            for(Integer diceValue : diceValues)
                printDiceValue(diceValue.toString());
            printLine("");

            for(FamilyMember pawn : pawnsInserted)
                printPawn(pawn.getFamilyMemberColor(), pawn.getDiceColorAssociated());

            //printCardSplittedNames(tower.getCardColor(), cards);

            printFormat("\n %1$s \n", String.join(" ", Collections.nCopies(slotsResources.size(), "__________")));
            printFormat("|%1$s|\n", String.join("|", Collections.nCopies(slotsResources.size(), "          ")));
            for (int i = 0; i < resourcesLines; i++) {
                String s;

            /*print resource value and get resources' splitted string*/
                s = "|";
                List<String[]> splittedStrings = new ArrayList<>();
                Integer resourceNameLines = 0;
                for (List<Map.Entry<String,Integer>> sr : slotsResources) {
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
                    for (String[] name : splittedStrings) {
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
          }
    }

    public void showExcommunicationCards() {
        excommunicationCards.forEach(ex -> printFormat("Period %1$d, Penalty %2$s\n", ex.getPeriod(),ex.getPenalty().getClass().getSimpleName().toString()));
    }

    public static void main (String[] args) {
        Configurator.loadConfigs();
        List<Tower> towers = Configurator.getTowers();
        AbstractDevelopmentCard card = new TerritoryCard("Support to the Pope", 1, 2, null, null);
        AbstractDevelopmentCard card1 = new TerritoryCard("General", 1, 2, null, null);
        AbstractDevelopmentCard card2 = new TerritoryCard("Repair the Church", 1, 2, null, null);
        AbstractDevelopmentCard card3 = new TerritoryCard("Citadel", 1, 2, null, null);
        towers.get(0).getTowerSlots().get(0).setCardStored(card);
        towers.get(0).getTowerSlots().get(1).setCardStored(card1);
        towers.get(0).getTowerSlots().get(2).setCardStored(card2);
        towers.get(0).getTowerSlots().get(3).setCardStored(card3);
        Market market = Configurator.getMarket();
        CLI cli = new CLI();
        cli.market = market;
    }

    public void showPlayersInfo() {
        CLIStuff.printToConsole.println("Here are the infos about all players in game");
        players.forEach(p -> {
                printPlayer(p.getPawnColor(),p.getPlayerName());
                p.getActivatedLeaderCards().forEach(c -> printFormat("%1$s ", c.getName()));
                printLine("");
                Resources res = p.getResources();
                for(ResourceType t : ResourceType.values())
                    printFormat("%1$s : %2$s", t.toString(), res.getResourceByType(t).toString());
                p.getExcommunicationCards().forEach(e -> printFormat("%1$s : %2$s", e.getPeriod(),
                                                            e.getPenalty().getClass().getSimpleName()));

                printFormat("The Personal Board of player %1$s\n", p.getPlayerName());
                PersonalBoard pb = p.getPersonalBoard();
                for(DevelopmentCardColor color : DevelopmentCardColor.values()) {
                    printTowerTypeColor(color);
                    pb.getDevelopmentCardsByType(color).get().forEach(c -> printFormat("%1$s ", c.getName()));
                    printLine("");
                }
        }
        );
    }

    public void showDiceValues() {
        dices.forEach(d -> printFormat("%1$s value: %2$d ", d.getColor().toString(), d.getValue()));
    }

    @Override
    public void endTurn() {
        CLIStuff.printYellow("Your turn has ended\n");
    }

    @Override
    public void disconnectionWarning() {
        CLIStuff.printError("You are not connected to the game\n");
    }

    @Override
    public Integer bonusTileSelection(List<BonusTile> bonusTiles) {
        String input;
        Integer bonusTileSelected = 0;
        Boolean validUserInput = false;

        CLIStuff.printToConsole.println("Choose the bonus tile you desire to have");

        bonusTiles.forEach(bt -> {
            printToConsole.println("This bonus tile gives:");
            printFormat("For harvest area %1$s", bt.getHarvestBonus().getResources().toString());
            printFormat("For production area %1$s", bt.getHarvestBonus().getResources().toString());
        });

        do {
            input = readUserInput.nextLine();

            try {
                Validator.checkValidity(input, bonusTiles);
                bonusTileSelected = Integer.parseInt(input);
                validUserInput = true;
            }
            catch (IncorrectInputException e) {
                printError(INCORRECT_INPUT);
            }
        } while(!validUserInput);

        return bonusTileSelected;
    }

    @Override
    public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) {
        String input;
        Integer leaderSelected = 0;
        Boolean validUserInput = false;

        CLIStuff.printToConsole.println("Choose the leader you desire to have at your service");

        leaderCards.forEach(lc -> {
            printToConsole.println("This leader has the following characteristics:");
            printFormat("Requirements: %1$s", lc.getRequirements().getResourcesRequirements().get().getResources().toString());
            printFormat("Bonus Type: %1$s", lc.getBonus().getClass().getSimpleName());
        });

        do {
            input = readUserInput.nextLine();

            try {
                Validator.checkValidity(input, leaderCards);
                leaderSelected = Integer.parseInt(input);
                validUserInput = true;
            }
            catch (IncorrectInputException e) {
                printError(INCORRECT_INPUT);
            }
        } while(!validUserInput);

        return leaderSelected;
    }

}