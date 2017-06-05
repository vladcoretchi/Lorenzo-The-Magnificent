package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractableContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;

import java.util.ArrayList;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.CURCH_REPORT_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.RESOURCE_INCOME_CONTEXT;
import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.FAITH_POINTS;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext  extends AbstractGameContext {
    private ArrayList<ExcommunicationCard> excommunicationCards; //added by the GameManager at game startup



    public CurchReportContext() {
        contextType = CURCH_REPORT_CONTEXT;
        excommunicationCards = new ArrayList<>();
    }


    @Override
    public void interactWithPlayer(Player player) {
        //let the player choice if they wants to be excommunicated and assigned the negative effect to them
        checkEnoughFaithPoints(player, player.getResources().getResourceByType(FAITH_POINTS));
        //TODO: for players that have enough points ask YES or NO to be excommunicated
        Boolean choice = gameManager.getActivePlayerNetworkController().curchReportDecision();


        setChanged(); notifyObservers(player);  /*trigger sisto IV if is an observer*/


        //TODO: addVictoryPointsFromFaithPath based on faith track position
        Integer faithReward = player.getResources().getResourceByType(FAITH_POINTS);

        ResourcesBonus reward = new ResourcesBonus(new Resources(0,faithReward,0), 0);  /*Wrapper*/
        ((ResourceIncomeContext) gameManager.getContextByType(RESOURCE_INCOME_CONTEXT)).handleResources(player, reward);


    }

    /**
     *
     * @param resourceByType
     */
    private void checkEnoughFaithPoints(Player player, Integer faithPoints) {
        //TODO: faith points necessary depends on the # of period
        //if(faithPoints < Configurator.MIN_FAITHS_POINTS[GameManager.getPeriod()])
            //excommunicationCards.get(GameManager.getPeriod()).getPenalty().applyEffect(player);
    }


    /**
     * @param card choosed at game startup as a penalty card (1 per period)
     */
    public void addExcommunicationCard(ExcommunicationCard card) {
        excommunicationCards.add(card);
    }
}


