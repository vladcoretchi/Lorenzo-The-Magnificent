package it.polimi.ingsw.LM34.Model.Board.GameBoard;

import java.util.ArrayList;

/**
 * Created by Giulio Comi on 03/05/2017.
 */
//TODO: evaluate to let the GameManager hold the Game Spaces in addition to other model components
//TODO: apply Singleton design pattern
//TODO: perhaps this class complicates things without any advantages
/*this class has the aim to aggregate the spaces of the gameboard in a organized way*/
public class GameBoard {
private Market market;
private CouncilPalace councilPalace;
private ArrayList<Towers> towers;
private HarvestArea harvestArea;
private ProductionArea productionArea;

//TODO: is this a good idea to return the subparts of the gameboard?
public GameSpace getGameSpace(GameSpace gs) {
    return gs.getSpace();
}


public GameBoard(Market m, CouncilPalace cp, ArrayList<Towers> ts, HarvestArea ha, ProductionArea pa) {
    market=m;
    councilPalace= cp;
    towers= ts;
    harvestArea= ha;
    productionArea= pa;
}

}
