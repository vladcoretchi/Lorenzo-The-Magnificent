package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class HarvestAreaContext extends AbstractGameContext {


    public void initContext(Player player) {
        setChanged();
        notifyObservers(player.getFamilyMembers());
    }


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
    public void initContext() {}

    @Override
    public ContextType getType() {
        return ContextType.HARVEST_AREA_CONTEXT;
    }


    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }
}
