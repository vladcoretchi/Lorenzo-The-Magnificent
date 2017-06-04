package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
public class Player implements Serializable {
    private final String playerName;
    private final PawnColor pawnColor; //COLOR OF THE PAWN ASSOCIATED TO THE PLAYER
    private ArrayList<FamilyMember>  familyMembers;
    private PersonalBoard personalBoard;
    private ArrayList <LeaderCard> leadercards;
    private ArrayList<ExcommunicationCard> excommunicationCards;
    private AbstractEffect permanentBonus;
    //TODO: initialize the resources elsewhere
    private Resources resources;
    private Integer councilPrivileges;

    private ArrayList<AbstractEffect> observerEffects;

    //VARIABLE FOR COMMUNICATION TO CLIENT
    //TODO: evalueate if this network connection is correct

    public Player(String name, PawnColor pawnColor, PersonalBoard personalBoard) {
        this.playerName = name;
        this.pawnColor= pawnColor;
        this.personalBoard= personalBoard;
        resources = new Resources();
        councilPrivileges = 0;
        this.observerEffects = new ArrayList<>();

        familyMembers= new ArrayList<FamilyMember>();

        for ( DiceColor diceColor : DiceColor.values())
            familyMembers.add(new FamilyMember(pawnColor, diceColor));
    }

    public String getPlayerName() {
        return this.playerName;
    }

    //the controller updates the resources and bonuses of the player directly in the personalBoard;
    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public PawnColor getPawnColor() {
        return pawnColor;
    }

    public void addResources(Resources res)    {
        this.resources.sumResources(res);
    }

    public Resources getResources() { return this.resources; }

    public ArrayList<FamilyMember> getFamilyMembers() {
        return this.familyMembers;
    }

    public ArrayList<AbstractEffect> getObservers() {
        return this.observerEffects;
    }

    //TODO: delete this
    public void registerObserver(AbstractEffect a) {
        observerEffects.add(a);
    }

    //TODO: delete this
    public void unSubscribeObservers() {
        observerEffects.clear();
    }

    public Integer getCouncilPrivileges() {
        return councilPrivileges;
    }

    public void addCouncilPrivileges(Integer councilPrivileges) {
        this.councilPrivileges += councilPrivileges;
    }

    public void subResources(Resources res) {
        this.resources.subResources(res);
    }

    public ArrayList<ExcommunicationCard> getExcommunicationCards() {
        return this.excommunicationCards;
    }

    public ArrayList<LeaderCard> getActivatedLeadercards() {
        ArrayList<LeaderCard> activatedLeaders = new ArrayList<>();
        for(LeaderCard c : leadercards)
            if (c.isActivatedByPlayer())
                activatedLeaders.add(c);

        return activatedLeaders;
    }

    public void addExcommunicationCards(ExcommunicationCard excommunicationCard) {
        this.excommunicationCards.add(excommunicationCard);
    }

    public Boolean hasEnoughResources (Resources resourcesRequired) {
        Resources resourcesAvailable =this.getResources();
        for(ResourceType resType : ResourceType.values())
            if(!(resourcesAvailable.getResourceByType(resType) >= resourcesRequired.getResourceByType(resType)))
                return false;

        return true;
    }




}
