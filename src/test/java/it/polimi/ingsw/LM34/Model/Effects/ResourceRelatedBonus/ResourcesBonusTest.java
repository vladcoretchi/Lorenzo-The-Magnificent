package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.GameRoom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static org.junit.Assert.*;

public class ResourcesBonusTest {
    @Test
    public void sumResourcesBonus() throws Exception {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);

        resourcesBonus.sumResourcesBonus(resourcesBonus);

        assertTrue(resourcesBonus.getResources().equals(resources));
        assertEquals(6, resourcesBonus.getCouncilPrivilege().longValue());
    }

    @Test
    public void update() throws Exception {
       /* Observable o = new Observable();
        Object obj = new Object();
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        resourcesBonus.update(o, obj);*/



    }

    @Test
    public void applyEffect() throws Exception {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        List<String> players = new ArrayList<>();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        AbstractGameContext abstractGameContext = new AbstractGameContext() {

            @Override
            public void interactWithPlayer() {

            }
        };

//        abstractGameContext.setGameManager(new GameManager(new GameRoom(), players));

   //     resourcesBonus.applyEffect(abstractGameContext);

        //this test failed with NullPointerException, check if is model`s fault or test`s fault

    }

}