package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Model.Cards.ExcommunicationCard;
import it.polimi.ingsw.LM34.Model.Cards.LeaderCard;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by GiulioComi on 07/05/2017.
 */

public final class SetupLeaderAndExcommunicationDecks {




    public static void prepareLeaderAndExcommunicationDecks(ArrayList<LeaderCard> leaderCards, ArrayList<ExcommunicationCard> excommunicationCards) {

        Collections.shuffle(leaderCards);
        Collections.shuffle(excommunicationCards);
        orderExcommunicatioCardByPeriod(excommunicationCards);
    }


    public static void orderExcommunicatioCardByPeriod(ArrayList<ExcommunicationCard> exc) {
        ArrayList<ExcommunicationCard> temp = new ArrayList();

        for (Integer period = 1; period <= Configurator.TOTAL_PERIODS; period++)
            for (ExcommunicationCard e : exc)
                if (e.getPeriod() == period)
                    temp.add(e);
        exc = temp;
    }


}
