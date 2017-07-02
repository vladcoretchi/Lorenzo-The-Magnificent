package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A data structure to store in an organized way the cards of the same development type
 * @param <T>, a {@link AbstractDevelopmentCard} type of card
 */
public class DevelopmentCardDeck<T extends AbstractDevelopmentCard> implements Iterable<T> {
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

    /**
     * @return {@link Iterator} for the Deck
     */
    @Override
    public Iterator<T> iterator() {
        return developmentDeck.iterator();
    }

    public DevelopmentCardColor getCardColor() {
        return this.cardColor;
    }
}
