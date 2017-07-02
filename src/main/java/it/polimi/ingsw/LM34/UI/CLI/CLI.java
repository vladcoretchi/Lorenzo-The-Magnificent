package it.polimi.ingsw.LM34.UI.CLI;

import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Enums.Model.WorkingAreaType;
import it.polimi.ingsw.LM34.Enums.UI.GameInformationType;
import it.polimi.ingsw.LM34.Enums.UI.NetworkType;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.*;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.BonusTile;
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
    private Map<Integer, Integer> faithPath;
    private Map<Integer, Integer> mapMilitaryPointsForTerritories;
    private Map<Integer, Integer> mapCharactersToVictoryPoints;
    private Map<Integer, Integer> mapTerritoriesToVictoryPoints;
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
        backString.ifPresent((str) -> printFormat("%1$d) %2$s\n", 0, str));
        for (int i = 0; i < data.size(); i++) {
            printFormat("%1$d) %2$s\n", i+1, data.get(i).toString());
        }

        message.ifPresent((str) -> printLine(str));
        Integer selectedValue;
        try {
            selectedValue = Integer.parseInt(readUserInput.nextLine());
            Validator.checkValidity(--selectedValue, data);
        }
        catch (IncorrectInputException ex) {
            LOGGER.info(ex.toString());
            errorMessage.ifPresent((str) -> printError(str));
            selectedValue = selectionMenu(data, backString, message, errorMessage);
        }

        return selectedValue;
    }

    /**
     * Let the player choose the connection type and the try to login
     */
    @Override
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
    @Override
    public void loginResult(Boolean result) {
        if (result)
            printLine("Access granted!");
        else {
            printError("Access denied! Wrong username or password");
            loginMenu();
        }
    }

    @Override
    public void loadExcommunicationCards(List<ExcommunicationCard> excommunicationCards) {
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

    /**
     * Show the game results and declare the winner
     * @param players in game
     */
    @Override
    public void endGame(List<Player> players) {
        Integer maxVictoryPointsScored = 0;
        Integer playerPoints;
        String winnerName= new String();

        /***Shows the points scored by all players***/
        players.forEach(player -> {
                    printPlayerName(player.getPlayerName(), player.getPawnColor());
                    printFormat(" has scored %1$s victory points\n", player.getResources().getResourceByType(ResourceType.VICTORY_POINTS));
                }
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

    @Override
    public PlayerAction turnMainAction(Optional<Exception> lastActionValid) {
        //TODO: return
        return null;
    }

    //TODO
    @Override
    public PlayerAction turnSecondaryAction(Optional<Exception> lastActionValid) {
        return null;
    }

    /**
     * @param familyMembers available to the player
     * @return familyMember choosed from the player
     */
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
     * this method will be called when the user will choice about which {@link it.polimi.ingsw.LM34.Controller.ContextFactory} player wish to use
     */
    //TODO
    public Integer contextSelection(List<PlayerSelectableContexts> allContext)  {
        return selectionMenu(allContext,
                Optional.of("End turn"),
                Optional.of("Select the context to enter or 0 to end this turn"),
                Optional.of("Invalid selection"));
    }

    /**
     * This method will be called when player will want to enter into the {@link Market}
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
                //LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
                printError(INCORRECT_INPUT);
            }
        } while(!validUserInput);

       //TODO: send to server (market, selectedSlot)
    }

    /**
     * Provides the player the ability to select one of the two {@link WorkingAreaType} to enter
     */
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
        String input;
        /***SHOW COUNCIL PALACE***/
        printCouncilPalace();

        printToConsole.println("\nDo you want to place one of your pawns in the Council Palace?");
        input = readUserInput.nextLine();

        if ("yes".equalsIgnoreCase(input)) {
            //TODO: send to server councilPalaceSelection if true
        } else if ("no".equalsIgnoreCase(input)) {
        //TODO: go back to menu selection
        }else {
            printError("Invalid choice");
            councilPalaceSelection();
        }
    }

    /**
     * Shows the CouncilPalace state
     */
    private void printCouncilPalace() {
        ActionSlot actionSlot = palace.getActionSlot();
        List<FamilyMember> occupyingPawns = palace.getActionSlot().getFamilyMembers();

        printFormat("Council Palace resources: %1$s CouncilPrivileges: %2$s\n", actionSlot.getResourcesReward().getResources().getResources(),
                                        actionSlot.getResourcesReward().getCouncilPrivilege());

        occupyingPawns.forEach(p -> printPawn(p.getFamilyMemberColor(), p.getDiceColorAssociated()));
    }

    /**
     * Provides a graphic knowledge of the rewards each slots of a particular {@link GameSpace}
     * @param slots
     */
    private void printSlots(List<ActionSlot> slots) {
        List<List<Map.Entry<String,Integer>>> slotsResources = new ArrayList<>();
        List<FamilyMember> pawnsInserted = new ArrayList<>();
        List<Integer> diceValues = new ArrayList<>();

        slots.forEach((ActionSlot as) -> {
            diceValues.add(as.getDiceValue());
            /***PAWN COLOR INSERTED IN SLOT***/
            if(as.getFamilyMembers() != null && as.getFamilyMembers().size() > 0)
                pawnsInserted.add(as.getFamilyMembers().get(0)); //TODO: handle multiple family members
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


        diceValues.forEach(diceValue -> printDiceValue(diceValue.toString()));
        printLine("");

        pawnsInserted.forEach((pawn) -> printPawn(pawn.getFamilyMemberColor(), pawn.getDiceColorAssociated()));

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
        printTowers();

        /***Let the player choose the tower***/
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

        /***Let the player choose the level***/
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
        tower--;
        floor--;

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
        Integer usedServants;

        printFormat("to complete this action, you need at least %1$d servants (you have %2$d servants)\n", minimumServantsRequested, servantsAvailable);
        CLIStuff.printToConsole.println("How many servants do you wish to use?");

        try {
            input = readUserInput.nextLine();
            usedServants = Integer.parseInt(input);
            Validator.checkValidity(usedServants, servantsAvailable);
            return usedServants;
        } catch (IncorrectInputException | NumberFormatException e) {
            //LOGGER.info("");
            printError("Incorrect number of servants");
            servantsSelection(servantsAvailable, minimumServantsRequested);
            return 0;
        }
    }

    /**
     * @param choices available about resource exchange bonuses
     * @return the reward opted by the player
     */
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
            Validator.checkValidity(input, choices);
            choice = Integer.parseInt(input);
        }
        catch (Exception e) {
            //LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
            printError("The resources exchange choosed is not valid");
            resourceExchangeSelection(choices);
        }

        return choice;
    }

    /**
     * @param leadersOwned that the game consider the player to have available
     * @return the leader choosed and the action to perform on him
     */
    @Override
    public Pair<String, LeaderCardsAction>  leaderCardSelection(List<LeaderCard> leadersOwned) {
        LeaderCardsAction actionChoiced = LeaderCardsAction.DISCARD;
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
            Validator.checkValidity(input, leadersOwned);
        }
        catch (IncorrectInputException e) {
            //LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
            printError("The leader choosed is not valid");
            leaderCardSelection(leadersOwned);
        }

        return new ImmutablePair(input, actionChoiced);
    }

    /**
     * The Curch Report decision asked from the game to the player
     * @return the decision of the player
     */
    @Override
    public Boolean churchSupport() {
        String input;
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

    /**
     * @param availableBonuses, set from the game
     * @return the choice made by the player among the options in input
     */
    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        String input;
        Integer choice;
        printFormat("Choose the reward you desire:\n");
        availableBonuses.forEach(b -> printFormat("%1$s\n", b.getResources()));
        try {
            input = readUserInput.nextLine();
            Validator.checkValidity(input, availableBonuses);
            choice = Integer.parseInt(input);
            return choice;
        }
        catch (Exception e) {
            //LOGGER.log(Level.INFO, "Incorrect input", e);
            printError("The selected reward is not valid");
            selectCouncilPrivilegeBonus(availableBonuses);
            return 0;
        }
    }

    /**
     *Prints all the towers and their tower slots showing info about cards stored and reward each slots provides
     */
    public void printTowers() {

        for(Tower tower : this.towers) {
            List<List<Map.Entry<String,Integer>>> slotsResources = new ArrayList<>();
            List<Integer> diceValues = new ArrayList<>();
            List<FamilyMember> pawnsInserted = new ArrayList<>();
            List<String> cardNames = new ArrayList<>();
            tower.getTowerSlots().forEach((TowerSlot as) -> {
                Map res = new HashMap<>();
                /***DICE VALUE***/
                diceValues.add(as.getDiceValue());
                /***PAWN COLOR INSERTED IN SLOT***/
                if(as.getFamilyMembers() != null && as.getFamilyMembers().size() > 0)
                    pawnsInserted.add(as.getFamilyMembers().get(0)); //TODO: handle multiple family members
                if(as.getCardStored() != null)
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

            printTowerTypeColor(tower.getCardColor());

            printLine("");
            diceValues.forEach(dv -> printDiceValue(dv.toString()));
            printLine("");

            for(FamilyMember pawn : pawnsInserted)
                if(pawn != null)
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
    }

    /**
     * Shows the excommunicationCards set at game startup
     */
    private void showExcommunicationCards() {
        excommunicationCards.forEach(ex -> printFormat("Period %1$d, Penalty %2$s\n", ex.getPeriod(),ex.getPenalty().getClass().getSimpleName()));
    }

    /**
     * Shows all infos about each players in the game
     */
    private void showPlayersInfo() {
        CLIStuff.printToConsole.println("Here are the infos about all players in game");
        players.forEach(p -> {

            /**Leader the player has activated**/
            printPlayer(p.getPawnColor(),p.getPlayerName());
            p.getActivatedLeaderCards().forEach(c -> printFormat("Activated leaders: %1$s ", c.getName()));

            /**Resources the player has**/
            printLine("\nResources he has:");
            Resources res = p.getResources();
            for(ResourceType t : ResourceType.values())
                printFormat("%1$s : %2$s", t.toString(), res.getResourceByType(t).toString());

            /**Resources the player has**/
            p.getExcommunicationCards().forEach(e -> printFormat("%1$s : %2$s", e.getPeriod(),
                                                e.getPenalty().getClass().getSimpleName()));

            /**The cards the players has bought during the game**/
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

    /**
     * Shows the values of the 3 dices in this round
     */
    private void showDiceValues() {
        dices.forEach(d -> printFormat("%1$s value: %2$d ", d.getColor().toString(), d.getValue()));
    }

    /**
     *Inform the player that his turn has ended (for timeout or his choice)
     */
    @Override
    public void endTurn() {
        CLIStuff.printYellow("Your turn has ended\n");
    }

    /**
     * Inform the player that the server is not more reachable
     */
    @Override
    public void disconnectionWarning() {
        CLIStuff.printError("You are not connected to the game\n");
    }

    /**
     * Shows multiple kind of info about players
     * @param infoType information about a player {@link GameInformationType}
     * @param playerName the associated player
     * @param playerColor the color associated to the player to whom the info concerns
     */
    @Override
    public void informInGamePlayers(GameInformationType infoType, String playerName, PawnColor playerColor) {
        printPlayerName(playerName + "" + infoType.toString() +"\n", playerColor);
    }

    /**
     * @param bonusTiles that the players has the opportunity to choose one from
     * @return the bonus tile the player wants to have during the game
     */
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
                LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
                printError(INCORRECT_INPUT);
            }
        } while(!validUserInput);

        return bonusTileSelected;
    }

    /**
     * @param leaderCards that the players has the opportunity to choose one from
     * @return the leader the player wants to have as one of his 4 leaders
     */
    @Override
    public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) {
        String input;
        Integer leaderSelected = 0;
        Boolean validUserInput = false;

        printToConsole.println("Choose the leader you desire to have at your service");

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
            catch (IncorrectInputException | NumberFormatException e) {
                LOGGER.log(Level.INFO, getClass().getSimpleName(), e);
                printError(INCORRECT_INPUT);
            }
        } while(!validUserInput);

        return leaderSelected;
    }

    /**
     * Store the info from the server about this game object
     */
    @Override
    public void loadMapTerritoriesToVictoryPoints(Map<Integer, Integer> mapTerritoriesToVictoryPoints) {
        this.mapTerritoriesToVictoryPoints = mapTerritoriesToVictoryPoints;
    }

    @Override
    public void loadMapMilitaryPointsForTerritories(Map<Integer, Integer> mapMilitaryPointsForTerritories) {
        mapMilitaryPointsForTerritories = mapMilitaryPointsForTerritories;

    }

    /**
     * Store the info from the server about this game object
     */
    @Override
    public void loadMapCharactersToVictoryPoints(Map<Integer, Integer> mapCharactersToVictoryPoints) {
        this.mapCharactersToVictoryPoints = mapCharactersToVictoryPoints;
    }

    /**
     * Store the info from the server about this game object
     */
    @Override
    public void loadFaithPath(Map<Integer, Integer> faithPath) {
        this.faithPath = faithPath;
    }

    /**
     * Presents to the player the association between # of cards and victory points
     */
    @Override
    public void showMapCharactersToVictoryPoints() {
        printToConsole.println("Here is the mapping between number of characters owned and victory points provided at endGame");
        mapTerritoriesToVictoryPoints.forEach((pos, points) -> printToConsole.println(pos + ":" + points));
    }

    /**
     * Presents to the player the association between # of cards and victory points
     */
    @Override
    public void showMapTerritoriesToVictoryPoints() {
        printToConsole.println("Here is the mapping between number of territories owned and victory points provided at endGame");
        mapCharactersToVictoryPoints.forEach((pos, points) -> printToConsole.println(pos + ":" + points));
    }

    /**
     * Presents to the player the state of the FaithPath
     */
    @Override
    public void showFaithPath() {
        printToConsole.println("Here is the mapping between the position on the faithPath and victory points provided at Church Support");
        faithPath.forEach((pos, points) -> printToConsole.println(pos + ":" + points));
    }

    @Override
    public void showMilitaryPointsForTerritories() {
        printToConsole.println("Here is the mapping between the number of territories and the military points required to get another territory card");
        mapMilitaryPointsForTerritories.forEach((pos, points) -> printToConsole.println(pos + ":" + points));
    }

    /**
     * Presents to the player the state of the ProductionArea
     */
    private void showProductionArea() {
        printToConsole.println("Production Area single slot:");
        List<ActionSlot> tempSlots = new ArrayList<>();
        tempSlots.add(productionArea.getSingleSlot());
        printSlots(tempSlots);
        printToConsole.println("Production Area advanced slots:");
        printSlots(productionArea.getAdvancedSlots());
    }

    /**
     * Presents to the player the state of the HarvestArea
     */
    private void showHarvestArea() {
        printToConsole.println("Harvest Area single slot:");
        List<ActionSlot> tempSlots = new ArrayList<>();
        tempSlots.add(harvestArea.getSingleSlot());
        printSlots(tempSlots);
        printToConsole.println("Harvest Area advanced slots:");
        printSlots(harvestArea.getAdvancedSlots());
    }

    public static void main (String[] args) {
        Configurator.loadConfigs();
        Market market = Configurator.getMarket();
        CLI cli = new CLI();
        cli.market = market;
        cli.towers = Configurator.getTowers();
        try {
            //cli.towers.get(1).getTowerSlots().get(0).insertFamilyMember(new FamilyMember(PawnColor.RED, ORANGE));
            /*cli.market.insertFamilyMember(0, new FamilyMember(PawnColor.BLUE, DiceColor.ORANGE));
            cli.market.insertFamilyMember(1, new FamilyMember(PawnColor.GREEN, DiceColor.BLACK));
            cli.market.insertFamilyMember(2, new FamilyMember(PawnColor.RED, DiceColor.WHITE));
            cli.market.insertFamilyMember(3, new FamilyMember(PawnColor.YELLOW, DiceColor.ORANGE));*/
        } catch (Exception e) {e.printStackTrace(); }
        cli.updateProductionArea(Configurator.getProductionArea());
        cli.marketSlotSelection();
        cli.printTowers();
        List<Player> players = new ArrayList<>();
        Player giacomo = new Player("giacomo", PawnColor.BLUE, new PersonalBoard());
        giacomo.addResources(new Resources(4,5,1,2));
        Player antonio = new Player("antonio", PawnColor.RED, new PersonalBoard());
        players.add(giacomo); players.add(antonio);
        antonio.addResources(new Resources(4,5,1,2, 7,4 ,9));
    }
}