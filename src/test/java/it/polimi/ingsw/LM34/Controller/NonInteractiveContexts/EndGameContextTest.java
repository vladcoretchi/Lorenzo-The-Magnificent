package it.polimi.ingsw.LM34.Controller.NonInteractiveContexts;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class EndGameContextTest {

    /**
     * this test will check if players will effectively copied from GameManager
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        EndGameContext endGameContext = new EndGameContext();
        endGameContext.interactWithPlayer(new Object());
    }

    /**
     * this test will check if the victory points by developmnet card will be calculated corrected
     * @throws Exception
     */
    @Test
    public void onEndGameCalculatePointsByDevelopmentCardsOwned() throws Exception {
        Map<Player, Integer> victoryPointsByPlayer = new HashMap<>();
        Map<Player, Integer> victoryPointsByPlayerResult;
        Player player = new Player("aldo", PawnColor.GREEN, new PersonalBoard());
        victoryPointsByPlayer.put(player, 3);
        EndGameContext endGameContext = new EndGameContext();
        victoryPointsByPlayerResult = endGameContext.onEndGameCalculatePointsByDevelopmentCardsOwned(victoryPointsByPlayer);
        assertEquals(3, victoryPointsByPlayerResult.get(player).longValue());

    }

    /**
     * this test will check if the victory points by venture cards will be calculated corrected
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void onEndCalculateVictoryPointsPerPlayerByVentureCards() throws Exception {
       EndGameContext endGameContext = new EndGameContext();
       Map<Player, Integer> victoryPointsPerPlayer = new HashMap<>();
       Player player = new Player("aldo", PawnColor.GREEN, new PersonalBoard());
       victoryPointsPerPlayer.put(player, 3);
       endGameContext.onEndCalculateVictoryPointsPerPlayerByVentureCards(victoryPointsPerPlayer);
    }

    /**
     * this test will check if the victory points by resources will be calculated correctly
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void onEndCalculateVictoryPointsPerPlayerByResources() throws Exception {
        EndGameContext endGameContext = new EndGameContext();
        Map<Player, Integer> victoryPointsPerPlayer = new HashMap<>();
        Player player = new Player("aldo", PawnColor.GREEN, new PersonalBoard());
        victoryPointsPerPlayer.put(player, 3);
        endGameContext.onEndCalculateVictoryPointsPerPlayerByResources(victoryPointsPerPlayer);
    }
}