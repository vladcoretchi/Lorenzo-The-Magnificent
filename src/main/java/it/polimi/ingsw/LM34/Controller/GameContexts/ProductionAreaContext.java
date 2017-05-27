package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ProductionAreaContext extends AbstractGameContext {
    private FamilyMember memberChoosed;
    private Integer tempValue;

    public void initContext(Player player) {
        setChanged();
        notifyObservers(player.getFamilyMembers());
    }


    @Override
    public void interactWithPlayer(Player player) {

        //TODO: the player chooses the slot to occupy (highlight the difference beetwen single slot and advanced slot)
        setChanged();
        //TODO: now values of dices are increased
        getContextByType(ContextType.ACTION_SLOT_CONTEXT).interactWithPlayer(player);
        //TODO: player chooses the familymember

        //TODO: now values of dices are increased
        FamilyMember memberChoosed = player.getFamilyMembers().get(1);
        FamilyMember tempMemberChoosed = memberChoosed.clone();
        //TODO: here we pass the family member chosed (only one)
        setChanged();
        notifyObservers(tempMemberChoosed);
        tempMemberChoosed.getValue();
    }


    @Override
    public ContextType getType() {
        return ContextType.PRODUCTION_AREA_CONTEXT;
    }


}
