package model.board;

import java.util.ArrayList;

//TODO: apply Singleton design pattern
public class CouncilPalace {
private ArrayList<FamilyMember> occupyingPawns; //FamilyMembers in the palace
private final Integer diceValue; //minimum value to place FamilyMembers in the market space


public ArrayList<ActionSlot> getActionSlots() {
    return this.marketSlots;
}

//costructor called only at the beginning of the game
//this is a space where configuration are loaded from file, so there must not be variables with hardcoded values...
public CouncilPalace(Integer diceValue) {
    this.diceValue= diceValue;
    occypyingPawns= new ArrayList<FamilyMember>();
}

public void insertFamilyMember(FamilyMember fm) {
    occupyingPawns.add(fm);
}

public void sweepPalace() {
    occupyingPawns.clear();
}

public boolean isOccupied() {
	return occupyingPawns.isEmpty;
}

//return the order of the players in the next turn
public ArrayList<PawnColor> getNextTurnOrder() {
Arraylist<PawnColor> tempOrder= new ArrayList<PawnColor>();
	foreach (fm in occupyingPawns)
		if(!occupyingPawns.contains(fm))
			tempOrder.add(fm);
	return tempOrder;
	

			