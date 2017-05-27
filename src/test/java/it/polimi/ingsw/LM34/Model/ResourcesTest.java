package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by vladc on 5/23/2017.
 */
public class ResourcesTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testModifyByType() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);

        resources.modifyResourceByType(ResourceType.COINS, 1);
        assertEquals("Coins: expected 2", resources.getResourceByType(ResourceType.COINS).intValue(), 2);
        resources.modifyResourceByType(ResourceType.WOODS, 2);
        assertEquals("Woods: expected 3", resources.getResourceByType(ResourceType.WOODS).intValue(), 3);
        resources.modifyResourceByType(ResourceType.STONES, 3);
        assertEquals("Stones: expected 4", resources.getResourceByType(ResourceType.STONES).intValue(), 4);
        resources.modifyResourceByType(ResourceType.SERVANTS, -3);
        assertEquals("Servants: expected -2", resources.getResourceByType(ResourceType.SERVANTS).intValue(), -2);
        resources.modifyResourceByType(ResourceType.MILITARY_POINTS, 5);
        assertEquals("MilitaryPoints: expected 6", resources.getResourceByType(ResourceType.MILITARY_POINTS).intValue(), 6);
        resources.modifyResourceByType(ResourceType.FAITH_POINTS, -6);
        assertEquals("FaithPoints: expected -5", resources.getResourceByType(ResourceType.FAITH_POINTS).intValue(), -5);
        resources.modifyResourceByType(ResourceType.VICTORY_POINTS, 15);
        assertEquals("VictoryPoints: expected 16", resources.getResourceByType(ResourceType.VICTORY_POINTS).intValue(), 16);
    }

    @Test
    public void testModifyByTypeWithException1() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);

        exception.expect(NullPointerException.class);
        exception.reportMissingExceptionWithMessage("NullPointerException was not thrown");
        resources.modifyResourceByType(null, 2);
    }

    @Test
    public void testModifyByTypeWithException2() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);

        exception.expect(NullPointerException.class);
        exception.reportMissingExceptionWithMessage("NullPointerException was not thrown");
        resources.modifyResourceByType(ResourceType.COINS, null);
    }

    @Test
    public void testModifyByTypeWithException3() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);

        exception.expect(NullPointerException.class);
        exception.reportMissingExceptionWithMessage("NullPointerException was not thrown");
        resources.modifyResourceByType(null, null);
    }

    @Test
    public void testSumResources() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);
        Resources resourcesGoods = new Resources(1, 2, -20, 56);
        Resources resourcesPoints = new Resources(-5,2,3);

        resources.sumResources(resourcesGoods);
        assertEquals("Coins: expected 2", resources.getResourceByType(ResourceType.COINS).intValue(), 2);
        assertEquals("Woods: expected 3", resources.getResourceByType(ResourceType.WOODS).intValue(), 3);
        assertEquals("Stones: expected -19", resources.getResourceByType(ResourceType.STONES).intValue(), -19);
        assertEquals("Servants: expected 57", resources.getResourceByType(ResourceType.SERVANTS).intValue(), 57);

        resources.sumResources(resourcesPoints);
        assertEquals("MilitaryPoints: expected -4", resources.getResourceByType(ResourceType.MILITARY_POINTS).intValue(), -4);
        assertEquals("FaithPoints: expected 3", resources.getResourceByType(ResourceType.FAITH_POINTS).intValue(), 3);
        assertEquals("VictoryPoints: expected 4", resources.getResourceByType(ResourceType.VICTORY_POINTS).intValue(), 4);
    }

    @Test
    public void testSumResourcesWithException() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);
        exception.expect(NullPointerException.class);
        exception.reportMissingExceptionWithMessage("NullPointerException was not thrown");

        resources.sumResources(null);
    }
}
