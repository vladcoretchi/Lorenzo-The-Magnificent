package it.polimi.ingsw.LM34.Controller;

import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts.*;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.*;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.EndGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.NoSuchContextException;
import org.junit.Test;
import sun.nio.cs.US_ASCII;

import static org.junit.Assert.*;

public class ContextFactoryTest {

    @Test
    public void getContext() throws Exception {
        assertTrue(ContextFactory.getContext(ContextType.LEADER_CARDS_CONTEXT) instanceof LeaderCardsContext);
        assertTrue(ContextFactory.getContext(ContextType.ACTION_SLOT_CONTEXT) instanceof ActionSlotContext);
        assertTrue(ContextFactory.getContext(ContextType.CHURCH_REPORT_CONTEXT) instanceof ChurchReportContext);
        assertTrue(ContextFactory.getContext(ContextType.END_GAME_CONTEXT) instanceof EndGameContext);
        assertTrue(ContextFactory.getContext(ContextType.HARVEST_AREA_CONTEXT) instanceof HarvestAreaContext);
        assertTrue(ContextFactory.getContext(ContextType.PRODUCTION_AREA_CONTEXT) instanceof ProductionAreaContext);
        assertTrue(ContextFactory.getContext(ContextType.RESOURCE_INCOME_CONTEXT) instanceof ResourceIncomeContext);
        assertTrue(ContextFactory.getContext(ContextType.TOWERS_CONTEXT) instanceof TowersContext);
        assertTrue(ContextFactory.getContext(ContextType.INCREASE_PAWNS_VALUE_BY_SERVANTS_CONTEXT) instanceof IncreasePawnsValueByServantsContext);
        assertTrue(ContextFactory.getContext(ContextType.RESOURCE_EXCHANGE_CONTEXT) instanceof ResourcesExchangeContext);
        assertTrue(ContextFactory.getContext(ContextType.USE_COUNCIL_PRIVILEGE_CONTEXT) instanceof UseCouncilPrivilegeContext);
        assertTrue(ContextFactory.getContext(ContextType.LEADER_CARDS_CONTEXT) instanceof LeaderCardsContext);
        assertTrue(ContextFactory.getContext(ContextType.COUNCIL_PALACE_CONTEXT) instanceof CouncilPalaceContext);
        assertTrue(ContextFactory.getContext(ContextType.MARKET_AREA_CONTEXT) instanceof MarketAreaContext);
        assertTrue(ContextFactory.getContext(ContextType.TURN_CONTEXT) instanceof TurnContext);
        assertTrue(ContextFactory.getContext(ContextType.FAMILY_MEMBER_SELECTION_CONTEXT) instanceof FamilyMemberSelectionContext);
    }

}