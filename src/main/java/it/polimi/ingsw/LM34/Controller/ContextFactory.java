package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.*;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.*;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.EndGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;

/**
 * A Factory pattern that creates the contexts at the beginning of the game
 * {@link AbstractGameContext}
 */
public final class ContextFactory {

    private ContextFactory() {/*This constructor his private in order to avoid that this Class is instantiated*/}

    /**
     * @param contextType {@link ContextType Enum} by which instantiate the corresponding object
     * @return the appropriate {@link AbstractGameContext}
     * @throws NoSuchContextException
     */
    public static AbstractGameContext getContext(ContextType contextType) throws NoSuchContextException {
        switch (contextType) {
            case LEADER_CARDS_CONTEXT:
                return new LeaderCardsContext();
            case ACTION_SLOT_CONTEXT:
                return new ActionSlotContext();
            case CHURCH_REPORT_CONTEXT:
                return new ChurchReportContext();
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
            case TURN_CONTEXT:
                return new TurnContext();
            case FAMILY_MEMBER_SELECTION_CONTEXT:
                return new FamilyMemberSelectionContext();
            default:
                throw new NoSuchContextException();
        }
    }
}

