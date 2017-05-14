package it.polimi.ingsw.LM34.Model.Bonus;

/**
 * Created by vladc on 5/14/2017.
 */

//TODO: rename (at least)
public class OtherExcomunicationTilesPenalties implements  EffectInterface {
    private Boolean skipFirstTurn;
    private Integer servantsPerActionValue;

    public OtherExcomunicationTilesPenalties(Boolean skipFirstTurn) {
        this.skipFirstTurn = skipFirstTurn;
        this.servantsPerActionValue = null;
    }

    public OtherExcomunicationTilesPenalties(Integer servantsPerActionValue) {
        this.skipFirstTurn = false;
        this.servantsPerActionValue = servantsPerActionValue;
    }

    public Boolean hasToSkipFirstTurn() {
        return this.skipFirstTurn;
    }

    public Integer getServantsPerActionValue() {
        return this.servantsPerActionValue;
    }
}
