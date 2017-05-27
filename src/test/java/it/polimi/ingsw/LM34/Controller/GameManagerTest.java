package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.SpecialContexts.CurchReportContext;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Utils.Utilities;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by robertodorata on 5/25/17.
 */
public class GameManagerTest {

    @Test
    public void testDrawExcommunicationCards() {

        ArrayList<ExcommunicationCard> excommunicationCards = new ArrayList<>();

        CurchReportContext curchContext = new CurchReportContext();

        ArrayList<ExcommunicationCard> exCards = Utilities.getExcommunictionCards(excommunicationCards);

        for(ExcommunicationCard card : exCards)
            curchContext.addExcommunicationCard(card);

        assertEquals("exCard just initialized, its size should be 0", exCards.size(), 0);
    }


}
