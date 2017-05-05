package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import it.polimi.ingsw.LM34.Model.Bonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;

/**
 * Created by Julius on 04/05/2017.
 */

//general slot of the tower
public class TowerSlot extends ActionSlot {
    private final Bonus occupyingReward;
    private Integer diceValueRequired;
    private FamilyMember familyMember;
    private DevelopmentCard cardStored;


    public TowerSlot( Bonus occupyingReward, Integer diceValueRequired) {
        super(occupyingReward, diceValueRequired); //store in the actionslot the bonus it provides
        this.diceValueRequired= diceValueRequired;
    }

    public void setCardStored (DevelopmentCard card) {
        this.cardStored= card;
    }

    public DevelopmentCard getCardStored () {
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


