package it.polimi.ingsw.LM34.Model.Board.PlayerBoard;

import it.polimi.ingsw.LM34.Model.Bonus.PermanentBonus;

/**
 * Created by Giulio Comi on 03/05/2017.
 */

public class BonusTile {
private PermanentBonus productionBonus;
private PermanentBonus harvestBonus;


//to be initialized before the starting of the game with the bonus values reported in the bonus tiles
    public BonusTile(PermanentBonus pb, PermanentBonus hb) {
	this.productionBonus= pb;
	this.harvestBonus= hb;
    }

    public void setProductionBonus(PermanentBonus b) {

    }

    public void setHarvestBonus(PermanentBonus b) {
    }


}