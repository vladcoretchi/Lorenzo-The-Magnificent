package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by GiulioComi on 20/05/2017.
 */
public class DevelopmentCardDeck<T extends AbstractDevelopmentCard> implements  Iterable<T> {

    //TODO: get deck loaded from Configurator at the preparation of the game
    private ArrayList<T> developmentDeck = new ArrayList<>();
    private DevelopmentCardColor cardColor;

    public DevelopmentCardDeck(ArrayList<T> developmentCards) {

        this.developmentDeck = developmentCards;
        //get the development card color type by extracting this info from the first card of the deck
        cardColor = developmentCards.get(0).getColor(); //TODO: refactor
    }

    public DevelopmentCardDeck() {
        this.developmentDeck = null;
    }

    @Override
    public Iterator<T> iterator() {
        return developmentDeck.iterator();
    }

    public void setupDevelopmentCardDeck() {

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

    public DevelopmentCardColor getCardColor() {
        return this.cardColor;
    }


}
