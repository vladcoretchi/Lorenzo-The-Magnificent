package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class HarvestAreaContext extends AbstractGameContext {
    private Integer tempValue;

    @Override
    public void interactWithPlayer(Player player) {
        //TODO: the player chooses the slot to occupy (highlight the difference beetwen single slot and advanced slot)
        setChanged();
        //TODO: now values of dices are increased
        //Utilities.getContextByType(contexts, ContextType.ACTION_SLOT_CONTEXT).initContext();
        //TODO: player chooses the familymember

        //TODO: now values of dices are increased
        FamilyMember memberChoosed = player.getFamilyMembers().get(1);
        FamilyMember tempMemberChoosed = memberChoosed.clone();
        //TODO: here we pass the family member chosed (only one)
        setChanged();
        notifyObservers(tempMemberChoosed);
        tempMemberChoosed.getValue();
        //TODO: tempValue= increasepawnsvalue.interactwithplayer();

    }

    @Override
    public ContextType getType() {
        return ContextType.HARVEST_AREA_CONTEXT;
    }

}
