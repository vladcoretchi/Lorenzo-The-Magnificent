package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourcesTest {

    /**
     * this test will check if, when resourceType or quantity is null, the NullPointerException will be properly thrown
     * @throws Exception
     */
    @Test
    public void sumResourceTypeShouldThrowsException() throws Exception {
        Resources resources = new Resources(1,1,1,1,1,1,1);

        try {
            resources.sumResourceType(null, 1);
            fail("NullPointerException was not occured when resourceType is null");
        }
        catch (NullPointerException ex) {

        }

        try {
            resources.sumResourceType(ResourceType.SERVANTS, null);
            fail("NullPointerExcpetion was not occured when quantity is null");
        }
        catch (NullPointerException ex) {

        }

        try {
            resources.sumResourceType(null, null);
            fail("NullPointerException was not occured when both resourceType and quantity are null");
        }
        catch (NullPointerException ex) {

        }

    }

    /**
     * this test will check if the addiction or subtraction between resources will be properly executed
     */
    @Test
    public void sumResourceType() {
        Resources resources = new Resources(0,0,0,0,0,0,0);

        for (ResourceType resourceType : ResourceType.values())
            resources.sumResourceType(resourceType, 1);

        for(ResourceType resourceType : ResourceType.values())
            assertEquals(1, resources.getResourceByType(resourceType).longValue());

        for (ResourceType resourceType : ResourceType.values())
            resources.sumResourceType(resourceType, -1);

        for(ResourceType resourceType : ResourceType.values())
            assertEquals(0, resources.getResourceByType(resourceType).longValue());

        for (ResourceType resourceType : ResourceType.values())
            resources.sumResourceType(resourceType, -1);

        for(ResourceType resourceType : ResourceType.values())
            assertEquals(-1, resources.getResourceByType(resourceType).longValue());
    }

    /**
     * this test will check if, when all resources are 0, the NullPointerException will be properly thrown
     * @throws Exception
     */
    @Test(expected = NullPointerException.class)
    public void sumResourcesShouldThrowsException() throws Exception {
        Resources resources = new Resources(0,0,0,0,0,0,0);
        resources.sumResources(null);
    }

    /**
     * this test will check if all addictions between resources will be properly done
     */
    @Test
    public void sumResources() {
        Resources resources = new Resources(0,0,0,0,0,0,0);
        Resources addendPositive = new Resources(1,1,1,1,1,1,1);
        Resources addendNegative = new Resources(-1, -1, -1, -1, -1, -1, -1);
        Resources addendEmpty = new Resources(0,0,0,0,0,0,0);


        //test if 0 + 0 == 0
        resources.sumResources(addendEmpty);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(0, resources.getResourceByType(resourceType).longValue());

        //test if 0 + 1 == 1
        resources.sumResources(addendPositive);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(1, resources.getResourceByType(resourceType).longValue());

        //resources == 1 after previous sum. So, by adding -1, resources will return == 0
        resources.sumResources(addendNegative);

        //test if 0 - 1 == -1
        resources.sumResources(addendNegative);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(-1, resources.getResourceByType(resourceType).longValue());



        //resources == -1 after previous sum. So, by adding 1 twice, resources will return == 1
        resources.sumResources(addendPositive);
        resources.sumResources(addendPositive);

        //test if 1 + 1 == 2
        resources.sumResources(addendPositive);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(2, resources.getResourceByType(resourceType).longValue());

        //resources == 2 after previous sum. So, by adding -1, resources will return == 1
        resources.sumResources(addendNegative);

        //test if 1 - 1 == 0
        resources.sumResources(addendNegative);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(0, resources.getResourceByType(resourceType).longValue());

        //resources == 0 after previous sum. So, by adding 1, resources will return == 1
        resources.sumResources(addendPositive);

        //test if 1 + 0 = 1
        resources.sumResources(addendEmpty);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(1, resources.getResourceByType(resourceType).longValue());



        //resources == 1 after previous sum. So, by adding -1 twice, resources will return == -1
        resources.sumResources(addendNegative);
        resources.sumResources(addendNegative);

        //test if -1 - 1 == -2
        resources.sumResources(addendNegative);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(-2, resources.getResourceByType(resourceType).longValue());

        //resources == -2 after previous sum. So, by adding 1, resources will return == -1
        resources.sumResources(addendPositive);

        //test if -1 + 0 == -1
        resources.sumResources(addendEmpty);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(-1, resources.getResourceByType(resourceType).longValue());

        //test if -1 + 1 == 0
        resources.sumResources(addendPositive);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(0, resources.getResourceByType(resourceType).longValue());



    }

    /**
     * this test will check if, when multiplier is null, the NullPointerException will be properly thrown
     * @throws Exception NullPointerException if multiplier is null
     */
    @Test(expected = NullPointerException.class)
    public void multiplyResourcesShouldThrowsException() throws Exception {
        Resources resources = new Resources(0,0,0,0,0,0,0);
        resources.multiplyResources(null);
    }

    /**
     * this test will check if all multiplies between Resources will be properly thrown
     * @throws Exception
     */
    @Test
    public void multiplyResources() {
        Resources resources = new Resources(1,1,1,1,1,1,1);

        //test if 1 * 1 == 1
        resources.multiplyResources(1);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(1, resources.getResourceByType(resourceType).longValue());

        //test if 1 * -1 == -1
        resources.multiplyResources(-1);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(-1, resources.getResourceByType(resourceType).longValue());

        //test if -1 * 0 == 0
        resources.multiplyResources(0);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(0, resources.getResourceByType(resourceType).longValue());

        //test if 0 * 0 == 0
        resources.multiplyResources(0);
        for(ResourceType resourceType : ResourceType.values())
            assertEquals(0, resources.getResourceByType(resourceType).longValue());

    }

    /**
     * this test will check if, when player has enough resources, will be properly returned true, otherwise false
     */
    @Test
    public void hasEnough() {
        Resources resources = new Resources(-1,-1,-1,-1,-1,-1,-1);
        Resources addendNegative = new Resources(-1, -1,-1, -1, -1, -1,-1);
        Resources resourcedRequested = new Resources(-2,-2,-2,-2,-2,-2,-2);

        assertTrue(resources.hasEnough(resourcedRequested));

        //resources will value -2
        resources.sumResources(addendNegative);

        assertTrue(resources.hasEnough(resourcedRequested));

        //resources will value -3
        resources.sumResources(addendNegative);

        assertFalse(resources.hasEnough(resourcedRequested));

    }

}