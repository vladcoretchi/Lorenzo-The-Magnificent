package it.polimi.ingsw.LM34.Model.Cards;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Model.Resources;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents different type of requirements that the player has to satisfy
 * in order to activate the associated {@link LeaderCard}
 */
public class LeaderRequirements implements Serializable {
    private Resources resourcesRequirements;
    private Map<DevelopmentCardColor, Integer> cardRequirements;

    /**
     * Defines the number of card per type that the user must have to activate the leader card.
     * If cardRequirements contains a {@link DevelopmentCardColor.MULTICOLOR} card color,
     * the user must have n cards of a single type regardless of what type it is
     */
    public LeaderRequirements(Resources resourcesRequirements, Map<DevelopmentCardColor, Integer> cardRequirements) {
        this.resourcesRequirements = resourcesRequirements;
        this.cardRequirements = cardRequirements;
    }

    public LeaderRequirements(Resources resourcesRequirements){
        this.resourcesRequirements = resourcesRequirements;
        this.cardRequirements = null;
    }

    public LeaderRequirements(Map<DevelopmentCardColor, Integer> cardRequirements) {
        this.resourcesRequirements = null;
        this.cardRequirements = cardRequirements;
    }

    public Optional<Resources> getResourcesRequirements() {
        return Optional.ofNullable(this.resourcesRequirements);
    }

    public Optional<Map<DevelopmentCardColor, Integer>> getCardRequirements() {
        return Optional.ofNullable(this.cardRequirements);
    }
}
