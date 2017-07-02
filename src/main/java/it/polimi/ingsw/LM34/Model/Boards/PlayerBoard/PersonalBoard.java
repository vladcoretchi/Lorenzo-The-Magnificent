package it.polimi.ingsw.LM34.Model.Boards.PlayerBoard;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.BuildingCard;
import it.polimi.ingsw.LM34.Model.Cards.TerritoryCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonalBoard implements Serializable {
    /**
     * Development card types stored in the PersonalBoard
     */
    private List<AbstractDevelopmentCard> territories;
    private List<AbstractDevelopmentCard> characters;
    private List<AbstractDevelopmentCard> ventures;
    private List<AbstractDevelopmentCard> buildings;

    /**
     * {@link PersonalBoard} stored in the PersonalBoard
     */
    private BonusTile personalBonusTile;

    public PersonalBoard() {
        this.territories = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.ventures = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.personalBonusTile = null;
    }

    public void addCard(AbstractDevelopmentCard card) throws InvalidCardType {
        switch (card.getColor()) {
            case GREEN: territories.add(card);
                break;
            case BLUE: characters.add(card);
                break;
            case YELLOW: buildings.add(card);
                break;
            case PURPLE: ventures.add(card);
                break;
            default:
                throw new InvalidCardType("This card is not a DevelopmentCard");
        }
    }

    /**
     * @param developmentCardType of which we want to retrieve the deck associated
     * @return
     */
    public Optional<List<AbstractDevelopmentCard>> getDevelopmentCardsByType(DevelopmentCardColor color)  {
        switch (color) {
            case PURPLE: return Optional.of(ventures);
            case BLUE: return Optional.of(characters);
            case GREEN: return Optional.of(territories);
            case YELLOW: return Optional.of(buildings);
            default:
                return Optional.empty();
        }
    }

    /**
     * @param valueToProduct of the player at the moment
     * @return all building cards that have their diceValue requirement less than the valueToProduct
     */
    public List<AbstractDevelopmentCard> getActivableBuildingCard(Integer valueToProduct) {
        List<AbstractDevelopmentCard> activableCards = new ArrayList<>();
        buildings.forEach(c -> {
            if(((BuildingCard)c).getDiceValueToProduct() > valueToProduct)
                activableCards.add(c);
        });

        return activableCards;
    }

    /**
     * @param valueToHarvest of the player at the moment
     * @return all building cards that have their diceValue requirement less than the valueToHarvest
     */
    public List<AbstractDevelopmentCard> getActivableTerritoryCard(Integer valueToHarvest) {
        List<AbstractDevelopmentCard> activableCards = new ArrayList<>();
        territories.forEach(c -> {
            if(((TerritoryCard)c).getDiceValueToHarvest() > valueToHarvest)
                activableCards.add(c);
        });

        return activableCards;
    }

    public void setPersonalBonusTile(BonusTile bonusTile) {
        this.personalBonusTile = bonusTile;
    }
    public BonusTile getPersonalBonusTile() {
        return this.personalBonusTile;
    }
}
