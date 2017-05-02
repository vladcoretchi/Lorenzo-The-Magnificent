package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Exception.Model.InvalidResourceTypeException;
import it.polimi.ingsw.LM34.Model.Enum.ResourceType;

/**
 * Created by Giulio Comi on 5/2/2017.
 */
public class Resources {
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
    public Resources(Integer coins, Integer woods, Integer stones, Integer servants, Integer militaryPoints, Integer faithPoints, Integer victoryPoints) {
        this.coins = coins;
        this.woods = woods;
        this.stones = stones;
        this.servants = servants;

        this.militaryPoints = militaryPoints;
        this.faithPoints = faithPoints;
        this.victoryPoints = victoryPoints;
    }

    //modifyCoins allows both addition and subtraction of quantity
    public void modifyResourceByType(ResourceType resourceType, Integer quantity) throws InvalidResourceTypeException {
        if (!(resourceType instanceof ResourceType))
            throw new InvalidResourceTypeException();

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
                return this.woods; //break;
            case COINS:
                return this.coins; //break;
            case STONES:
                return this.stones; //break;
            case SERVANTS:
                return this.servants; //break;

            case MILITARY_POINTS:
                return this.militaryPoints; //break;
            case FAITH_POINTS:
                return this.faithPoints; //break;
            case VICTORY_POINTS:
                return this.victoryPoints; //break;
            default:
                throw new InvalidResourceTypeException();
        }
    }
}