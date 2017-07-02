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

public class Player implements Serializable {
    private final String playerName;
    private final PawnColor pawnColor; /*Color of the Pawn associated to the player*/
    private List<FamilyMember>  familyMembers;
    private Resources resources;
    private PersonalBoard personalBoard; /**{@link PersonalBoard} cointains the developments cards bought**/
    private List<LeaderCard> leaderCards;
    private List<ExcommunicationCard> excommunicationCards; /**Excommunication cards collected by the player during the game**/


    public Player(String name, PawnColor pawnColor, PersonalBoard personalBoard) {
        this.playerName = name;
        this.pawnColor = pawnColor;
        this.personalBoard = personalBoard;
        this.resources = new Resources();

        this.familyMembers = new ArrayList<>();
        for (DiceColor diceColor : DiceColor.values())
            /**Initialize each FamilyMember with the color of the player**/
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

    /**
     * @return the leaders activated by the player
     */
    public List<LeaderCard> getActivatedLeaderCards() {
        List<LeaderCard> activatedLeaders = new ArrayList<>();
        this.leaderCards.forEach(card -> {
            if(card.isActivatedByPlayer())
                activatedLeaders.add(card);
        });

        return activatedLeaders;
    }

    /**
     * Adds 2 {@link Resources}
     * @param the {@link Resources} to add
     */
    public void addResources(Resources res)    {
        this.resources.sumResources(res);
    }

    /**
     * Adds 2 {@link Resources}
     * @param the {@link Resources} to subtract
     */
    public void subResources(Resources res) {
        this.resources.subResources(res);
    }

    /**
     * @param excommunicationCard to add to the player if he gets excommunicated
     */
    public void addExcommunicationCards(ExcommunicationCard excommunicationCard) {
        this.excommunicationCards.add(excommunicationCard);
    }

    /**
     * @param resourcesRequired by the action the player wants to perform
     * @return if the player could perform the action
     */
    public Boolean hasEnoughResources (Resources resourcesRequired) {
        Resources resourcesAvailable = this.getResources();
        for(ResourceType resType : ResourceType.values())
            if(resourcesAvailable.getResourceByType(resType) < resourcesRequired.getResourceByType(resType))
                return false;

        return true;
    }
}
