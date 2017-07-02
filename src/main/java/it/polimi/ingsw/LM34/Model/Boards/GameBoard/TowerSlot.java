package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import java.io.Serializable;

//general slot of the tower
public class TowerSlot extends ActionSlot implements Serializable {
    private AbstractDevelopmentCard cardStored;

    public TowerSlot(Boolean isSingle, Integer diceValueRequired, ResourcesBonus reward) {
        super(isSingle, diceValueRequired, reward);
    }

    public void setCardStored (AbstractDevelopmentCard card) {
        this.cardStored = card;
    }

    public AbstractDevelopmentCard getCardStored () {
        return this.cardStored;
    }

    public Boolean hasCard() {
        return (this.cardStored != null);
    }

    public void sweepTowerSlot() {
        super.sweep();
        this.cardStored = null;
    }
}


