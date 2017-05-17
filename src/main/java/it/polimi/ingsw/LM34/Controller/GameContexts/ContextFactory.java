package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.ContextEnum;

/**
 * Created by GiulioComi on 17/05/2017.
 */
public final class ContextFactory {


    //TODO: evaluate if this is really static or cannot be in factory pattern
    public  static ContextInterface getContext(ContextEnum contextType) {
        switch (contextType) {
            case ACTION_SLOT_CONTEXT:
                return  new ActionSlotContext();
            case CURCH_REPORT_CONTEXT:
                return new CurchReportContext();
            case END_GAME_CONTEXT:
                return new EndGameContext();
            case HARVEST_AREA_CONTEXT:
                return new HarvestAreaContext();
            case PRODUCTION_AREA_CONTEXT:
                return new ProductionAreaContext();
            case RESOURCE_INCOME_CONTEXT:
                return new ResourceIncomeContext();
            case TOWER_CONTEXT:
                return new TowerContext();
            default:
                return null;

        }

    }
}
