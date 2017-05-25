package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.FamilyMember;
import it.polimi.ingsw.LM34.Model.Player;

import java.util.ArrayList;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class HarvestAreaContext extends AbstractGameContext {
    private ArrayList<FamilyMember> familyMember;

    public void initContext(Player player) {
        familyMember = player.getFamilyMembers();
        setChanged();
        notifyObservers(player.getFamilyMembers());
    }

    @Override
    public void interactWithPlayer(Player player) {
        //TODO: implement what player can do here and modify the model in this controller class
        //Utilities.getContextByType(contexts, ContextType.ACTION_SLOT_CONTEXT).initContext();
        //TODO: player chooses the familymember
        setChanged();
        //TODO: here we pass the family member chosed (only one)
        notifyObservers(player.getFamilyMembers());
        //TODO: now values of dices are increased
    }


    @Override
    public ContextType getType() {
        return ContextType.HARVEST_AREA_CONTEXT;
    }

}
