package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Utils.Configurator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DevelopmentCardDeck<T extends AbstractDevelopmentCard> implements  Iterable<T> {

    private List<T> developmentDeck = new ArrayList<>();
    private DevelopmentCardColor cardColor;

    public DevelopmentCardDeck(ArrayList<T> developmentCards) {
        this.developmentDeck = developmentCards;
    }

    public DevelopmentCardDeck(List<T> cardList) {
        this.developmentDeck = cardList;
        //get the development card color type by extracting this info from the first card of the deck
        this.cardColor = cardList.get(0).getColor();
    }


    @Override
    public Iterator<T> iterator() {
        return developmentDeck.iterator();
    }

    public void setupDevelopmentCardDeck() {
        //Collections.shuffle(developmentDeck);
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
