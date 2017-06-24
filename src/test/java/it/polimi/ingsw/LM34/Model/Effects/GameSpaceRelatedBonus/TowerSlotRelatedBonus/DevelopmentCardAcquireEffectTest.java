package it.polimi.ingsw.LM34.Model.Effects.GameSpaceRelatedBonus.TowerSlotRelatedBonus;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import org.junit.Test;
import static org.junit.Assert.*;

public class DevelopmentCardAcquireEffectTest {

    private DevelopmentCardAcquireEffect developmentCardAcquireEffect = new DevelopmentCardAcquireEffect(DevelopmentCardColor.BLUE, 3, true);
    private AbstractGameContext callerContext;

    @Test(expected = NullPointerException.class)
    public void applyEffectTest() throws NullPointerException {
        AbstractGameContext towerContext = callerContext.getContextByType(ContextType.TOWERS_CONTEXT);
    }
}
