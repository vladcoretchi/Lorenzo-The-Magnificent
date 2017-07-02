package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tower extends GameSpace implements Serializable {
    private DevelopmentCardColor cardColor;
    private List<TowerSlot> slots;

    public Tower (DevelopmentCardColor cardColor, List<TowerSlot> slotsToLoad) {
        this.cardColor = cardColor;
        slots = slotsToLoad;
    }

    /**
     * @return the type of development cards that are stored in this tower
     */
    public DevelopmentCardColor getCardColor() {return cardColor;}

    public void addCard (AbstractDevelopmentCard card) {
        for (int i = 0; i < this.slots.size(); i++)
            if (!this.slots.get(i).hasCard()) {
                this.slots.get(i).setCardStored(card);
                return;
            }
    }

    //call to check if a column has already a familyMember inside so that a 3 coins penalty is activated
    public boolean isTowerEmpty() {
        for (int i = 0; i < this.slots.size(); i++)
            if(!this.slots.get(i).isEmpty())
                return false;

        return true;
    }

    //called at the end of each turn
    @Override
    public void sweep() {
        this.slots.forEach(TowerSlot::sweepTowerSlot);
    }

    public DevelopmentCardColor getDevelopmentTypeStored() {
        return this.cardColor;
    }

    public List<AbstractDevelopmentCard> getCardsStored() {
        List<AbstractDevelopmentCard> cardsStoredInTower = new ArrayList<>();
        this.slots.forEach(slot -> {
            if(slot.getCardStored() != null)
                cardsStoredInTower.add(slot.getCardStored());
        });
        return cardsStoredInTower;
    }

    public List<TowerSlot> getTowerSlots() {
        return this.slots;
    }
}