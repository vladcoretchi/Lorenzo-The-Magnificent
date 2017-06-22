package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Player;
import java.util.List;

/**
 * Created by GiulioComi on 28/05/2017.
 */
public interface DiceDependentContextsInterface {

    public void sweep();

    public List<ActionSlot> getActionSlots();

    public void finalizeRewardAttribution(Player player);

}
