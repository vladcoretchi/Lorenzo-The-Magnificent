package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.SpecialContexts.FamilyMemberSelectionContext;
import it.polimi.ingsw.LM34.Controller.NonInteractableContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.ActionSlot;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Effects.ResourceRelatedBonus.ResourcesBonus;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurator;

import java.util.ArrayList;

import static it.polimi.ingsw.LM34.Enums.Controller.ContextType.*;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class HarvestAreaContext extends AbstractGameContext implements DiceDependentContextsInterface {
    private Integer tempValue;
    private WorkingArea harvestArea;


    public HarvestAreaContext() {
        contextType = HARVEST_AREA_CONTEXT;
        harvestArea = Configurator.getHarvestArea();
    }

    @Override
    public void interactWithPlayer(Player player) {
        tempValue = 0;
        //the player chooses the slot to occupy (highlight the difference
        // beetwen single slot and advanced slot)
        //TODO: now values of dices are increased
        //TODO: player chooses the action slot

        //The reward of the slots are add to the player
        //ActionSlot slotSelected = gameManager.getActivePlayerNetworkController().actionSlotSelection();
        //TODO: interact with player in action slot is necessary?
        ActionSlotContext actionSlotContext = ((ActionSlotContext)gameManager.getContextByType(ACTION_SLOT_CONTEXT));
        ActionSlot selectedSlot = actionSlotContext.actionSlotSelection(this, player);

        //TODO: now values of dices are increased
        FamilyMemberSelectionContext familyMemberSelectionContext = (FamilyMemberSelectionContext)getContextByType(FAMILY_MEMBER_SELECTION_CONTEXT);
        FamilyMember selectedPawn = familyMemberSelectionContext.familyMemberSelection(selectedSlot.getDiceValue(), player);
        Integer tempValue = selectedPawn.getValue();


        setChanged(); notifyObservers(tempValue); //observers do a setValue on it

        //harvestArea.getSingleSlot().getResourcesReward().applyEffect(this, player);
        ResourcesBonus harvestBonus = player.getPersonalBoard().getPersonalBonusTile().getHarvestBonus();
        player.getPersonalBoard().getBuildingCardOwned().
                forEach(card -> card.getPermanentBonus().applyEffect(this, player));
        ((ResourceIncomeContext)gameManager.getContextByType(RESOURCE_INCOME_CONTEXT)).handleResources(player, harvestBonus);
    }


    @Override
    public void sweep() {
        harvestArea.sweep();
    }

    @Override
    public ArrayList<ActionSlot> getActionSlots() {
        return harvestArea.getActionSlots();
    }


}
