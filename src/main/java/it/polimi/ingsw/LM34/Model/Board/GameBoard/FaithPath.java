package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: apply Singleton design pattern
public class FaithPath implements GameSpace {

    private ArrayList<Integer> positionValues;
    private Iterator iterator = positionValues.iterator();

    //TODO: evaluate to implement with hashmap O(1) instead of arraylist
    //the arraylist passed as parameter must be setup during game configuration loading
    public FaithPath(ArrayList<Integer> valuesPerPosition) {
        this.positionValues = valuesPerPosition;
    }

    //this method is called during Vatican Reports to know how many VictoryPoints a player deserves
    //TODO: here or in controller it must be handled if a player wants to be excommunicated (in that case infact he does not deserve the victory points reward)
    public Integer getPlayerPoints(Player player, Integer faithPoints) {
        positionValues.iterator();
        Integer index;
        //TODO: restyling this for
        for (index=0; faithPoints > 0 && iterator.hasNext(); index++,faithPoints--){
                iterator.next();
            }
            return positionValues.get(index).intValue();
    }

    public GameSpace getSpace() {
        return this;
    }
}

