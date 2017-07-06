package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeaderCardsContextTest {

    /**
     * this test will check if leaderCardAction will properly recognized
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        LeaderCardsContext leaderCardsContext = new LeaderCardsContext();
        leaderCardsContext.interactWithPlayer();
    }

    /**
     * this test will check if a leader ability will properly copied
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void copyOtherLeaderAbility() throws Exception {
        LeaderCardsContext leaderCardsContext = new LeaderCardsContext();
        leaderCardsContext.copyOtherLeaderAbility();
    }

}