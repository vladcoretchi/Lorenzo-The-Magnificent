package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 24/05/2017.
 */
public class MarketAreaContext extends AbstractGameContext {

    @Override
    public ContextType getType() {
        return ContextType.MARKET_AREA_CONTEXT;
    }


    @Override
    public void interactWithPlayer(Player player) {
        //the player chooses the family member to place and where to place it
        //if(fm.getValue() >= market.getDiceValue())
        //try {
        // market.insertFamilyMember(fm);
        //marketSlot.getReward().applyEffect(player);
        //} catch(...) {
        //say that slot occupied and remake the player do his choices
        //interactWithPlayer(player); //let the player redo his choices...

        //TODO: when the player choices to go back to the gameboard view...
        //endContext();
    }

    public void endContext() {
        //turnContext.interactWithPlayer();
    }
}
