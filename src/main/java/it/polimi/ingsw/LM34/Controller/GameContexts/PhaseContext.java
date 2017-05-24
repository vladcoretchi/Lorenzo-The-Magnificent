package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class PhaseContext extends AbstractGameContext {
    ArrayList<AbstractGameContext> contexts;
    private Boolean skipTurn; //excommunication card malus

    //Constructor called only at the game setup
    public PhaseContext(ArrayList<AbstractGameContext> contexts) {
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

        if (!skipTurn) {
            ArrayList<AbstractEffect> observers = player.getObservers();
            for (AbstractEffect observer : observers)
                if (!observer.isOncePerRound())
                    observer.subscribeObserverToContext(contexts);

            notifyObservers(player); //for PerRoundLeaderReward

            interactWithPlayer(player);
            //TODO: start timeout
        } else {
            endContext();
            skipTurn = false;
        }
    }

    public void interactWithPlayer(Player player) {
        //TODO: lascia scegliere al giocatore dove andare
        //switch sulla scelta dell'utente per farlo entrare nel contesto che vuole
    }
    public void endContext(Player player) {
        //TODO:in this context deactivate all observers of the player that has finished his turn
        player.unSubscribeObservers();
        gameManager.nextPhase();
    }

    @Override
    public void initContext() {/*not used*/}

    @Override
    public ContextType getType() {
        return ContextType.PHASE_CONTEXT;
    }

    @Override
    public void endContext() {
        //TODO
    }

    public void setSkipTurn(Boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
