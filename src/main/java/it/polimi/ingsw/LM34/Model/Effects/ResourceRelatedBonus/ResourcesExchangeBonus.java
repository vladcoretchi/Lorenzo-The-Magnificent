package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.GameContexts.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Utilities;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by vladc on 5/13/2017.
 *
 * This class handles the multiple choice that some BuildingCards' permanent bonus offers
 */

//TODO: think of a way to have two alternatives for the special building cards
public class ResourcesExchangeBonus extends AbstractEffect implements Observer {
    private Pair<Resources, Resources>[] resourceExchange;

    public ResourcesExchangeBonus(Pair<Resources, Resources>[] resourceExchange) {
        this.resourceExchange = resourceExchange;
    }

    @Override
    public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {
        Utilities.getContextByType(contexts, ContextType.PRODUCTION_AREA_CONTEXT).addObserver(this);
    }


    public void resetApplyFlag() {
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }
}
