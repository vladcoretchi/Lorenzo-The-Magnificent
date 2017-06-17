package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Utils.Utilities;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Giulio Comi on 03/04/2017.
 */
public class Resources implements Serializable {
    private static final long serialVersionUID = 8305447519327637463L;

    private Map<ResourceType, Integer> resourcesMap;

    //constructor for initializing all values to 0 (this comes handy in some situation)
    public Resources() {
        resourcesMap = new EnumMap<>(ResourceType.class);
    }

    public Resources(Integer coins, Integer woods, Integer stones, Integer servants, Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        resourcesMap = new EnumMap<>(ResourceType.class);

        if(coins > 0)
            this.resourcesMap.put(ResourceType.COINS, coins);
        if(woods > 0)
            this.resourcesMap.put(ResourceType.WOODS, woods);
        if(stones > 0)
            this.resourcesMap.put(ResourceType.STONES, stones);
        if(servants > 0)
            this.resourcesMap.put(ResourceType.SERVANTS, servants);
        if(militaryPoints > 0)
            this.resourcesMap.put(ResourceType.MILITARY_POINTS, militaryPoints);
        if(faithPoints > 0)
            this.resourcesMap.put(ResourceType.FAITH_POINTS, faithPoints);
        if(victoryPoints > 0)
            this.resourcesMap.put(ResourceType.VICTORY_POINTS, victoryPoints);
    }

    public Resources(Integer coins, Integer woods, Integer stones, Integer servants) {
        resourcesMap = new EnumMap<>(ResourceType.class);

        if(coins > 0)
            this.resourcesMap.put(ResourceType.COINS, coins);
        if(woods > 0)
            this.resourcesMap.put(ResourceType.WOODS, woods);
        if(stones > 0)
            this.resourcesMap.put(ResourceType.STONES, stones);
        if(servants > 0)
            this.resourcesMap.put(ResourceType.SERVANTS, servants);
    }

    public Resources(Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        resourcesMap = new EnumMap<>(ResourceType.class);

        if(militaryPoints > 0)
            this.resourcesMap.put(ResourceType.MILITARY_POINTS, militaryPoints);
        if(faithPoints > 0)
            this.resourcesMap.put(ResourceType.FAITH_POINTS, faithPoints);
        if(victoryPoints > 0)
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
        if (resourceType == null)
            throw new NullPointerException();

        return this.resourcesMap.getOrDefault(resourceType, 0);
    }

    //allows both addition and subtraction of quantity
    public void modifyResourceByType(ResourceType resourceType, Integer quantity) {
        if (resourceType == null || quantity == null)
            throw new NullPointerException();

        this.resourcesMap.merge(resourceType, quantity, Utilities.sumInteger);
        if (this.resourcesMap.get(resourceType) == 0)
            this.resourcesMap.remove(resourceType);
    }

    //this method also handles subtraction as negative number of resourcesMap
    public void sumResources (Resources res) {
        if (res == null)
            throw new NullPointerException();

        res.getResources().forEach(this::modifyResourceByType);
    }

    public void subResources (Resources res) {
        if (res == null)
            throw new NullPointerException();

        res.getResources().forEach(this::modifyResourceByType);
    }

    public void multiplyResources (Integer multiplier) {
        if (multiplier == null)
            throw new NullPointerException();

        this.resourcesMap.forEach((type, value) -> value *= multiplier);
    }
}