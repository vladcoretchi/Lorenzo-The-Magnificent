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
public class ResourcesExchangeBonus extends AbstractEffect implements Observer {
    private Pair<Resources, Resources> resourceExchange;

    public ResourcesExchangeBonus(Pair<Resources, Resources> resourceExchange) {
        this.resourceExchange = resourceExchange;
    }

    public Pair<Resources, Resources> getExchangeChoices() {
        return this.resourceExchange;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
