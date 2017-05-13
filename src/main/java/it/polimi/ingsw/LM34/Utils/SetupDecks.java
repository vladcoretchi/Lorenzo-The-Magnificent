package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Model.Cards.DevelopmentCardInterface;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 07/05/2017.
 */

public final class SetupDecks {
    private static final Integer TOTAL_PERIODS=3;

    public static ArrayList<DevelopmentCardInterface> orderCardByPeriod(ArrayList<DevelopmentCardInterface> dci) {
        ArrayList<DevelopmentCardInterface> temp = new ArrayList();

        for (Integer period = 1; period <= TOTAL_PERIODS; period++)
            for (DevelopmentCardInterface d : dci)
                if (d.getPhase() == period)
                    temp.add(d);
        return temp;

    }
}
