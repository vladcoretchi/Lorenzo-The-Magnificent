package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
public class Player implements Serializable {
    private final String playerName;
    private final PawnColor pawnColor; //COLOR OF THE PAWN ASSOCIATED TO THE PLAYER
    private List<FamilyMember>  familyMembers;
    private PersonalBoard personalBoard;
    private List<LeaderCard> leaderCards;
    private List<ExcommunicationCard> excommunicationCards;
    private Resources resources;

    public Player(String name, PawnColor pawnColor, PersonalBoard personalBoard) {
        this.playerName = name;
        this.pawnColor = pawnColor;
        this.personalBoard = personalBoard;
        this.resources = new Resources();

        this.familyMembers = new ArrayList<>();
        for (DiceColor diceColor : DiceColor.values())
            this.familyMembers.add(new FamilyMember(pawnColor, diceColor));

        this.leaderCards = new ArrayList<>();
        this.excommunicationCards = new ArrayList<>();
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public PawnColor getPawnColor() {
        return this.pawnColor;
    }

    public Resources getResources() { return this.resources; }

    public List<FamilyMember> getFamilyMembers() {
        return this.familyMembers;
    }

    public List<ExcommunicationCard> getExcommunicationCards() {
        return this.excommunicationCards;
    }

    public List<LeaderCard> getActivatedLeaderCards() {
        List<LeaderCard> activatedLeaders = new ArrayList<>();
        this.leaderCards.forEach(card -> {
            if(card.isActivatedByPlayer())
                activatedLeaders.add(card);
        });

        return activatedLeaders;
    }

    public void addResources(Resources res)    {
        this.resources.sumResources(res);
    }

    public void subResources(Resources res) {
        this.resources.subResources(res);
    }

    public void addExcommunicationCards(ExcommunicationCard excommunicationCard) {
        this.excommunicationCards.add(excommunicationCard);
    }

    public Boolean hasEnoughResources (Resources resourcesRequired) {
        Resources resourcesAvailable = this.getResources();
        for(ResourceType resType : ResourceType.values())
            if(resourcesAvailable.getResourceByType(resType) < resourcesRequired.getResourceByType(resType))
                return false;

        return true;
    }

}
