package it.polimi.ingsw.LM34.Model.Board.PlayerBoard;

/**
 * Created by Giulio Comi on 03/05/2017.
 */

public class BonusTile {
private Bonus productionBonus;
private Bonus harvestBonus;


//to be initialized before the starting of the game with the bonus values reported in the bonus tiles
    public BonusTile(Bonus pb, Bonus hb) {
	this.productionBonus= pb;
	this.harvestBonus= hb;
    }

    public void setProductionBonus(Bonus b) {

    }

    public void setHarvestBonus(Bonus b) {
    }


}