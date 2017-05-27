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
        //TODO: implement what player can do here and modify the model in this controller class
        //let the player choice if they wants to be excommunicated and assigned the negative effect to them
        //TODO: if players get excommunicated assign to him and activate the excommunication card

        //TODO:for each player that satisfied notify his activated observe
        setChanged(); //trigger sisto IV if is an observer
        notifyObservers();

        //turnContext.interactWithPlayer();
    }

    @Override
    public ContextType getType() {
        return ContextType.CURCH_REPORT_CONTEXT;
    }


    public void addExcommunicationCard(ExcommunicationCard card) {
        this.excommunicationCards.add(card);
    }
}


