package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DevelopmentCardDeck<T extends AbstractDevelopmentCard> implements Iterable<T> {
    private List<T> developmentDeck = new ArrayList<>();
    private DevelopmentCardColor cardColor;

    public DevelopmentCardDeck(List<T> cardList) {
        this.developmentDeck = cardList;
        this.cardColor = cardList.get(0).getColor();
    }

    @Override
    public Iterator<T> iterator() {
        return developmentDeck.iterator();
    }

    public DevelopmentCardColor getCardColor() {
        return this.cardColor;
    }
}
