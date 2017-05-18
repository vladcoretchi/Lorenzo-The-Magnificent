package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 *
 * This class handles the multiple choice that some BuildingCards' permanent bonus offers
 */

//TODO: think of a way to have two alternatives for the special building cards
public class ResourcesExchangeBonus  {
    private Pair<Resources, Resources> resourceExchangeFirstOption;
    private Pair<Resources, Resources> resourceExchangeSecondOption;

    public ResourcesExchangeBonus(Pair<Resources, Resources> resourceExchange) {
        this.resourceExchange = resourceExchange;
    }

    public Pair<Resources, Resources> getExchangeChoices() {
        return this.resourceExchange;
    }

    //TODO:restyle this
    public boolean hasMoreOptions() {
        return resourceExchangeSecondOption!=null;
    }
}
