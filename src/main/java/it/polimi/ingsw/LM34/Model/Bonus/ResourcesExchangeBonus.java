package it.polimi.ingsw.LM34.Model.Bonus;

import it.polimi.ingsw.LM34.Model.Resources;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vladc on 5/13/2017.
 */
public class ResourcesExchangeBonus implements BonusInterface {
    private Map<Resources, Resources> resourceExchange;

    //this class has the aim to handle the case of multiple reward for different input resources in BuildingCards
    public ResourcesExchangeBonus(Map<Resources, Resources> resourceExchange) {
        this.resourceExchange = resourceExchange;
    }

    public Map<Resources, Resources> getExchangeChoices() {
        return this.resourceExchange;
    }
}
