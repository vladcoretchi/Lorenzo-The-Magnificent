package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void testaddObserver() {

        ArrayList<AbstractEffect> abstractEffects = new ArrayList<>();

       /* AbstractEffect observer = new AbstractEffect() {

            public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {

            }

            @Override
            public void applyEffect(Player player) {

            }
        };

        for(int i = 0; i < abstractEffects.size(); i++)
            assertTrue("abstractEffects should be null ", abstractEffects.isEmpty());

        abstractEffects.add(observer);

        assertEquals("Size of abstractEffect: should be 1 ", abstractEffects.size(), 1);

    }

    @Test
    public void testAddResources() {
        Resources res = new Resources(1,1,1,1,1,1,1);

        res.sumResources(res);

        assertEquals("Coins: expected 2", res.getResourceByType(ResourceType.COINS).intValue(), 2);
        assertEquals("Woods: expected 2", res.getResourceByType(ResourceType.WOODS).intValue(), 2);
        assertEquals("Stones: expected 2", res.getResourceByType(ResourceType.STONES).intValue(), 2);
        assertEquals("Servants: expected 2", res.getResourceByType(ResourceType.SERVANTS).intValue(), 2);
        assertEquals("MilitaryPoints: expected 2", res.getResourceByType(ResourceType.MILITARY_POINTS).intValue(), 2);
        assertEquals("FaithPoints: expected 2", res.getResourceByType(ResourceType.FAITH_POINTS).intValue(), 2);
        assertEquals("VictoryPoints: expected 2", res.getResourceByType(ResourceType.VICTORY_POINTS).intValue(), 2);
    }

    @Test
    public void testUnsubscribeObserver() {
        ArrayList<AbstractEffect> abstractEffects = new ArrayList<>();

        AbstractEffect observer = new AbstractEffect() {

            public void subscribeObserverToContext(ArrayList<AbstractGameContext> contexts) {

            }

            @Override
            public void  applyEffect(Player player) {

            }
        };

        for(Integer i = 0; i < 5; i++)
            abstractEffects.add(observer);

        abstractEffects.clear();

        assertTrue("abstractEffects should be empty ", abstractEffects.isEmpty());

    }


    @Test
    public void testSubResources() {
        Resources res = new Resources(2,2,2,2,2,2,2);
        Resources minus = new Resources(1,1,1,1,1,1,1);

        res.subResources(minus);

        assertEquals("Coins: expected 1", res.getResourceByType(ResourceType.COINS).intValue(), 1);
        assertEquals("Woods: expected 1", res.getResourceByType(ResourceType.WOODS).intValue(), 1);
        assertEquals("Stones: expected 1", res.getResourceByType(ResourceType.STONES).intValue(), 1);
        assertEquals("Servants: expected 1", res.getResourceByType(ResourceType.SERVANTS).intValue(), 1);
        assertEquals("MilitaryPoints: expected 1", res.getResourceByType(ResourceType.MILITARY_POINTS).intValue(), 1);
        assertEquals("FaithPoints: expected 1", res.getResourceByType(ResourceType.FAITH_POINTS).intValue(), 1);
        assertEquals("VictoryPoints: expected 1", res.getResourceByType(ResourceType.VICTORY_POINTS).intValue(), 1);

   */ }

}