package it.polimi.ingsw.LM34.Controller.InteractivePlayerContexts.DiceDependentContexts;

import it.polimi.ingsw.LM34.Controller.AbstractGameContext;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Player;
import it.polimi.ingsw.LM34.Utils.Configurator;

/**
 * Created by GiulioComi on 24/05/2017.
 */
public class MarketAreaContext extends AbstractGameContext implements DiceDependentContextsInterface {
    private Market market;
    private Integer tempValue;

    private Boolean ban;


public MarketAreaContext() {
    contextType = ContextType.MARKET_AREA_CONTEXT;
    market = Configurator.getMarket(); }

    @Override
    public void interactWithPlayer(Player player) {
        System.out.println("siamo in market area context");
        setChanged();
        notifyObservers();

        //TODO: correct: ?interactWithPlayer(player); //let the player redo his choices...
        /*if(!ban) { //the ban is set if the player got the related excommunication card

            //the player chooses the family member to place and where to place it
            //if(fm.getValue() >= market.getDiceValue())
            //try {
            // market.insertFamilyMember(fm);
            //marketSlot.getReward().applyEffect(player);
            //} catch(...) {
            //say that slot occupied and remake the player do his choices
            ResourceIncomeContext incomeContext = (ResourceIncomeContext) gameManager.getContextByType(ContextType.RESOURCE_INCOME_CONTEXT);
            incomeContext.handleResources(player, new Resources(2, 4, 5, 5));
        }
        else {
            //TODO: inform player of the ban
            gameManager.getContextByType(TURN_CONTEXT).interactWithPlayer(player);
        }*/
    }


    @Override
    public void increaseTempValue(Integer servantsConsumed) {
        tempValue += servantsConsumed;
    }

    @Override
    public void sweep() {
        market.sweep();
    }

    public void setBan() { this.ban = true; }

}
