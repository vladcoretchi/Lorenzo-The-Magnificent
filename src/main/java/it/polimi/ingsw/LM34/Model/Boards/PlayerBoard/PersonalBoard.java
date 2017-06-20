package it.polimi.ingsw.LM34.Model.Boards.PlayerBoard;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.BuildingCard;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//TODO: REFACTOR THIS CLASS
/*this class has the aim to aggregate what resources and cards the player have collected*/
public class PersonalBoard {
    //cards
    private List<AbstractDevelopmentCard> territories = new ArrayList<AbstractDevelopmentCard>();
    private List<AbstractDevelopmentCard> characters = new ArrayList<AbstractDevelopmentCard>();
    private List<AbstractDevelopmentCard> ventures = new ArrayList<AbstractDevelopmentCard>();
    private List<AbstractDevelopmentCard> buildings = new ArrayList<AbstractDevelopmentCard>();
    //bonus tile
    private BonusTile personalBonusTile;

    public void addCard(AbstractDevelopmentCard card) throws InvalidCardType {
        switch (card.getColor()) {
            case GREEN:
                territories.add(card);
                break;
            case BLUE:
                characters.add(card);
                break;
            case YELLOW:
                buildings.add(card);
                break;
            case PURPLE:
                ventures.add(card);
                break;
            default:
                throw new InvalidCardType("This card is not a DevelopmentCard");
        }
    }


    public Optional<List<AbstractDevelopmentCard>> getDevelopmentCardsByType(DevelopmentCardColor color)  {
        switch (color) {
            case PURPLE:
                return Optional.of(ventures);
            case BLUE:
                return Optional.of(characters);
            case GREEN:
                return Optional.of(territories);
            case YELLOW:
                return Optional.of(buildings);
            default:
                return Optional.empty();
        }
    }

    public List<AbstractDevelopmentCard> getActivableBuildingCard(Integer valueToProduct) {
        List<AbstractDevelopmentCard> activableCards = new ArrayList<>();
        buildings.forEach(c -> {
            if(((BuildingCard)c).getDiceValueToProduct() > valueToProduct)
                activableCards.add(c);
        });

        return activableCards;
    }

    public List<AbstractDevelopmentCard> getActivableTerritoryCard(Integer valueToHarvest) {
        List<AbstractDevelopmentCard> activableCards = new ArrayList<>();
        territories.forEach(c -> {
            if(((TerritoryCard)c).getDiceValueToHarvest() > valueToHarvest)
                activableCards.add(c);
        });

        return activableCards;
    }

    public BonusTile getPersonalBonusTile() {
        return this.personalBonusTile;
    }


    public void setPersonalBonusTile(BonusTile bonusTile) {
        this.personalBonusTile = bonusTile;
    }

}
