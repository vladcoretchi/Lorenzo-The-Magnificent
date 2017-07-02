package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Utils.Copyable;
import java.io.Serializable;

public class FamilyMember implements Serializable, Copyable {
    private PawnColor pawnColor;
    private DiceColor diceColor;
    private Integer value;
    private boolean isUsed;

    //constructor used only at the beginning of the game during setup up
    public FamilyMember(PawnColor pawnColor, DiceColor diceColor) {
        this.pawnColor = pawnColor;
        this.diceColor = diceColor;
        this.isUsed = false;
    }

    @Override
    public FamilyMember copy() {
        FamilyMember familyMember = new FamilyMember(this.pawnColor, this.diceColor);
        familyMember.setValue(this.value.intValue());
        if(this.isUsed)
            familyMember.placePawn();
        else
            familyMember.freePawn();

        return familyMember;
    }

    //used to verify if a action is allowed based on the corresponding dice value
    public Integer getValue() {
        return this.value;
    }

    public PawnColor getFamilyMemberColor() {
        return this.pawnColor;
    }

    public DiceColor getDiceColorAssociated() { return this.diceColor; }

    //the controller updates the family member values as soon as the corresponding dice is rolled at the beginning of each turn
    public void setValue(Integer diceValue) {
        this.value = diceValue;
    }

    //mark the FamilyMember as used after it has been placed in a action slot
    public void placePawn() {
        this.isUsed = true;
    }

    //the controller calls this at the end of each turn to "return" to players their pawns
    public void freePawn() {
        this.isUsed = false;
    }

    //check availability of this family member
    public boolean isUsed() {
        return this.isUsed;
    }
}