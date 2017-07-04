package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void discardLeaderCard() throws Exception {
        Player player = new Player("aldo", PawnColor.RED, new PersonalBoard());
        LeaderCard leaderCard1 = new LeaderCard("obama", null, null, true);
        LeaderCard leaderCard2 = new LeaderCard("renzi", null, null, true);
        LeaderCard leaderCard3 = new LeaderCard("putin", null, null, true);
        player.addLeaderCard(leaderCard1);
        player.addLeaderCard(leaderCard2);

        assertEquals(2, player.getPendingLeaderCards().size());

        player.discardLeaderCard(leaderCard1);

        assertEquals(1, player.getPendingLeaderCards().size());

        player.discardLeaderCard(leaderCard3);

        assertEquals(1, player.getPendingLeaderCards().size());
    }

    @Test
    public void addLeaderCard() throws Exception {
        Player player = new Player("giovanni", PawnColor.RED, new PersonalBoard());

        assertEquals(0, player.getPendingLeaderCards().size());

        LeaderCard leaderCard1 = new LeaderCard("abe", null, null, true);
        LeaderCard leaderCard2 = new LeaderCard(null, null, null, true);

        player.addLeaderCard(leaderCard1);

        assertEquals(1, player.getPendingLeaderCards().size());

        player.addLeaderCard(leaderCard2);

        assertEquals(2, player.getPendingLeaderCards().size());

    }

    @Test
    public void addResources() throws Exception {
        Player player = new Player("giacomo", PawnColor.RED, new PersonalBoard());
        Resources resourcesEmpty = new Resources(0,0,0,0,0,0,0);
        Resources resourcesPositive = new Resources(1,1,1,1,1,1,1);
        Resources resourcesNegative = new Resources(-2, -2, -2, -2, -2, -2, -2);

        player.addResources(resourcesEmpty);
        for(ResourceType resourceType : ResourceType.values()) {
            assertEquals(0, player.getResources().getResourceByType(resourceType).longValue());
        }

        player.addResources(resourcesPositive);
        for(ResourceType resourceType : ResourceType.values()) {
            assertEquals(1, player.getResources().getResourceByType(resourceType).longValue());
        }

        player.addResources(resourcesNegative);
        for(ResourceType resourceType : ResourceType.values()) {
            assertEquals(-1, player.getResources().getResourceByType(resourceType).longValue());
        }

    }

    @Test
    public void addExcommunicationCards() throws Exception {
        Player player = new Player("chiara", PawnColor.RED, new PersonalBoard());
        ExcommunicationCard excommunicationCard = new ExcommunicationCard(1,1,null);

        assertEquals(0, player.getExcommunicationCards().size());

        player.addExcommunicationCards(excommunicationCard);

        assertEquals(1, player.getExcommunicationCards().size());
    }

    @Test
    public void hasEnoughResources() throws Exception {
        Player player = new Player("claudia", PawnColor.RED, new PersonalBoard());
        Resources resources = new Resources(-1,-1,-1,-1,-1,-1,-1);

        assertTrue(player.hasEnoughResources(resources));

        player.addResources(resources);

        assertTrue(player.hasEnoughResources(resources));

        player.addResources(resources);

        assertFalse(player.hasEnoughResources(resources));
    }

    @Test
    public void hasEnoughCardsOfType() throws Exception {
        Player player = new Player("silvana", PawnColor.RED, new PersonalBoard());

        assertTrue(player.hasEnoughCardsOfType(DevelopmentCardColor.BLUE, -1));

        assertFalse(player.hasEnoughCardsOfType(DevelopmentCardColor.BLUE, 1));


    }

}