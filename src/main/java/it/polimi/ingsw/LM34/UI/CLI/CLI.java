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
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
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
import it.polimi.ingsw.LM34.Utils.Utilities;
import it.polimi.ingsw.LM34.Utils.Validator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.logging.Level;

import static it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts.*;
import static it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor.MULTICOLOR;
import static it.polimi.ingsw.LM34.UI.CLI.CLIStuff.*;
import static it.polimi.ingsw.LM34.UI.UIConnectionInfo.*;
import static it.polimi.ingsw.LM34.Utils.Utilities.LOGGER;

/**
 * this class implements in console mode all method specified by {@link UIInterface}
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
        selectableContexts = Arrays.asList(PlayerSelectableContexts.values());
    }
    private Integer selectionMenu(List<?> data, Optional<String> backString, Optional<String> message, Optional<String> errorMessage) {
        backString.ifPresent(str -> printFormat("%1$d) %2$s%n", 0, str));
        for (int i = 0; i < data.size(); i++)
            printFormat("%1$d) %2$s%n", i + 1, data.get(i).toString());

        message.ifPresent(CLIStuff::printLine);
        Integer selectedValue;
        try {
            selectedValue = Integer.parseInt(readUserInput.nextLine());
            if(--selectedValue >= 0)
                Validator.checkValidity(selectedValue, data);
        }
        catch (IncorrectInputException | NumberFormatException ex) {
            LOGGER.log(Level.FINEST, ex.getMessage(), ex);
            errorMessage.ifPresent(CLIStuff::printError);
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
     * Let the player choose a leader between the
     * @param activatedLeadersByOtherPlayers that other players have activated during the game
     * @return the selected index of the leader to copy
     */
    @Override
    public Integer leaderCardCopy(List<LeaderCard> activatedLeadersByOtherPlayers) {
       return leaderCardSelectionPhase(activatedLeadersByOtherPlayers);
    }

    /**
     * Show the game results and declare the winner
     * @param players in game
     */
    @Override
    public void endGame(List<Player> players) {
        Integer maxVictoryPointsScored = 0;
        Integer playerPoints;
        String winnerName = "";

        /*Shows the points scored by all players*/
        players.forEach(player -> {
                    printPlayerName(player.getPlayerName(), player.getPawnColor());
                    printFormat(" has scored %1$s victory points%n", player.getResources().getResourceByType(ResourceType.VICTORY_POINTS));
                }
        );

        /*And now declare the winner*/
        for(Player player : players) {
            playerPoints = player.getResources().getResourceByType(ResourceType.VICTORY_POINTS);
            if (playerPoints > maxVictoryPointsScored) {
                maxVictoryPointsScored = playerPoints;
                winnerName = player.getPlayerName();
            }
        }
        printFormat("And... THE WINNER IS %1$s%n", winnerName);
    }

    @Override
    public PlayerAction turnMainAction(Optional<Exception> lastActionValid) {
        if (!lastActionValid.isPresent())
            showPlayersInfo();
        else
            printError(lastActionValid.get().getMessage());

        Optional<PlayerSelectableContexts> context = contextSelection(this.selectableContexts);
        if(!context.isPresent())
            return new PlayerAction(null, null);

        switch (context.get()) {
            case TOWERS_CONTEXT:
                return new PlayerAction(TOWERS_CONTEXT, towerSlotSelection());
            case COUNCIL_PALACE_CONTEXT:
                councilPalaceSelection();
                return new PlayerAction(COUNCIL_PALACE_CONTEXT, null);
            case HARVEST_AREA_CONTEXT:
                return new PlayerAction(HARVEST_AREA_CONTEXT, workingAreaSlotSelection(WorkingAreaType.HARVEST));
            case PRODUCTION_AREA_CONTEXT:
                return new PlayerAction(PRODUCTION_AREA_CONTEXT, workingAreaSlotSelection(WorkingAreaType.PRODUCTION));
            case MARKET_AREA_CONTEXT:
                return new PlayerAction(MARKET_AREA_CONTEXT, marketSlotSelection());
            case LEADER_CARDS_CONTEXT:
                return new PlayerAction(LEADER_CARDS_CONTEXT, null);
            default:
                return turnMainAction(Optional.of(new IncorrectInputException()));
        }
    }

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
        List<String> familyMembersString = new ArrayList<>();
        familyMembers.forEach(fm -> familyMembersString.add(String.format("%1$s (Value: %2$d)", fm.getDiceColorAssociated(), fm.getValue())));
        return selectionMenu(familyMembersString,
                Optional.empty(),
                Optional.of("Select the family member to use"),
                Optional.of("Select a valid family member"));
    }

    /**
     * this method will be called when the console will ask user what kind of connection technology he wants to use, Rmi or Socket
     * @return the user's choice
     */
    private NetworkType connectionTypeSelection() {
        List<NetworkType> networkTypes = Arrays.asList(NetworkType.values());
        Integer selection = selectionMenu(networkTypes,
                Optional.empty(),
                Optional.of("Select the network connection type"),
                Optional.of(INVALID_SELECTION));

        return networkTypes.get(selection);
    }

    /**
     * this method will be called when the user will have to select the
     * {@link it.polimi.ingsw.LM34.Controller.AbstractGameContext} to enter
     */
    private Optional<PlayerSelectableContexts> contextSelection(List<PlayerSelectableContexts> allContext)  {
        Integer selection = selectionMenu(allContext,
                Optional.of("End turn"),
                Optional.of("Select the context to enter or 0 to end this turn"),
                Optional.of(INVALID_SELECTION));

        if(selection < 0)
            return Optional.empty();

        return Optional.of(allContext.get(selection));
    }

    /**
     * This method will be called when player will want to enter into the {@link Market}
     */
    private Integer marketSlotSelection() {
        /*Show the market*/
        printSlots(market.getActionSlots());
        printToConsole.println("In which slot do you want to place one of your pawn?%n");

        return checkInput(market.getActionSlots());
    }

    /**
     * Provides the player the ability to select one of the two {@link WorkingAreaType} to enter
     */
    private Integer workingAreaSlotSelection(WorkingAreaType workingArea) {
        Integer selectedSlot;

        showProductionArea();
        showHarvestArea();

       CLIStuff.printToConsole.format("In which slot of the %1$s area do you want to enter?%n", workingArea.toString());

       if(workingArea.getWorkingAreaType().equalsIgnoreCase(productionArea.toString()))
           selectedSlot = checkInput(productionArea.getActionSlots());
       else
           selectedSlot = checkInput(harvestArea.getActionSlots());

        return selectedSlot;
    }

    /**
     * this method will be called when player will enter into Council Palace
     */
    private void councilPalaceSelection() {
        /*SHOW COUNCIL PALACE*/
        printCouncilPalace();

        printToConsole.println("You selected to place one of your pawns in the Council Palace. Press a key to continue");
        readUserInput.next();
    }

    /**
     * Shows the CouncilPalace state
     */
    private void printCouncilPalace() {
        ActionSlot actionSlot = palace.getActionSlot();
        List<FamilyMember> occupyingPawns = palace.getActionSlot().getFamilyMembers();

        printFormat("Council Palace resources: %1$s CouncilPrivileges: %2$s%n", actionSlot.getResourcesReward().getResources().getResources(),
                                        actionSlot.getResourcesReward().getCouncilPrivilege());

        occupyingPawns.forEach(p -> printPawn(p.getFamilyMemberColor(), p.getDiceColorAssociated()));
    }

    /**
     * Provides a graphic knowledge of the rewards each slots of a particular {@link GameSpace}
     * @param slots
     */
    private void printSlots(List<ActionSlot> slots) {
        List<List<Pair<String, Integer>>> slotsResources = new ArrayList<>();
        List<FamilyMember> pawnsInserted = new ArrayList<>();
        List<Integer> diceValues = new ArrayList<>();

        slots.forEach((ActionSlot as) -> {
            diceValues.add(as.getDiceValue());
            /*PAWN COLOR INSERTED IN SLOT*/
            if(!as.getFamilyMembers().isEmpty())
                pawnsInserted.add(as.getFamilyMembers().get(0));
            else
                pawnsInserted.add(null);
            List<Pair<String, Integer>> res = new ArrayList<>();
            Resources resources = as.getResourcesReward().getResources();
            resources.getResources().forEach((ResourceType rt, Integer val) -> res.add(new ImmutablePair<>(rt.toString(), val)));
            if (as.getResourcesReward().getCouncilPrivilege() > 0)
                res.add(new ImmutablePair<>("COUNCIL PRIVILEGES", as.getResourcesReward().getCouncilPrivilege()));
            slotsResources.add(res);
        });

        Integer resourcesLines = 0;
        for (List sr : slotsResources) {
            if (sr.size() > resourcesLines)
                resourcesLines = sr.size();
        }

        diceValues.forEach(diceValue -> printDiceValue(diceValue.toString()));
        printLine("");

        pawnsInserted.forEach(pawn -> {
            if(pawn != null)
                printPawn(pawn.getFamilyMemberColor(), pawn.getDiceColorAssociated());
            else
                printFormat("            ");
        });

        printFormat("%n %1$s %n", String.join(" ", Collections.nCopies(slotsResources.size(), "__________")));
        printFormat("|%1$s|%n", String.join("|", Collections.nCopies(slotsResources.size(), "          ")));

        for (int i = 0; i < resourcesLines; i++) {
            String s;

            /*print resource value and get resources' splitted string*/
            s = "|";
            List<String[]> splittedStrings = new ArrayList<>();
            Integer resourceNameLines = 0;
            for (List<Pair<String,Integer>> sr : slotsResources) {
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
            printFormat("|%1$s|%n", String.join("|", Collections.nCopies(slotsResources.size(), "          ")));
        }

        printFormat("|%1$s|%n%n", String.join("|", Collections.nCopies(slotsResources.size(), "__________")));
    }

    /**
     * this method will be called when the user will choice which tower wants to make his action and in which tower's floor
     * @param towerNumber how many towers contains a game board
     * @param towerFloor how many floors contains a tower
     */
    private Pair<DevelopmentCardColor, Integer> towerSlotSelection() {
        DevelopmentCardColor towerType;
        Integer floor;

        /*SHOW THE TOWERS*/
        printTowers();

        /*Let the player choose the tower based on DevelopmentCardColor*/
        List<String> colors = new ArrayList<>();
        for(DevelopmentCardColor color : DevelopmentCardColor.values())
            if(color != MULTICOLOR)
                colors.add(color.name());
        towerType = DevelopmentCardColor.valueOf(
                colors.get(selectionMenu(colors,
                        Optional.empty(),
                        Optional.of("In which tower do you wish to bring your family member?"),
                        Optional.of(INVALID_SELECTION))));

        /*Let the player choose the level of the selected tower*/
        printToConsole.println("In which tower's floor do you want to place your pawn?\n");
        floor = readUserInput.nextInt();

        return new ImmutablePair<>(towerType, --floor);
    }

    /**
     * this method will be called when user need to use some servants to improve his pawn's value
     * @param servantsAvailable user's available servants
     * @param minimumServantsRequested minimum number of servants to complete action
     * @return how many servants user has decided to use
     */
    @Override
    public Integer servantsSelection(Integer servantsAvailable, Integer minimumServantsRequested) {
        Boolean validUserInput = false;
        Integer input = 0;

        if(minimumServantsRequested > 0)
            printFormat("To complete this action, you need at least %1$d servants (you have %2$d servants)%n", minimumServantsRequested, servantsAvailable);
        else
            printFormat("You can use servants to increment your family member's value (%1$s available)%n", servantsAvailable);
        printToConsole.println("How many servants do you wish to use?\n");

        do {
            try {
                input = readUserInput.nextInt();
                Validator.checkValidity(input, minimumServantsRequested, servantsAvailable);
                validUserInput = true;
            }
            catch (IncorrectInputException e) {
                LOGGER.log(Level.FINEST, e.getMessage(), e);
                printError(INCORRECT_INPUT);
            }
        } while(!validUserInput);

        return input;
    }

    /**
     * @param choices available about resource exchange bonuses
     * @return the reward opted by the player
     */
    @Override
    public Integer resourceExchangeSelection(List<Pair<Resources, ResourcesBonus>> choices) {
        List<String> choicesList = new ArrayList<>();
        choices.forEach(c -> choicesList.add(String.format("Resources required: %1$s ---> Resources provided: %2$s, CouncilPrivileges provided: %3$d",
                c.getLeft().getResources().toString(), c.getRight().getResources().getResources().toString(), c.getRight().getCouncilPrivilege())));

        return selectionMenu(choicesList,
                Optional.of("Ignore bonus"),
                Optional.of("Select the resource exchange to apply"),
                Optional.of(INVALID_SELECTION));
    }


    /**
     * @param leadersOwned that the game consider the player to have available
     * @return the leader choosed and the action to perform on him
     */
    @Override
    public Pair<String, LeaderCardsAction> leaderCardSelection(List<LeaderCard> leadersOwned) {
        if(leadersOwned.isEmpty()) {
            printError("No leaders to choose from!");
            return null;
        }

        List<String> items = new ArrayList<>();
        items.add(LeaderCardsAction.PLAY.name());
        items.add(LeaderCardsAction.DISCARD.name());

        LeaderCardsAction selectedAction = LeaderCardsAction.valueOf(
                items.get(selectionMenu(items,
                        Optional.empty(),
                        Optional.of("Choose a leader card action"),
                        Optional.of(INVALID_SELECTION))));

        items = new ArrayList<>();
        for(LeaderCard card : leadersOwned)
            items.add(card.getName());

        String selectedLeader = items.get(selectionMenu(items,
                Optional.empty(),
                Optional.of("Choose the leader to apply the action to"),
                Optional.of(INVALID_SELECTION)));

        return new ImmutablePair<>(selectedLeader, selectedAction);
    }

    /**
     * The Curch Report decision asked from the game to the player
     * @return the decision of the player
     */
    @Override
    public Boolean churchSupport() {
        String input;
        printFormat("Do you want to Support the Church (yes/no)?%n");

        input = readUserInput.nextLine();

        if("yes".equalsIgnoreCase(input))
            return true;
        else if ("no".equalsIgnoreCase(input))
            return false;
        else {
            printError(INCORRECT_INPUT);
            return churchSupport();
        }
    }

    /**
     * This class is called for the {@link it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard}
     * that have as an alternative requirement option the payment of MILITARY_POINTS
     */
    @Override
    public Boolean alternativeRequirementsPayment() {
        String input;
        printFormat("Do you want to pay military points for this card (yes/no)?%n");

        input = readUserInput.nextLine();

        if("yes".equalsIgnoreCase(input))
            return true;
        else if("no".equalsIgnoreCase(input))
            return false;
        else {
            printError(INCORRECT_INPUT);
            return alternativeRequirementsPayment();
        }
    }

    /**
     * @param availableBonuses, set from the game
     * @return the choice made by the player among the options in input
     */
    @Override
    public Integer selectCouncilPrivilegeBonus(List<Resources> availableBonuses) {
        List<String> councilPrivileges = new ArrayList<>();
        availableBonuses.forEach(bonus -> councilPrivileges.add(bonus.getResources().toString()));

        return selectionMenu(councilPrivileges,
                Optional.empty(),
                Optional.of("Choose the reward you desire"),
                Optional.of(INVALID_SELECTION));
    }

    /**
     *Prints all the {@link Tower} and their {@link TowerSlot}s showing informations about cards stored
     * and the reward each slots provides
     */
    public void printTowers() {

        for(Tower tower : this.towers) {
            List<List<Pair<String,Integer>>> slotsResources = new ArrayList<>();
            List<Integer> diceValues = new ArrayList<>();
            List<FamilyMember> pawnsInserted = new ArrayList<>();
            List<String> cardNames = new ArrayList<>();
            tower.getTowerSlots().forEach((TowerSlot as) -> {
                List<Pair<String,Integer>> res = new ArrayList<>();
                /*DICE VALUE*/
                diceValues.add(as.getDiceValue());
                /*PAWN COLOR INSERTED IN SLOT*/
                if(!as.getFamilyMembers().isEmpty())
                    pawnsInserted.add(as.getFamilyMembers().get(0));
                else
                    pawnsInserted.add(null);
                if(as.getCardStored() != null)
                    cardNames.add(as.getCardStored().getName());
                else
                    cardNames.add("");

                Resources resources = as.getResourcesReward().getResources();
                resources.getResources().forEach((ResourceType rt, Integer val) -> res.add(new ImmutablePair<>(rt.toString(), val)));
                if (as.getResourcesReward().getCouncilPrivilege() > 0)
                    res.add(new ImmutablePair<>("COUNCIL PRIVILEGES", as.getResourcesReward().getCouncilPrivilege()));
                slotsResources.add(res);
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

            pawnsInserted.forEach(pawn -> {
                if(pawn != null)
                    printPawn(pawn.getFamilyMemberColor(), pawn.getDiceColorAssociated());
                else
                    printFormat("           ");
            });

            printLine("");
            printFormat("\"Remaining cards in the tower (in order): \"");
            cardNames.forEach(c -> {
                if("".equalsIgnoreCase(c))
                    printFormat(" ************,");
                else
                    printFormat(" %1$s, ", c);
            });

            printFormat("%n %1$s %n", String.join(" ", Collections.nCopies(slotsResources.size(), "__________")));
            printFormat("|%1$s|%n", String.join("|", Collections.nCopies(slotsResources.size(), "          ")));
            for (int i = 0; i < resourcesLines; i++) {
                String s;

            /*print resource value and get resources' splitted string*/
                s = "|";
                List<String[]> splittedStrings = new ArrayList<>();
                Integer resourceNameLines = 0;
                for (List<Pair<String,Integer>> sr : slotsResources) {
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
                printFormat("|%1$s|%n", String.join("|", Collections.nCopies(slotsResources.size(), "          ")));
            }

            printFormat("|%1$s|%n", String.join("|", Collections.nCopies(slotsResources.size(), "__________")));
          }
    }

    /**
     * Shows the {@link ExcommunicationCard}s that have been choosed at game startup
     */
    private void showExcommunicationCards() {
        excommunicationCards.forEach(ex -> printFormat("Period %1$d, Penalty %2$s%n", ex.getPeriod(),ex.getPenalty().getClass().getSimpleName()));
    }

    /**
     * Shows all infos about each players in the game
     */
    private void showPlayersInfo() {
        CLIStuff.printToConsole.println("Here are the information about the players in the game");
        players.forEach(p -> {

            /*Leader the player has activated*/
            printPlayer(p.getPawnColor(),p.getPlayerName());
            if(p.getActivatedLeaderCards() != null && p.getActivatedLeaderCards().isEmpty()) {
                printLine("Activated leaders: ");
                p.getActivatedLeaderCards().forEach(c -> printFormat("%1$s ", c.getName()));
            }

            /*Resources the player has*/
            printLine("%nResources:");
            Resources res = p.getResources();
            for(ResourceType t : ResourceType.values())
                printFormat("%1$s: %2$s%n", t.toString(), res.getResourceByType(t).toString());

            /*The ExcommunicationCards of the player*/
            printLine("%nPenalties:");
            p.getExcommunicationCards().forEach(e -> printFormat("%1$s: %2$s%n", e.getPeriod(),
                                                e.getPenalty().getClass().getSimpleName()));

            /*The AbstractDevelopmentCards the players has bought during the game*/
            printFormat("Personal Board of player %1$s%n", p.getPlayerName());
            for(DevelopmentCardColor color : DevelopmentCardColor.values()) {
                Optional<List<AbstractDevelopmentCard>> cards = p.getPersonalBoard().getDevelopmentCardsByType(color);
                cards.ifPresent(cardsList -> {
                    printTowerTypeColor(color);
                    cardsList.forEach(card -> printFormat("%1$s ", card.getName()));
                    printLine("");
                });
            }
        });
    }

    /**
     * Shows the values of the 3 {@link Dice}s in this round
     */
    private void showDiceValues() {
        dices.forEach(d -> printFormat("%1$s value: %2$d ", d.getColor().toString(), d.getValue()));
    }

    /**
     *Inform the player that his turn has ended (for timeout or his choice)
     */
    @Override
    public void endTurn() {
        CLIStuff.printYellow("Your turn has ended%n");
    }

    /**
     * Inform the player that the server is no more reachable
     */
    @Override
    public void disconnectionWarning() {
        CLIStuff.printError("You are not connected to the game%n");
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
        List<String> bonusTilesString = new ArrayList<>();
        bonusTiles.forEach(bt ->
            bonusTilesString.add(String.format("Harvest: %1$s Production: %2$s",
                    bt.getHarvestBonus().getResources().getResources().toString(),
                    bt.getProductionBonus().getResources().getResources().toString()))
        );

        return selectionMenu(bonusTilesString,
                Optional.empty(),
                Optional.of("Choose the bonus tile you desire to have"),
                Optional.of(INVALID_SELECTION));
    }

    /**
     * @param leaderCards that the players has the opportunity to choose one from
     * @return the leader the player wants to have as one of his 4 leaders
     */
    @Override
    public Integer leaderCardSelectionPhase(List<LeaderCard> leaderCards) {
        List<String> leaderCardsString = new ArrayList<>();
        leaderCards.forEach(lc -> leaderCardsString.add(lc.getName()));

        return selectionMenu(leaderCardsString,
                Optional.empty(),
                Optional.of("Choose the leader you desire to have at your service"),
                Optional.of("Invalid selection"));
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
        this.mapMilitaryPointsForTerritories = mapMilitaryPointsForTerritories;

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
        printYellow("Production Area single slot:");
        List<ActionSlot> tempSlots = new ArrayList<>();
        tempSlots.add(productionArea.getSingleSlot());
        printSlots(tempSlots);
        printYellow("Production Area advanced slots:\n\n");
        tempSlots.clear();
        tempSlots.add(productionArea.getAdvancedSlot());
        printSlots(tempSlots);
    }

    /**
     * Presents to the player the state of the HarvestArea
     */
    private void showHarvestArea() {
        printYellow("Harvest Area single slot:");
        List<ActionSlot> tempSlots = new ArrayList<>();
        tempSlots.add(harvestArea.getSingleSlot());
        printSlots(tempSlots);
        printYellow("Harvest Area advanced slots:\n\n");
        tempSlots.clear();
        tempSlots.add(harvestArea.getAdvancedSlot());
        printSlots(tempSlots);
    }

    /**
     * Whenever a CLI method needs to get a valid Integer within a certain range from the player,
     * this utility is invoked
     * @param availableList, list of elements of the game a player can choose among
     * @return the selected option
     */
    private Integer checkInput(List<?> availableList) {
        Boolean validUserInput = false;
        Integer input = 0;
        do {
            try {
                input = readUserInput.nextInt();
                Validator.checkValidity(--input, availableList);
                validUserInput = true;
            }
            catch (IncorrectInputException | InputMismatchException e) {
                LOGGER.log(Level.FINEST, e.getMessage(), e);
                printError(INCORRECT_INPUT);
            }
        } while(!validUserInput);

        return input;
    }
}