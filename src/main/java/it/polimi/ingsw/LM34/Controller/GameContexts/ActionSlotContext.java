package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.CouncilPalace;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.Market;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.WorkingArea;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 16/05/2017.
 */
public class ActionSlotContext extends AbstractGameContext {
    //TODO: Ludovico Ariosto changes a boolean here so that the player can add his pawn despite of action slot limits
    //handle a particular excommunication card
    private Market market;
    private CouncilPalace councilPalace;
    private WorkingArea workingArea;




    public void initContext(Market market, CouncilPalace councilPalace, WorkingArea workingArea) {
        this.councilPalace = councilPalace;
        this.market = market;
        this.workingArea = workingArea;

        setChanged();
        notifyObservers(ContextStatus.ENTERED);
    }

    private void interactWithPlayer(Player player) {
        //TODO: implement what player can do here and modify the model in this controller class
        //handle "federico da montefeltro"
        setChanged();
        notifyObservers(ContextStatus.FINISHED);
    }

    @Override
    public void initContext() {

    }

    @Override
    public ContextType getType() {
        return ContextType.ACTION_SLOT_CONTEXT;
    }

    @Override
    public void endContext() {
        //phaseContext.interactWithPlayer();
    }

}

