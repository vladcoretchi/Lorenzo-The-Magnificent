package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.ResourceType;
import it.polimi.ingsw.LM34.Utils.Utilities;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Giulio Comi on 03/04/2017.
 */
public class Resources implements Serializable {
    private Map<ResourceType, Integer> resources;

    //constructor for initializing all values to 0 (this comes handy in some situation)
    public Resources() {
        resources = new HashMap<>();
    }

    public Resources(Integer coins, Integer woods, Integer stones, Integer servants, Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        resources = new HashMap<>();

        this.resources.put(ResourceType.COINS, coins);
        this.resources.put(ResourceType.WOODS, woods);
        this.resources.put(ResourceType.STONES, stones);
        this.resources.put(ResourceType.SERVANTS, servants);
        this.resources.put(ResourceType.MILITARY_POINTS, militaryPoints);
        this.resources.put(ResourceType.FAITH_POINTS, faithPoints);
        this.resources.put(ResourceType.VICTORY_POINTS, victoryPoints);
    }

    public Resources(Integer coins, Integer woods, Integer stones, Integer servants) {
        resources = new HashMap<>();

        this.resources.put(ResourceType.COINS, coins);
        this.resources.put(ResourceType.WOODS, woods);
        this.resources.put(ResourceType.STONES, stones);
        this.resources.put(ResourceType.SERVANTS, servants);
    }

    public Resources(Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        resources = new HashMap<>();

        this.resources.put(ResourceType.MILITARY_POINTS, militaryPoints);
        this.resources.put(ResourceType.FAITH_POINTS, faithPoints);
        this.resources.put(ResourceType.VICTORY_POINTS, victoryPoints);
    }

    public Map<ResourceType, Integer> getResources() {
        return this.resources;
    }

    //this method allows the controller to retrieve just the information about the type of resource it needs in that moment
    public Integer getResourceByType(ResourceType resourceType) {
        if (resourceType == null) throw new NullPointerException();

        return this.resources.getOrDefault(resourceType, 0);
    }

    //allows both addition and subtraction of quantity
    public void modifyResourceByType(ResourceType resourceType, Integer quantity) {
        if (resourceType == null || quantity == null) throw new NullPointerException();

        this.resources.merge(resourceType, quantity, Utilities.sumInteger);
        if (this.resources.get(resourceType) == 0) this.resources.remove(resourceType);
    }

    //this method also handles subtraction as negative number of resources
    public void sumResources (Resources res) {
        if (res == null) throw new NullPointerException();

        for (Map.Entry<ResourceType, Integer> resEntry : res.getResources().entrySet())
            this.modifyResourceByType(resEntry.getKey(), resEntry.getValue());
    }
}