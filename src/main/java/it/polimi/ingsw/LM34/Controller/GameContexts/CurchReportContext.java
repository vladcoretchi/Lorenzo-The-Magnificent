package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class CurchReportContext  extends AbstractGameContext {
    private ArrayList<ExcommunicationCard> excommunicationCards; //added by the GameManager at game startup


    public void interactWithPlayer(ArrayList<Player> players) {
        //let the player choice if they wants to be excommunicated and assigned the negative effect to them
        //TODO: if players get excommunicated assign to him and activate the excommunication card
            //card.applyEffect(player)
            //player.addExcommunicationCard(card);
        //TODO:for each player that satisfied notify his activated observer
        /*trigger sisto IV if is an observer*/
        setChanged(); notifyObservers();
        //TODO: for each player, addVcitoryPointsFromFaithPath...
    }

    @Override
    public ContextType getType() {
        return ContextType.CURCH_REPORT_CONTEXT;
    }

    public void addExcommunicationCard(ExcommunicationCard card) {
        excommunicationCards.add(card);
    }
}


