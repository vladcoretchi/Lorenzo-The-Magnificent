package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;

import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by robertodorata on 5/25/17.
 */
public class ResourcesPerItemBonusTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testApplyIstantEffect() {

        Integer militaryPointsRequired = 100;

        PersonalBoard personalBoard = new PersonalBoard();
        Player player = new Player(PawnColor.RED, personalBoard);
        Resources bonusResources = new Resources(1,1,1,1,1,1,1);

        for(Integer timesApplied = 0; timesApplied < militaryPointsRequired; timesApplied++) {
            player.addResources(bonusResources);
        }

        assertEquals("Coins: expected 100", player.getResources().getResourceByType(ResourceType.COINS).intValue(), 100);
        assertEquals("Woods: expected 100", player.getResources().getResourceByType(ResourceType.WOODS).intValue(), 100);
        assertEquals("Stones: expected 100", player.getResources().getResourceByType(ResourceType.STONES).intValue(), 100);
        assertEquals("Servants: expected 100", player.getResources().getResourceByType(ResourceType.MILITARY_POINTS).intValue(), 100);
        assertEquals("FaithPoints: expected 100", player.getResources().getResourceByType(ResourceType.FAITH_POINTS).intValue(), 100);
        assertEquals("VictoryPoints: expected 100", player.getResources().getResourceByType(ResourceType.VICTORY_POINTS).intValue(), 100);

    }

    @Test
    public void testApplyInstantEffectWithException() {

        Integer numberOfThatCardTypeOwned = 0;

        PersonalBoard personalBoard = new PersonalBoard();
        Player player = new Player(PawnColor.RED, personalBoard);

        DevelopmentCardColor cardColor = DevelopmentCardColor.valueOf("ORANGE");

        try {
            player.getPersonalBoard().getDevelopmentCardsByType(cardColor);
        }
        catch(InvalidCardType ex) {

        }

        exception.expect(IllegalArgumentException.class);
        exception.reportMissingExceptionWithMessage("IllegalArgumentException was not thrown");

    }

}
