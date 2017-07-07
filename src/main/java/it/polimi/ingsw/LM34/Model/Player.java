package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.DiceColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Player implements Serializable {
    private static final long serialVersionUID = -6531439439079355880L;
    
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
            /*Initialize each FamilyMember with the color of the player*/
            if(diceColor != DiceColor.DEFAULT)
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
     * @return the {@link LeaderCard s} activated by the player
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
     * @return the leaders still to be activated or discarded by the player
     */
    public List<LeaderCard> getPendingLeaderCards() {
        List<LeaderCard> leaders = new ArrayList<>();
        this.leaderCards.forEach(card -> {
            if(!card.isActivatedByPlayer())
                leaders.add(card);
        });

        return leaders;
    }

    /**
     * Removes a leader card from the list
     * @param card card to remove
     */
    public void discardLeaderCard(LeaderCard card) {
        this.leaderCards.remove(card);
    }

    /**
     * Adds a card to the list (used when a user copies another user's leader card)
     * @param card card to add
     */
    public void addLeaderCard(LeaderCard card) {
        this.leaderCards.add(card);
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
        return this.resources.hasEnough(resourcesRequired);
    }

    /**
     *
     * @param cardType type of the card to verify
     * @param num number of cards that the user should have
     * @return if the user has a defined number (or more) of cards of a type
     */
    public Boolean hasEnoughCardsOfType(DevelopmentCardColor cardType, Integer num) {
        Optional<List<AbstractDevelopmentCard>> cards = this.getPersonalBoard().getDevelopmentCardsByType(cardType);
        return (cards.isPresent() && cards.get().size() >= num);
    }

    public List<FamilyMember> getAvailableFamilyMembers() {
        List<FamilyMember> availablePawns = new ArrayList<>();
        this.familyMembers.forEach(pawn -> {
            if(!pawn.isUsed())
                availablePawns.add(pawn);
        });

        return availablePawns;
    }
}
