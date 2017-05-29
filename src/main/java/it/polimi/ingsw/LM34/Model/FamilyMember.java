package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;

import java.io.Serializable;

/**
 * Created by Giulio Comi on 02/04/2017.
 */
public class FamilyMember implements Serializable, Cloneable {
    private final PawnColor pawnColor;
    private DiceColor diceColor;
    private Integer value;
    private boolean isUsed;
    //TODO: find another way different to get a reference of the player from the family members
    private Player playerOwner;


    //constructor used only at the beginning of the game during setup up

    FamilyMember(PawnColor pawnColor, DiceColor diceColor) {
        this.pawnColor = pawnColor;
        this.diceColor = diceColor;
        this.playerOwner = playerOwner;
        this.isUsed = false;
    }

    //TODO
    @Override
    public FamilyMember clone() {
        try {
            return (FamilyMember) super.clone();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //used to verify if a action is allowed based on the corresponding dice value
    public Integer getValue() {
        return this.value;
    }

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

    public PawnColor getFamilyMemberColor() {
        return this.pawnColor;
    }


    public DiceColor getDiceColorAssociated() { return this.diceColor; }

    public Player getPlayer() {
        return playerOwner;
    }
}