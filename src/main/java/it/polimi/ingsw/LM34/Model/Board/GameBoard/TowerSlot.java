package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by Julius on 04/05/2017.
 */

//general slot of the tower
public class TowerSlot extends ActionSlot {
    private Integer diceValueRequired;
    private FamilyMember familyMember;
    private AbstractDevelopmentCard cardStored;
    private Integer level;
    private Integer coloumn;

    public TowerSlot(Resources occupyingReward, Integer diceValueRequired, Integer level, Integer coloumn) {
        super(true,diceValueRequired, occupyingReward, 0 ); //store in the actionslot the bonus it provides
        this.diceValueRequired = diceValueRequired;
    }

    public void setCardStored (AbstractDevelopmentCard card) {
        this.cardStored = card;
    }

    public AbstractDevelopmentCard getCardStored () {
        return this.cardStored;
    }

    public boolean isEmpty() {
        return (this.cardStored==null); //card already taken by a player
    }

    public void sweepTowerSlot() { this.cardStored = null; }

}


