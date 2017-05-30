package it.polimi.ingsw.LM34.Controller.GameContext;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by robertodorata on 5/26/17.
 */
public class UseCouncilPrivilegeContextTest {

    @Test
    public void testIntractWithPlayer() {

        Integer numberOfCouncilPrivileges  = 3;
        PersonalBoard personalBoard = new PersonalBoard();
        /*Player player = new Player(PawnColor.RED, personalBoard);

        for(Integer used = 0; used < numberOfCouncilPrivileges; used++) {
            player.addResources(new Resources(1,2,3,4));
        }

        assertEquals("Coins: expected 3", player.getResources().getResourceByType(ResourceType.COINS).intValue(), 3);
        assertEquals("Woods: expected 6", player.getResources().getResourceByType(ResourceType.WOODS).intValue(), 6);
        assertEquals("Stones: expected 9", player.getResources().getResourceByType(ResourceType.STONES).intValue(), 9);
        assertEquals("Servants: expected 12", player.getResources().getResourceByType(ResourceType.SERVANTS).intValue(), 12);

    */}
}
