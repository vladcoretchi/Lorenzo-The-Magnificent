package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardInterface;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Resources;

/**
 * Created by Julius on 04/05/2017.
 */

//general slot of the tower
public class TowerSlot extends ActionSlot {
    private Integer diceValueRequired;
    private FamilyMember familyMember;
    private DevelopmentCardInterface cardStored;
    private Integer level;
    private Integer coloumn;


    public TowerSlot(Resources occupyingReward, Integer diceValueRequired, Integer level, Integer coloumn) {
        super(occupyingReward, diceValueRequired); //store in the actionslot the bonus it provides
        this.diceValueRequired = diceValueRequired;
    }

    public void setCardStored (DevelopmentCardInterface card) {
        this.cardStored = card;
    }

    public DevelopmentCardInterface getCardStored () {
        return this.cardStored;
    }

    public boolean isEmpty() {
        return (this.cardStored==null); //card already taken by a player
    }

    public void sweepTowerSlot() {
        sweep(); //remove the familyMember
    }


    //TODO: insertFamilyMember is not coded because TowerSlot inherits it from ActionSlot
}


