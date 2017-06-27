package it.polimi.ingsw.LM34.Model;

import it.polimi.ingsw.LM34.Enums.Model.DiceColor;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {
    private Random rand = new Random();
    private final DiceColor color;
    private Integer value = rand.nextInt(6) + 1;

    public Dice(DiceColor color) {
        this.color = color;
    }

    public DiceColor getColor() {
        return this.color;
    }

    public Integer getValue() {
        return value;
    }

    /**
     *Method used to set a new value for the Dice; this solution is preferred for sake of simplicity and integrity
     *against passing the new value from outside this class
     */
    public void rollDice(){
        this.value = rand.nextInt(6) + 1;
    }
}
