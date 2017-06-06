package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.ArrayList;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.CURCH_REPORT_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.FAITH_POINTS;
import static it.polimi.ingsw.LM34.Utils.Configurator.MIN_FAITHS_POINTS;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext  extends AbstractGameContext {
    private ArrayList<ExcommunicationCard> excommunicationCards; //added by the GameManager at game startup



    public CurchReportContext() {
        contextType = CURCH_REPORT_CONTEXT;
        excommunicationCards = new ArrayList<>();
    }



    public void interactWithPlayer(Player player) {
        //TODO: called by game manager that iterates on each player
        //let the player choice if they wants to be excommunicated and assigned the negative effect to them
        checkEnoughFaithPoints();
        //TODO: for players that have enough points ask YES or NO to be excommunicated
        Boolean choice = gameManager.getActivePlayerNetworkController().curchReportDecision();
        if(choice) {
            setChanged();
            notifyObservers(player);  /*trigger sisto IV if is an observer*/
        }


        //TODO: addVictoryPointsFromFaithPath based on faith track position
        Integer faithReward = player.getResources().getResourceByType(FAITH_POINTS);

        Resources reward = new Resources(0,faithReward,0);  /*Wrapper*/
        player.addResources(reward);

    }

    /**
     *
     * @param resourceByType
     */
    private void checkEnoughFaithPoints() {
        Player currentPlayer = gameManager.getCurrentPlayer();
        Integer faithPoints = currentPlayer.getResources().getResourceByType(FAITH_POINTS);
        //TODO: faith points necessary depends on the # of period

        /*Add to player excommunication card*/
        if(faithPoints < MIN_FAITHS_POINTS[gameManager.getPeriod()])
            excommunicationCards.get(gameManager.getPeriod()).getPenalty().applyEffect(this);
    }


    /**
     * @param card choosed at game startup as a penalty card (1 per period)
     */
    public void addExcommunicationCard(ExcommunicationCard card) {
        excommunicationCards.add(card);
    }

    @Override
    public void interactWithPlayer() {
        /*VOID*/
    }
}


