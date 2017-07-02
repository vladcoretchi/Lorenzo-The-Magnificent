package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;

import java.io.Serializable;

/**
 * Slot of a Tower
 */
public class TowerSlot extends ActionSlot implements Serializable {
    /**
     * Card associated to the slot
     */
    private AbstractDevelopmentCard cardStored;

    public TowerSlot(Boolean isSingle, Integer diceValueRequired, ResourcesBonus reward) {
        super(isSingle, diceValueRequired, reward);
    }

    /**
     * Store in the slot a card
     * @param card the card to place in this slot at the new game round
     */
    public void setCardStored (AbstractDevelopmentCard card) {
        this.cardStored = card;
    }

    public AbstractDevelopmentCard getCardStored () {
        return this.cardStored;
    }

    public Boolean hasCard() {
        return (this.cardStored != null);
    }

    /**
     * Free at the end of a round the card stored and the pawns placed in the slot ({@link ActionSlot}
     */
    public void sweepTowerSlot() {
        super.sweep();
        this.cardStored = null;
    }
}


