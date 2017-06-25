package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.LeaderCardsAction;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectableContexts;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Utils.Configurator;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TurnContext extends AbstractGameContext {
    List<PlayerSelectableContexts> accessibleContexts = new ArrayList<>();

    /**
     * Constructor called only at the game setup
     */
    public TurnContext() {
        this.contextType = ContextType.TURN_CONTEXT;
    }

    /**
     * @param player about to begin his turn
     * Reactivate all observers that are of the player that is going to play
     * NOTE: OncePerRound observers are excluded
     */
    public void initContext() {
        this.accessibleContexts.clear();
        for(PlayerSelectableContexts p : PlayerSelectableContexts.values())
            this.accessibleContexts.add(p);

        /* To make the player skip his turn */
        setChanged();
        notifyObservers(this); //for SkipTurn observer

        /*List<AbstractEffect> observers = this.getCurrentPlayer().getObservers();
        for (AbstractEffect observer : observers)
            if (!observer.isOncePerRound())
                observer.subscribeObserverToContext(contexts);
            notifyObservers(player); //for PerRoundLeaderReward*/
        System.out.println("Now is player: "+gameManager.getCurrentPlayer().getPawnColor()+" turn");
        interactWithPlayer();
    }

    @Override
    public void interactWithPlayer() {
        Boolean correctRequest = false;

        setChanged();
        notifyObservers();

        /* Provide to the players all the info for the contexts of the game he can enter freely */
        PlayerSelectableContexts selectedContext = null;

        Player currentPlayer = this.gameManager.getCurrentPlayer();

        this.gameManager.getPlayerNetworkController(currentPlayer).updateTowers(this.gameManager.getTowers());
        //this.gameManager.getPlayerNetworkController(currentPlayer).updateMarket(this.gameManager.getMarket());

        Pair<String, LeaderCardsAction> lc = this.gameManager.getPlayerNetworkController(currentPlayer).leaderCardSelection(Configurator.getLeaderCards(2));

        //this.gameManager.getPlayerNetworkController(currentPlayer).updateMarket(this.gameManager.getMarket());
        //this.gameManager.getPlayerNetworkController(currentPlayer).updateProductionArea(this.gameManager.getProductionArea());
        //this.gameManager.getPlayerNetworkController(currentPlayer).updateHarvestArea(this.gameManager.getHarvestArea());
    }

    /**
     * Deactivates all bonus observers of the player that has played
     */
    public void endContext() {
        //TODO: unsubscribe player observer at the end of the turn
        //gameManager.getContexts().forEach((c) -> c.deleteObservers());
        gameManager.nextTurn();

    }




    public void contextSelection(Player player) {

        /*Integer selected = this.gameManager.getActivePlayerNetworkController().contextSelection(accessibleContexts);

        try {
            Validator.checkValidity(selected.toString(), accessibleContexts);
            PlayerSelectableContexts selectedContext = accessibleContexts.get(selected);
            getContextByType(selectedContext).interactWithPlayer();
        }
        catch(IncorrectInputException ide){
            //If input mismatch expected informations... the player is able to try again
            contextSelection(player);
         }*/
    }



    /*Testing purpose main*/
    public static void main (String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player("cicoio", PawnColor.RED, new PersonalBoard());
        Configurator.loadConfigs();
        ArrayList<String> playersName = new ArrayList<>();
        playersName.add("pippo");
        GameManager gameManager = new GameManager(new GameRoom(),playersName);
        gameManager.setupGameContexts();
        AbstractGameContext context = gameManager.getContextByType(PlayerSelectableContexts.HARVEST_AREA_CONTEXT);
        System.out.println(context.getType().toString());
        /*gameManager.setupGameContexts();
        TurnContext turnContext = (TurnContext) gameManager.getContextByType(ContextType.TURN_CONTEXT);
        turnContext.setGameManager(gameManager);
        turnContext.trial(player);*/

    }
}
