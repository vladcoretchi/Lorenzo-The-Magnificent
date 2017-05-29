package it.polimi.ingsw.LM34.Controller.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Controller.GameManager;
import it.polimi.ingsw.LM34.Controller.SupportContexts.ResourceIncomeContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Model.Resources;
import it.polimi.ingsw.LM34.Utils.Configurations.Configurator;

/**
 * Created by GiulioComi on 24/05/2017.
 */
public class MarketAreaContext extends AbstractGameContext implements DiceDependentContextsInterface {
    private Integer tempValue;
    private Market market;

    @Override
    public ContextType getType() {
        return ContextType.MARKET_AREA_CONTEXT;
    }

public MarketAreaContext() { market = Configurator.getMarket(); }

    @Override
    public void interactWithPlayer(Player player) {
        //the player chooses the family member to place and where to place it
        //if(fm.getValue() >= market.getDiceValue())
        //try {
        // market.insertFamilyMember(fm);
        //marketSlot.getReward().applyEffect(player);
        //} catch(...) {
        //say that slot occupied and remake the player do his choices
        //TODO: correct: ?interactWithPlayer(player); //let the player redo his choices...

        System.out.println("siamo in market area context");
        ResourceIncomeContext incomeContext= (ResourceIncomeContext) GameManager.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
        incomeContext.handleResources(player, new Resources(2,4,5,5));

    }


    @Override
    public void increaseTempValue(Integer servantsConsumed) {
        tempValue += servantsConsumed;
    }

    public void sweep() {
        market.sweep();
    }
}
