package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ProductionAreaContext extends AbstractGameContext implements DiceDependentContextsInterface {
    private FamilyMember memberChoosed;
    private Integer tempValue;
    private WorkingArea productionArea;

    public void initContext(Player player) {
        setChanged();
        notifyObservers(player.getFamilyMembers());
    }

    /*CONSTRUCTOR*/
    public ProductionAreaContext() {
        contextType = ContextType.PRODUCTION_AREA_CONTEXT;
        Configurator.getProductionArea();//this.gameManager.getPlayers().size());
    }

    @Override
    public void interactWithPlayer() {

        //TODO: the player chooses the slot to occupy (highlight the difference beetwen single slot and advanced slot)



        //TODO: now values of dices are increased
        gameManager.getContextByType(ContextType.ACTION_SLOT_CONTEXT).interactWithPlayer();
        //TODO: player chooses the familymember

        //TODO: now values of dices are increased
        FamilyMember memberChoosed = gameManager.getCurrentPlayer().getFamilyMembers().get(1);
        FamilyMember tempMemberChoosed = memberChoosed.clone();
        //TODO: here we pass the family member chosed (only one)
        setChanged();
        notifyObservers(tempMemberChoosed);  //observers do a setValue on it
        tempMemberChoosed.getValue();
    }


    @Override
    public List<ActionSlot> getActionSlots() {
        return productionArea.getActionSlots();
    }

    @Override
    public void finalizeRewardAttribution(Player player) {

    }

    @Override
    public void sweep() {
        productionArea.sweep();
    }


}
