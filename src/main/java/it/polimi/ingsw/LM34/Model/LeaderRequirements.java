package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.DevelopmentCardColor;

import java.util.Map;
import java.util.Optional;

/**
 * Created by GiulioComi on 11/05/2017.
 */
public class LeaderRequirements {
    Optional<Resources> resourcesRequirements;

    /**
     * Defines the number of card per type that the user must have to activate the leader card.
     * If cardRequirements contains a DEFAULT card color,
     * the user must have n cards of a single type regardless of what type it is
     */
    Optional<Map<DevelopmentCardColor, Integer>> cardRequirements;

    public LeaderRequirements(Resources resourcesRequirements, Map<DevelopmentCardColor, Integer> cardRequirements) {
        this.resourcesRequirements = Optional.of(resourcesRequirements);
        this.cardRequirements = Optional.of(cardRequirements);
    }

    public LeaderRequirements(Resources resourcesRequirements){
        this.resourcesRequirements = Optional.of(resourcesRequirements);
        this.cardRequirements = Optional.empty();
    }

    public LeaderRequirements(Map<DevelopmentCardColor, Integer> cardRequirements) {
        this.resourcesRequirements = Optional.empty();
        this.cardRequirements = Optional.of(cardRequirements);
    }

    public Optional<Resources> getResourcesRequirements() {
        return this.resourcesRequirements;
    }

    public Optional<Map<DevelopmentCardColor, Integer>> getCardRequirements() {
        return this.cardRequirements;
    }
}
