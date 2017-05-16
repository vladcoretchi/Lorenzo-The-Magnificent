package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Model.Enums.ResourceType;
import it.polimi.ingsw.LM34.Utils.ArraySum;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Giulio Comi on 03/04/2017.
 */
public class Resources implements Serializable {
    private Integer resources[];

    //constructor for initializing all values to 0 (this comes handy in some situation)
    public Resources() {
        resources = new Integer[ResourceType.values().length];
        Arrays.fill(resources, 0);
    }

    public Resources(Integer coins, Integer woods, Integer stones, Integer servants, Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        // array initialization
        this();

        this.resources[ResourceType.COINS.ordinal()] = coins;
        this.resources[ResourceType.WOODS.ordinal()] = woods;
        this.resources[ResourceType.STONES.ordinal()] = stones;
        this.resources[ResourceType.SERVANTS.ordinal()] = servants;
        this.resources[ResourceType.MILITARY_POINTS.ordinal()] = militaryPoints;
        this.resources[ResourceType.FAITH_POINTS.ordinal()] = faithPoints;
        this.resources[ResourceType.VICTORY_POINTS.ordinal()] = victoryPoints;
    }

    public Resources(Integer coins, Integer woods, Integer stones, Integer servants) {
        // array initialization
        this();

        this.resources[ResourceType.COINS.ordinal()] = coins;
        this.resources[ResourceType.WOODS.ordinal()] = woods;
        this.resources[ResourceType.STONES.ordinal()] = stones;
        this.resources[ResourceType.SERVANTS.ordinal()] = servants;
    }

    public Resources(Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        // array initialization
        this();

        this.resources[ResourceType.MILITARY_POINTS.ordinal()] = militaryPoints;
        this.resources[ResourceType.FAITH_POINTS.ordinal()] = faithPoints;
        this.resources[ResourceType.VICTORY_POINTS.ordinal()] = victoryPoints;
    }

    public Integer[] getResources() {
        return this.resources;
    }

    //this method allows the controller to retrieve just the information about the type of resource it needs in that moment
    public Integer getResourceByType(ResourceType resourceType) {
        return this.resources[resourceType.ordinal()];
    }

    //allows both addition and subtraction of quantity
    public void modifyResourceByType(ResourceType resourceType, Integer quantity) {
        this.resources[resourceType.ordinal()] = quantity;
    }

    //this method also handles subtraction as negative number of resources
    public void sumResources (Resources res) {
        this.resources = ArraySum.sumElementByElement(this.resources, res.getResources());
    }
}