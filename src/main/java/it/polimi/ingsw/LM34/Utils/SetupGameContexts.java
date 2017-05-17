package it.polimi.ingsw.LM34.Utils;

import it.polimi.ingsw.LM34.Controller.GameContexts.*;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 17/05/2017.
 */
public class SetupGameContexts {

    public static void setupGameContexts(ArrayList<ContextInterface> contexts) {
       /* for (ContextEnum context : ContextEnum.values())
            contexts.add(ContextFactory.getContext(context));*/
        contexts.add(new ActionSlotContext());
        contexts.add(new CurchReportContext());
        contexts.add(new EndGameContext());
        contexts.add(new HarvestAreaContext());
        contexts.add(new ProductionAreaContext());
        contexts.add(new ResourceIncomeContext());
        contexts.add(new TowerContext());
    }
}
