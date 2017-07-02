package it.polimi.ingsw.LM34.Model.Boards.GameBoard;

import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;

import java.io.Serializable;

//general slot of the tower
public class TowerSlot extends ActionSlot implements Serializable {

    private AbstractDevelopmentCard cardStored;

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

    public boolean hasCard() {
        return (this.cardStored != null);
    }

    public void sweepTowerSlot() {
        super.sweep();
        this.cardStored = null;
    }

    public void insertFamilyMember(FamilyMember fm) throws OccupiedSlotException {
        if(familyMember == null)
            this.familyMember = fm;
        else
            throw new OccupiedSlotException();
    }
}


