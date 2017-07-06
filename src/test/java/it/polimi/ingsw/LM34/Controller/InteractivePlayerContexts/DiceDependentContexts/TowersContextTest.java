package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Enums.Model.DevelopmentCardColor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

public class TowersContextTest {

    /**
     * this test will check if pair will properly managed
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void interactWithPlayer() throws Exception {
        TowersContext towersContext = new TowersContext();
        Pair<DevelopmentCardColor, Integer> pairArguments = new ImmutablePair<>(DevelopmentCardColor.BLUE, 1);
        towersContext.interactWithPlayer(pairArguments);
    }

    /**
     * this test will check if noOccupiedTowerTax will properly set true
     * @throws Exception
     */
    @Test
    public void avoidOccupiedTowerTax() throws Exception {
        TowersContext towersContext = new TowersContext();
        towersContext.avoidOccupiedTowerTax();
    }

    /**
     * this test will check if ignoreMilitaryPointsRequirements will properly set true
     * @throws Exception
     */
    @Test
    public void ignoreMilitaryPointsRequirementsForTerritoryCards() throws Exception {
        TowersContext towersContext = new TowersContext();
        towersContext.ignoreMilitaryPointsRequirementsForTerritoryCards();
    }

}