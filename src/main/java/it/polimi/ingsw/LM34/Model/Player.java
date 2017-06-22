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
import java.util.List;

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

    public Player(String name, PawnColor pawnColor, PersonalBoard personalBoard) {
        this.playerName = name;
        this.pawnColor = pawnColor;
        this.personalBoard = personalBoard;
        this.resources = new Resources();
        this.councilPrivileges = 0;

        this.familyMembers = new ArrayList<FamilyMember>();

        for ( DiceColor diceColor : DiceColor.values())
            this.familyMembers.add(new FamilyMember(pawnColor, diceColor));
    }

    public String getPlayerName() {
        return this.playerName;
    }

    //the controller updates the resources and bonuses of the player directly in the personalBoard;
    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public PawnColor getPawnColor() {
        return this.pawnColor;
    }

    public void addResources(Resources res)    {
        this.resources.sumResources(res);
    }

    public Resources getResources() { return this.resources; }

    public List<FamilyMember> getFamilyMembers() {
        return this.familyMembers;
    }

    public Integer getCouncilPrivileges() {
        return this.councilPrivileges;
    }

    public void addCouncilPrivileges(Integer councilPrivileges) {
        this.councilPrivileges += councilPrivileges;
    }

    public void subResources(Resources res) {
        this.resources.subResources(res);
    }

    public List<ExcommunicationCard> getExcommunicationCards() {
        return this.excommunicationCards;
    }

    public List<LeaderCard> getActivatedLeaderCards() {
        List<LeaderCard> activatedLeaders = new ArrayList<>();
        for(LeaderCard c : this.leadercards)
            if (c.isActivatedByPlayer())
                activatedLeaders.add(c);

        return activatedLeaders;
    }

    public void addExcommunicationCards(ExcommunicationCard excommunicationCard) {
        this.excommunicationCards.add(excommunicationCard);
    }

    public Boolean hasEnoughResources (Resources resourcesRequired) {
        Resources resourcesAvailable = this.getResources();
        for(ResourceType resType : ResourceType.values())
            if(!(resourcesAvailable.getResourceByType(resType) >= resourcesRequired.getResourceByType(resType)))
                return false;

        return true;
    }

}
