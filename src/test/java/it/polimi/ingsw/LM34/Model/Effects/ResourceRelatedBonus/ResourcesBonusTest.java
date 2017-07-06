package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Controller.*;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Network.GameRoom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static org.junit.Assert.*;

public class ResourcesBonusTest {

    /**
     * this test will check if incomeContext will be properly called
     * @throws Exception ClassCastException if Observable won't be casted to AbstractGameContext
     */
    @Test//(expected = ClassCastException.class)
    public void update() throws Exception {
        Observable o = new Observable();
        Object obj = new Object();
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        resourcesBonus.update(o, obj);
    }

    /**
     * this test will check if card's effect will be properly applied
     * @throws Exception NullPointerException, because callerContext will contains a null game manager
     */
    @Test(expected = NullPointerException.class)
    public void applyEffect() throws Exception {
        Resources resources = new Resources(1,1,1,1,1,1,1);
        ResourcesBonus resourcesBonus = new ResourcesBonus(resources, 3);
        List<String> players = new ArrayList<>();
        players.add("aldo");
        players.add("giovanni");
        players.add("giacomo");
        AbstractGameContext abstractGameContext = new InitializeAbstractGameContext();
        abstractGameContext.setGameManager(new GameManager(new GameRoom(), players));
        resourcesBonus.applyEffect(abstractGameContext);

    }

    class InitializeAbstractGameContext extends AbstractGameContext {

        @Override
        public Object interactWithPlayer(Object... args) throws IncorrectInputException, MarketBanException, OccupiedSlotException, NotEnoughResourcesException, NotEnoughMilitaryPoints, InvalidLeaderCardAction, CardTypeNumLimitReachedException {
            return null;
        }
    }

}