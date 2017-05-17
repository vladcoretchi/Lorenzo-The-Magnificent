package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Model.Cards.AbstractDevelopmentCard;
import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by GiulioComi on 07/05/2017.
 */

public final class SetupDecks {
    private static final Integer TOTAL_PERIODS=3;



    public static void prepareOtherDecks(ArrayList<LeaderCard> leaderCards, ArrayList<ExcommunicationCard> excommunicationCards) {

        Collections.shuffle(leaderCards);
        Collections.shuffle(excommunicationCards);
        orderCardByPeriod(excommunicationCards);
    }



    public static void prepareDevelopmentCard(ArrayList<AbstractDevelopmentCard> deck) {
        Collections.shuffle(deck);
        orderDevelopmentCardByPeriod(deck);
    }




    public static void orderDevelopmentCardByPeriod(ArrayList<AbstractDevelopmentCard> dci) {
        ArrayList<AbstractDevelopmentCard> temp = new ArrayList();

        for (Integer period = 1; period <= TOTAL_PERIODS; period++)
            for (AbstractDevelopmentCard d : dci)
                if (d.getPeriod() == period)
                    temp.add(d);
        dci = temp;
    }

    public static void orderCardByPeriod(ArrayList<ExcommunicationCard> exc) {
        ArrayList<ExcommunicationCard> temp = new ArrayList();

        for (Integer period = 1; period <= TOTAL_PERIODS; period++)
            for (ExcommunicationCard e : exc)
                if (e.getPeriod() == period)
                    temp.add(e);
        exc = temp;
    }


}
