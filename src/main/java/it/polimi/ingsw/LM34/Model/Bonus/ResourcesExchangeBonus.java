package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Resources;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by vladc on 5/13/2017.
 */
public class ResourcesExchangeBonus implements EffectInterface {
    private Pair<Resources, Resources> resourceExchange;

    public ResourcesExchangeBonus(Pair<Resources, Resources> resourceExchange) {
        this.resourceExchange = resourceExchange;
    }

    public Pair<Resources, Resources> getExchangeChoices() {
        return this.resourceExchange;
    }
}
