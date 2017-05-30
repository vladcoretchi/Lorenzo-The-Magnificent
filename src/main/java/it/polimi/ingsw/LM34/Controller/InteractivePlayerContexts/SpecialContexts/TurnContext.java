package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.MarketAreaContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Controller.PlayerSelectionableContexts;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectDataException;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.MarketBan;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.PerRoundLeaderReward;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Network.GameRoom;
import it.polimi.ingsw.LM34.Utils.Configurator;
import it.polimi.ingsw.LM34.Utils.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TurnContext extends AbstractGameContext {
    List<PlayerSelectionableContexts> accessibleContexts = new ArrayList<>();




    /**
     Constructor called only at the game setup
     */


    public TurnContext() {
        contextType = ContextType.TURN_CONTEXT;
    }


    /**
     * @param player about to begin his turn
     *Reactivate all observers that are of the player that is going to play
     *NOTE: OncePerRound observers are excluded
     */
    public void initContext(Player player) {
        for(PlayerSelectionableContexts p : PlayerSelectionableContexts.values())
            accessibleContexts.add(p);
        /*To make the player skip his turn*/
        setChanged();
        notifyObservers(this); //for SkipTurn observer


        /*ArrayList<AbstractEffect> observers = player.getObservers();
        for (AbstractEffect observer : observers)
            if (!observer.isOncePerRound())
                observer.subscribeObserverToContext(contexts);
            notifyObservers(player); //for PerRoundLeaderReward*/
            System.out.println("Now is player: "+player.getPawnColor()+" turn");
            interactWithPlayer(player);
            //TODO: start timeout

    }

    @Override
    public void interactWithPlayer(Player player) {
        Boolean correctRequest = false;

        setChanged();
        notifyObservers();

        /*Provide to the players all the info for the contexts of the game he can enter freely*/
        PlayerSelectionableContexts selectedContext = null;
        /*CONTEXT SIDE VALIDATION*/
        contextSelection(player);

    }

    /**
     * Deactivates all bonus observers of the player that has played
     */
    public void endContext() {
        //TODO: unsubscribe player observer at the end of the turn
        /*contexts.forEach((c) -> c.deleteObservers());*/
        gameManager.nextTurn();

    }




    public void contextSelection(Player player) {

        Integer selected = this.gameManager.getActivePlayerNetworkController().contextSelection(accessibleContexts);

        try {
            Validator.checkValidity(selected.toString(),accessibleContexts);
            PlayerSelectionableContexts selectedContext = accessibleContexts.get(selected);
            getContextByType(selectedContext).interactWithPlayer(player);
        }
        /*If input mismatch expected informations... the player is able to try again*/
        catch(IncorrectDataException ide){
                contextSelection(player);
         }
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
        AbstractGameContext context = gameManager.getContextByType(PlayerSelectionableContexts.HARVEST_AREA_CONTEXT);
        System.out.println(context.getType().toString());
        /*gameManager.setupGameContexts();
        TurnContext turnContext = (TurnContext) gameManager.getContextByType(ContextType.TURN_CONTEXT);
        turnContext.setGameManager(gameManager);
        turnContext.trial(player);*/

    }

    //TODO: a testing method
    private void trial(Player player) {
        PerRoundLeaderReward perRoundLeaderReward = new PerRoundLeaderReward();
        perRoundLeaderReward.applyEffect(this, player);
        MarketBan marketBan = new MarketBan();
        marketBan.applyEffect(this, player);
        //this.addObserver(marketBan);
        MarketAreaContext marketAreaContext = (MarketAreaContext) gameManager.getContextByType(ContextType.MARKET_AREA_CONTEXT);
        marketAreaContext.interactWithPlayer(player);

    }

}
