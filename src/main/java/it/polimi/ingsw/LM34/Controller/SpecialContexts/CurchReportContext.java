package it.polimi.ingsw.LM34.Controller.SpecialContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.FAITH_POINTS;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext  extends AbstractGameContext {
    private ArrayList<ExcommunicationCard> excommunicationCards; //added by the GameManager at game startup



    public CurchReportContext() {}


    public void interactWithPlayer(ArrayList<Player> players) {
        //let the player choice if they wants to be excommunicated and assigned the negative effect to them
        players.forEach(player -> checkEnoughFaithPoints(player, player.getResources().getResourceByType(FAITH_POINTS)));




        setChanged(); notifyObservers();  /*trigger sisto IV if is an observer*/
        //TODO: for each player, addVcitoryPointsFromFaithPath...
        for(Player player : players) {
            player.getResources().getResourceByType(FAITH_POINTS);
            //if (player.getExcommunicationCards().get(period) != null) {
                //player.addResources(new Resources(0, , 0));
            //}
        }
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

    @Override
    public ContextType getType() {
        return ContextType.CURCH_REPORT_CONTEXT;
    }

    /**
     * @param card choosed at game startup as a penalty card (1 per period)
     */
    public void addExcommunicationCard(ExcommunicationCard card) {
        excommunicationCards.add(card);
    }
}


