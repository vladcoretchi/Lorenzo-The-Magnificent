package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Effects.ObserverEffect;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
public class Player implements Serializable {
    private final PawnColor pawnColor; //COLOR OF THE PAWN ASSOCIATED TO THE PLAYER
    private ArrayList<FamilyMember>  familyMembers;
    private PersonalBoard personalBoard;
    private ArrayList <LeaderCard> leadercards;
    private AbstractEffect permanentBonus;
    //TODO: initialize the resources elsewhere
    private Resources resources;

    private ArrayList<ObserverEffect> observerEffects;

    //VARIABLE FOR COMMUNICATION TO CLIENT
    //TODO: evalueate if this network connection is correct

    public Player(PawnColor pawnColor, PersonalBoard personalBoard) {
        this.pawnColor= pawnColor;
        this.personalBoard= personalBoard;
        resources = new Resources();

        familyMembers= new ArrayList<FamilyMember>();

        for ( DiceColor diceColor : DiceColor.values())
            familyMembers.add(new FamilyMember(pawnColor, diceColor));
    }


    //the controller updates the resources and bonuses of the player directly in the personalBoard;
    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public PawnColor getPawnColor() {
        return pawnColor;
    }

    public void addResources(Resources res) {
        this.resources.sumResources(res);
    }

    public Resources getResources() { return this.resources; }

    public ArrayList<FamilyMember> getFamilyMembers() {
        return this.familyMembers;
    }

    public ArrayList<ObserverEffect> getObservers() {
        return this.observerEffects;
    }

    public void addObserver(ObserverEffect observer) {
        observerEffects.add(observer);
    }

    public void unSubscribeObservers() {
        observerEffects.clear();
    }
}
