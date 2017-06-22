package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import java.io.Serializable;

/**
 * Created by Julius on 04/05/2017.
 */

//general slot of the tower
public class TowerSlot extends ActionSlot implements Serializable {

    private AbstractDevelopmentCard cardStored;
    private Integer level;


    public TowerSlot(Boolean isSingle, Integer diceValueRequired, ResourcesBonus reward) {
        singlePawnSlot = isSingle;
        diceValue = diceValueRequired;
        resources = reward;
    }

    public void setCardStored (AbstractDevelopmentCard card) {
        this.cardStored = card;
    }

    public AbstractDevelopmentCard getCardStored () {
        return this.cardStored;
    }

    @Override
    public boolean isEmpty() {
        return (this.cardStored == null); //card already taken by a player
    }

    public void sweepTowerSlot() {
        super.sweep();
        this.cardStored = null;
    }

    public Integer getLevel() {
        return level;
    }

}


