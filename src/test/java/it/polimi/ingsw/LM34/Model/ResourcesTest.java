package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class ResourcesTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void ModifyByType() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);

        resources.sumResourceType(ResourceType.COINS, 1);
        assertEquals("Coins: expected 2", resources.getResourceByType(ResourceType.COINS).intValue(), 2);
        resources.sumResourceType(ResourceType.WOODS, 2);
        assertEquals("Woods: expected 3", resources.getResourceByType(ResourceType.WOODS).intValue(), 3);
        resources.sumResourceType(ResourceType.STONES, 3);
        assertEquals("Stones: expected 4", resources.getResourceByType(ResourceType.STONES).intValue(), 4);
        resources.sumResourceType(ResourceType.SERVANTS, -3);
        assertEquals("Servants: expected -2", resources.getResourceByType(ResourceType.SERVANTS).intValue(), -2);
        resources.sumResourceType(ResourceType.MILITARY_POINTS, 5);
        assertEquals("MilitaryPoints: expected 6", resources.getResourceByType(ResourceType.MILITARY_POINTS).intValue(), 6);
        resources.sumResourceType(ResourceType.FAITH_POINTS, -6);
        assertEquals("FaithPoints: expected -5", resources.getResourceByType(ResourceType.FAITH_POINTS).intValue(), -5);
        resources.sumResourceType(ResourceType.VICTORY_POINTS, 15);
        assertEquals("VictoryPoints: expected 16", resources.getResourceByType(ResourceType.VICTORY_POINTS).intValue(), 16);
    }

    @Test
    public void ModifyByTypeWithException1() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);

        exception.expect(NullPointerException.class);
        exception.reportMissingExceptionWithMessage("NullPointerException was not thrown");
        resources.sumResourceType(null, 2);
    }

    @Test
    public void ModifyByTypeWithException2() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);

        exception.expect(NullPointerException.class);
        exception.reportMissingExceptionWithMessage("NullPointerException was not thrown");
        resources.sumResourceType(ResourceType.COINS, null);
    }

    @Test
    public void ModifyByTypeWithException3() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);

        exception.expect(NullPointerException.class);
        exception.reportMissingExceptionWithMessage("NullPointerException was not thrown");
        resources.sumResourceType(null, null);
    }

    @Test
    public void SumResources() {
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
    public void SumResourcesWithException() {
        Resources resources = new Resources(1, 1, 1, 1, 1, 1, 1);
        exception.expect(NullPointerException.class);
        exception.reportMissingExceptionWithMessage("NullPointerException was not thrown");

        resources.sumResources(null);
    }
}