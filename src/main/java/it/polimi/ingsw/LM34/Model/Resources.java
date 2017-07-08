package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Utils.Copyable;
import it.polimi.ingsw.LM34.Utils.Utilities;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

import static it.polimi.ingsw.LM34.Enums.Model.ResourceType.*;

public class Resources implements Serializable, Copyable {
    private static final long serialVersionUID = 8305447519327637463L;

    private Map<ResourceType, Integer> resourcesMap;

    public Resources() {
        resourcesMap = new EnumMap<>(ResourceType.class);
    }

    public Resources(Integer coins, Integer woods, Integer stones, Integer servants,
                     Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {

        resourcesMap = new EnumMap<>(ResourceType.class);

        if(coins != 0)
            this.resourcesMap.put(ResourceType.COINS, coins);
        if(woods != 0)
            this.resourcesMap.put(ResourceType.WOODS, woods);
        if(stones != 0)
            this.resourcesMap.put(ResourceType.STONES, stones);
        if(servants != 0)
            this.resourcesMap.put(ResourceType.SERVANTS, servants);
        if(militaryPoints != 0)
            this.resourcesMap.put(ResourceType.MILITARY_POINTS, militaryPoints);
        if(faithPoints != 0)
            this.resourcesMap.put(ResourceType.FAITH_POINTS, faithPoints);
        if(victoryPoints != 0)
            this.resourcesMap.put(ResourceType.VICTORY_POINTS, victoryPoints);
    }

    /**
     * Constructor for the 4 type of material resources
     * @param coins
     * @param woods
     * @param stones
     * @param servants
     */
    public Resources(Integer coins, Integer woods, Integer stones, Integer servants) {
        resourcesMap = new EnumMap<>(ResourceType.class);

        if(coins != 0)
            this.resourcesMap.put(ResourceType.COINS, coins);
        if(woods != 0)
            this.resourcesMap.put(ResourceType.WOODS, woods);
        if(stones != 0)
            this.resourcesMap.put(ResourceType.STONES, stones);
        if(servants != 0)
            this.resourcesMap.put(ResourceType.SERVANTS, servants);
    }

    /**
     * Constructor dedicated only to the points
     * @param militaryPoints
     * @param faithPoints
     * @param victoryPoints
     */
    public Resources(Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        resourcesMap = new EnumMap<>(ResourceType.class);

        if(militaryPoints != null)
            this.resourcesMap.put(ResourceType.MILITARY_POINTS, militaryPoints);
        if(faithPoints != null)
            this.resourcesMap.put(ResourceType.FAITH_POINTS, faithPoints);
        if(victoryPoints != null)
            this.resourcesMap.put(ResourceType.VICTORY_POINTS, victoryPoints);
    }

    public Resources(Map<ResourceType, Integer> resourcesMap) {
        this.resourcesMap = resourcesMap;
    }

    public Map<ResourceType, Integer> getResources() {
        return this.resourcesMap;
    }

    //this method allows the controller to retrieve just the information about the type of resource it needs in that moment
    public Integer getResourceByType(ResourceType resourceType) {
        if (resourceType != null)
            return this.resourcesMap.getOrDefault(resourceType, 0);
        else
            return 0;
    }

    //allows both addition and subtraction of quantity
    public void sumResourceType(ResourceType resourceType, Integer quantity) {
        if (resourceType != null && quantity != null) {
            this.resourcesMap.merge(resourceType, quantity, Utilities.sumInteger);
            if (this.resourcesMap.get(resourceType) == 0)
                this.resourcesMap.remove(resourceType);
        }
    }

    public void subResourceType(ResourceType resourceType, Integer quantity) {
        if (resourceType != null && quantity != null) {
            this.resourcesMap.merge(resourceType, quantity, Utilities.subInteger);
            if (this.resourcesMap.get(resourceType) == 0)
                this.resourcesMap.remove(resourceType);
        }
    }

    //this method handles addition of resourcesMap
    public void sumResources (Resources res) {
        if (res != null)
            res.getResources().forEach(this::sumResourceType);
    }


    //this method handles subtraction as negative number of resourcesMap
    public void subResources (Resources res) {
        if (res != null)
            res.getResources().forEach(this::subResourceType);
    }

    //this method also handles the multiplication of resourcesMap
    public void multiplyResources (Integer multiplier) {
        if (multiplier != null)
            for(ResourceType type : ResourceType.values())
                if(this.resourcesMap.containsKey(type)) {
                    this.resourcesMap.merge(type, multiplier, Utilities.multiplyInteger);
                }
    }

    public Boolean hasEnough(Resources res) {
        if(res == null || res.getResources() == null || res.getResources().isEmpty())
            return true;

        Iterator it = res.getResources().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
            if(this.getResourceByType((ResourceType) entry.getKey()) < (Integer) entry.getValue())
                return false;
        }

        return true;
    }

    @Override
    public Resources copy() {
        return new Resources(
                this.getResourceByType(COINS).intValue(),
                this.getResourceByType(WOODS).intValue(),
                this.getResourceByType(STONES).intValue(),
                this.getResourceByType(SERVANTS).intValue(),
                this.getResourceByType(MILITARY_POINTS).intValue(),
                this.getResourceByType(FAITH_POINTS).intValue(),
                this.getResourceByType(VICTORY_POINTS).intValue());
    }
}