package it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.NonInteractiveContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import it.polimi.ingsw.LM34.Enums.Model.PawnColor;
import it.polimi.ingsw.LM34.Exceptions.Controller.*;
import it.polimi.ingsw.LM34.Exceptions.Model.OccupiedSlotException;
import it.polimi.ingsw.LM34.Exceptions.Validation.IncorrectInputException;
import it.polimi.ingsw.LM34.Model.Boards.PlayerBoard.PersonalBoard;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourcesPerItemBonusTest {

    /**
     * this test will check if will be possible to apply card's effect to player's resources
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void applyEffect() throws Exception {
        Resources resources = new Resources(1,1,1,1,1,1,1);
       ResourcesPerItemBonus resourcesPerItemBonus = new ResourcesPerItemBonus(resources, DevelopmentCardColor.GREEN);
       AbstractGameContext abstractGameContext = new InitializeAbstractGameContext();
       resourcesPerItemBonus.applyEffect(abstractGameContext);

    }

    class InitializeAbstractGameContext extends AbstractGameContext {

        @Override
        public Object interactWithPlayer(Object... args) throws IncorrectInputException, MarketBanException, OccupiedSlotException, NotEnoughResourcesException, NotEnoughMilitaryPoints, InvalidLeaderCardAction, CardTypeNumLimitReachedException {
            return null;
        }
    }

}