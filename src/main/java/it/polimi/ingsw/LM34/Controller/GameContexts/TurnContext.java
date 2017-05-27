package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class TurnContext extends AbstractGameContext {
    ArrayList<AbstractGameContext> contexts;

    /**
     Constructor called only at the game setup
     */
    public TurnContext(ArrayList<AbstractGameContext> contexts) {
        this.contexts = contexts;
    }
        private GameManager gameManager;
    /**
     * @param player about to begin his turn
     *Reactivate all observers that are of the player that is going to play
     *NOTE: OncePerRound observers are excluded
     */
    public void initContext(Player player) {

        /*To make the player skip his turn*/
        setChanged();
        notifyObservers(this); //for SkipTurn observer


        ArrayList<AbstractEffect> observers = player.getObservers();
        for (AbstractEffect observer : observers)
            if (!observer.isOncePerRound())
                observer.subscribeObserverToContext(contexts);

            notifyObservers(player); //for PerRoundLeaderReward

            interactWithPlayer(player);
            //TODO: start timeout


    }

    @Override
    public void interactWithPlayer(Player player) {
        //TODO: let the player visit where he pleases to go
        //switchto che vuole
    } //sulla scelta dell'utente per farlo entrare nel contes

    public void endContext(Player player) {
        //TODO:in this context deactivate all observers of the player that has finished his turn
        player.unSubscribeObservers();
        gameManager.nextTurn();
    }
    
    @Override
    public ContextType getType() {
        return ContextType.TURN_CONTEXT;
    }

    /*public void endContext() {
        //void
    }*/

    /**
     * @param gameManager passed as parameter at the beginning of the game
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

}
