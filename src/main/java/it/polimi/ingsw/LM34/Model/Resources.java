package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Exception.Model.InvalidResourceTypeException;
import it.polimi.ingsw.LM34.Model.Enum.ResourceType;

import java.io.Serializable;

/**
 * Created by Giulio Comi on 03/04/2017.
 */
public class Resources implements Serializable {
    private Integer coins;
    private Integer woods;
    private Integer stones;
    private Integer servants;

    private Integer militaryPoints;
    private Integer faithPoints;
    private Integer victoryPoints;

    //this variable is used to manage a particular case...
    //better to remove it and think of another way to handle that special case
    private Integer mutualExclusion;

    //COSTRUTTORE CHIAMATO SIA NELL'INIZIALIZZAZIONE DEL GIOCO PER CIASCUN GIOCATORE, SIA PER RESTITUIRE COSTO RISORSE
    //IN MODO COMPATTO ;D

    //constructor for initializing all values to 0 (this comes handy in some situation)
    public Resources() {};

    //TODO: generate more overloading constructor
    public Resources(Integer coins, Integer woods, Integer stones, Integer servants, Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        this.coins= coins;
        this.stones= stones;
        this.woods= woods;
        this.servants= servants;
        this.militaryPoints = militaryPoints;
        this.faithPoints = faithPoints;
        this.victoryPoints = victoryPoints;
    }

    //overloading
    public Resources(Integer coinsRequired, Integer stonesRequired, Integer woodsRequired, Integer servantsRequired) {
        this.coins= coins;
        this.stones= stones;
        this.woods= woods;
        this.servants= servants;
        this.militaryPoints = 0;
        this.faithPoints = 0;
        this.victoryPoints = 0;
    }

    //modifyCoins allows both addition and subtraction of quantity
    public void modifyResourceByType(ResourceType resourceType, Integer quantity) throws InvalidResourceTypeException {
        switch (resourceType) {
            case WOODS:
                this.woods += quantity; break;
            case COINS:
                this.coins += quantity; break;
            case STONES:
                this.stones += quantity; break;
            case SERVANTS:
                this.servants += quantity; break;

            case MILITARY_POINTS:
                this.militaryPoints += quantity; break;
            case FAITH_POINTS:
                this.faithPoints += quantity; break;
            case VICTORY_POINTS:
                this.victoryPoints += quantity; break;
            default:
                throw new InvalidResourceTypeException();
        }
    }

    //this method allows the controller to retrieve just the information about the type of resource it needs in that moment
    public Integer getResourceByType(ResourceType resourceType) throws InvalidResourceTypeException {
        switch (resourceType) {
            case WOODS:
                return this.woods;
            case COINS:
                return this.coins;
            case STONES:
                return this.stones;
            case SERVANTS:
                return this.servants;

            case MILITARY_POINTS:
                return this.militaryPoints;
            case FAITH_POINTS:
                return this.faithPoints;
            case VICTORY_POINTS:
                return this.victoryPoints;
            default:
                throw new InvalidResourceTypeException();
        }
    }

    public Resources sumResources (Resources resources) throws InvalidResourceTypeException {
        this.woods += resources.getResourceByType(ResourceType.WOODS);
        this.coins += resources.getResourceByType(ResourceType.COINS);
        this.stones += resources.getResourceByType(ResourceType.STONES);
        this.servants += resources.getResourceByType(ResourceType.SERVANTS);
        this.militaryPoints += resources.getResourceByType(ResourceType.MILITARY_POINTS);
        this.faithPoints += resources.getResourceByType(ResourceType.FAITH_POINTS);
        this.victoryPoints += resources.getResourceByType(ResourceType.VICTORY_POINTS);

        return resources;
    }
}