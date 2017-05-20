package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by vladc on 5/13/2017.
 *
 * This class handles the multiple choice that some BuildingCards' permanent bonus offers
 */

//TODO: think of a way to have two alternatives for the special building cards
public class ResourcesExchangeBonus extends AbstractEffect  {
    private Pair<Resources, Resources>[] resourceExchange;

    public ResourcesExchangeBonus(Pair<Resources, Resources>[] resourceExchange) {
        this.resourceExchange = resourceExchange;
    }
}
