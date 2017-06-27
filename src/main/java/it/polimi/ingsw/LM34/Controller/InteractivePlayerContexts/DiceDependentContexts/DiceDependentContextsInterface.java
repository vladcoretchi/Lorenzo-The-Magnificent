package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.List;

public interface DiceDependentContextsInterface {

    public void sweep();

    public List<ActionSlot> getActionSlots();

    public void finalizeRewardAttribution(Player player);

}
