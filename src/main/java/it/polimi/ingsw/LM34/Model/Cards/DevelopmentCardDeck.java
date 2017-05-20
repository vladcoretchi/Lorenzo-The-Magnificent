package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by GiulioComi on 20/05/2017.
 */
public class DevelopmentCardDeck<T extends AbstractDevelopmentCard> implements  Iterable<T> {

    //TODO: get deck loaded from Configurator at the preparation of the game
    private ArrayList<T> developmentDeck;

    public DevelopmentCardDeck(ArrayList<T> developmentCards) {
        this.developmentDeck = developmentCards;
    }

    public DevelopmentCardDeck() {
        this.developmentDeck = null;
    }

    @Override
    public Iterator<T> iterator() {
        return developmentDeck.iterator();
    }

    public void prepareDevelopmentCard() {

        Collections.shuffle(developmentDeck);
        orderDevelopmentCardByPeriod();
    }


    public void orderDevelopmentCardByPeriod() {
        ArrayList<T> temp = new ArrayList<>();

        for (Integer period = 1; period <= Configurator.TOTAL_PERIODS; period++)
            for(T card : developmentDeck)
                if (card.getPeriod() == period)
                    temp.add(card);

        developmentDeck = temp;
    }

}
