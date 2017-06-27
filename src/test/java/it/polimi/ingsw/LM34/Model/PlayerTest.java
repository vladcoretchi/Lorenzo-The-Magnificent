package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Effects.AbstractEffect;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void hasEnoughResources() throws Exception {
        Player player = new Player("aldo", PawnColor.GREEN, new PersonalBoard());

        assertTrue(player.getResources().getResourceByType(ResourceType.SERVANTS) == 0);
    }

    @Test
    public void addResources() {
        Player player = new Player("aldo", PawnColor.GREEN, new PersonalBoard());
        Resources res = new Resources(1,1,1,1,1,1,1);
        player.addResources(res);
        res.sumResources(player.getResources());
        assertEquals("Coins: expected 2", 2, res.getResourceByType(ResourceType.COINS).intValue());
        assertEquals("Woods: expected 2", 2, res.getResourceByType(ResourceType.WOODS).intValue());
        assertEquals("Stones: expected 2", 2, res.getResourceByType(ResourceType.STONES).intValue());
        assertEquals("Servants: expected 2", 2, res.getResourceByType(ResourceType.SERVANTS).intValue());
        assertEquals("MilitaryPoints: expected 2", 2, res.getResourceByType(ResourceType.MILITARY_POINTS).intValue());
        assertEquals("FaithPoints: expected 2", 2, res.getResourceByType(ResourceType.FAITH_POINTS).intValue());
        assertEquals("VictoryPoints: expected 2", 2, res.getResourceByType(ResourceType.VICTORY_POINTS).intValue());
    }

    @Test
    public void SubResources() {
        Player player = new Player("aldo", PawnColor.GREEN, new PersonalBoard());
        Resources res = new Resources(2,2,2,2,2,2,2);
        Resources minus = new Resources(-1,-1,-1,-1,-1,-1,-1);
        player.subResources(minus);
        res.subResources(player.getResources());
        assertEquals("Coins: expected 1", 1, res.getResourceByType(ResourceType.COINS).intValue());
        assertEquals("Woods: expected 1", 1, res.getResourceByType(ResourceType.WOODS).intValue());
        assertEquals("Stones: expected 1", 1, res.getResourceByType(ResourceType.STONES).intValue());
        assertEquals("Servants: expected 1", 1, res.getResourceByType(ResourceType.SERVANTS).intValue());
        assertEquals("MilitaryPoints: expected 1", 1, res.getResourceByType(ResourceType.MILITARY_POINTS).intValue());
        assertEquals("FaithPoints: expected 1", 1, res.getResourceByType(ResourceType.FAITH_POINTS).intValue());
        assertEquals("VictoryPoints: expected 1", 1, res.getResourceByType(ResourceType.VICTORY_POINTS).intValue());
    }

}