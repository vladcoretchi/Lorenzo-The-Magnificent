package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts;

import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChurchReportContextTest {

    /**
     * this test will check if player will properly managed
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        ChurchReportContext churchReportContext = new ChurchReportContext();
        Player player = new Player("aldo", PawnColor.GREEN, new PersonalBoard());
        churchReportContext.interactWithPlayer(player);
    }

}