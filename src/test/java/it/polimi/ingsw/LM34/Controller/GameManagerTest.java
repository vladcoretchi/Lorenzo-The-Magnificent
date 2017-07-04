package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Network.GameRoom;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameManagerTest {

    @Test(expected = NullPointerException.class)
    public void startGame() throws Exception {
        List<String> players = new ArrayList<>();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        GameManager gameManager = new GameManager(new GameRoom(), players);
        gameManager.startGame();
    }

    @Test(expected = NullPointerException.class)
    public void endGame() throws Exception {
        List<String> players = new ArrayList<>();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        GameManager gameManager = new GameManager(new GameRoom(), players);
        gameManager.endGame();
    }

    @Test(expected = NullPointerException.class)
    public void nextTurn() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        gameManager.nextTurn();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.nextTurn();
    }

    @Test(expected = NullPointerException.class)
    public void nextPhase() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        gameManager.nextPhase();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.nextPhase();
    }

    @Test(expected = NullPointerException.class)
    public void nextRound() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.nextRound();
    }

    @Test(expected = NullPointerException.class)
    public void nextPeriod() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.nextPeriod();
    }

    @Test(expected = NullPointerException.class)
    public void replaceCards() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.replaceCards();
    }

    @Test(expected = NullPointerException.class)
    public void sweepActionSlots() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.sweepActionSlots();
    }

    @Test(expected = NullPointerException.class)
    public void setupPlayersResources() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.setupPlayersResources();
    }

    @Test(expected = NullPointerException.class)
    public void setupGameContexts() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.setupGameContexts();
    }

    @Test(expected = NullPointerException.class)
    public void bonusTileSelectionPhase() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.bonusTileSelectionPhase();
    }

    @Test(expected = NullPointerException.class)
    public void leaderSelectionPhase() throws Exception {
        List<String> players = new ArrayList<>();
        GameManager gameManager = new GameManager(new GameRoom(), players);
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        gameManager.leaderSelectionPhase();
    }
}