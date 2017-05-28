package it.polimi.ingsw.LM34.Controller.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ProductionAreaContext extends AbstractGameContext implements DiceDependentContextsInterface {
    private FamilyMember memberChoosed;
    private Integer tempValue;

    public void initContext(Player player) {
        setChanged();
        notifyObservers(player.getFamilyMembers());
    }

    public ProductionAreaContext() {}

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
        notifyObservers(tempMemberChoosed);  //observers do a setValue on it
        tempMemberChoosed.getValue();
    }


    @Override
    public ContextType getType() {
        return ContextType.PRODUCTION_AREA_CONTEXT;
    }

    @Override
    public void increaseTempValue(Integer servantsConsumed) {
        tempValue += servantsConsumed;
    }
}
