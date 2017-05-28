package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.GameContexts.*;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;

/**
 * Created by GiulioComi on 17/05/2017.
 *
 * A Factory pattern that creates the contexts at the beginning of the game
 */
public final class ContextFactory {

    public  static AbstractGameContext getContext(ContextType contextType) {
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
            case TOWERS_CONTEXT:
                return new TowersContext();
            case INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT:
                return new IncreasePawnsValueByServantsContext();
            case RESOURCE_EXCHANGE_CONTEXT:
                return new ResourcesExchangeContext();
            case USE_COUNCIL_PRIVILEGE_CONTEXT:
                return new UseCouncilPrivilegeContext();
            case COUNCIL_PALACE_CONTEXT:
                return new CouncilPalaceContext();
            case MARKET_AREA_CONTEXT:
                return new MarketAreaContext();
            default:
                return null;
        }
    }
}
