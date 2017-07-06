package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.*;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionSlotContextTest {

    /**
     * this test will check if action slot and ActionSlotContext will properly managed
     * @throws Exception
     */
    @Test
    public void interactWithPlayer() throws Exception {
        ActionSlotContext actionSlotContext = new ActionSlotContext();
        AbstractGameContext abstractGameContextArgument = new InitializeAbstractGameContext();
        ActionSlot actionSlot = new ActionSlot(true, 1, new ResourcesBonus(new Resources(1,1,1,1,1,1,1), 1));
        actionSlotContext.interactWithPlayer(abstractGameContextArgument, actionSlot);
    }

    /**
     * this test will check if ignoreSlotLimit will properly setted true
     * @throws Exception
     */
    @Test
    public void ignoreSlotLimit() throws Exception {
        ActionSlotContext actionSlotContext = new ActionSlotContext();
        actionSlotContext.ignoreSlotLimit();
    }

    class InitializeAbstractGameContext extends AbstractGameContext {

        @Override
        public Object interactWithPlayer(Object... args) throws IncorrectInputException, MarketBanException, OccupiedSlotException, NotEnoughResourcesException, NotEnoughMilitaryPoints, InvalidLeaderCardAction, CardTypeNumLimitReachedException {
            return null;
        }
    }

}