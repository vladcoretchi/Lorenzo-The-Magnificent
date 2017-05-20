package it.polimi.ingsw.LM34.Controller.GameContexts;

import it.polimi.ingsw.LM34.Enums.Controller.ContextStatus;
import it.polimi.ingsw.LM34.Enums.Controller.ContextType;
import it.polimi.ingsw.LM34.Exceptions.Model.InvalidCardType;
import it.polimi.ingsw.LM34.Model.Boards.GameBoard.TowerSlot;
import it.polimi.ingsw.LM34.Model.Player;

/**
 * Created by GiulioComi on 18/05/2017.
 */
public class DevelopmentCardAcquireContext extends AbstractGameContext {
    //TODO: use a temporary dice value instead of modifying the real dice value stored in the game manager
    //TODO: handle Filippo Brunelleschi, Cesare Borgia
    private Player currentPlayer;


    public void initContext(Player player) {
        currentPlayer = player;
        setChanged();
        notifyObservers(ContextStatus.ENTERED);
    }

    @Override
    public void initContext() {}

    @Override
    public ContextType getType() {
        return ContextType.DEVELOPMENT_CARD_ACQUIRE_CONTEXT;
    }

    @Override
    public void endContext() {
        setChanged();
        notifyObservers(ContextStatus.FINISHED);

        //applyInstantEffect();
    }

    //TODO: evaluate if the buy should stay in this class
    public void buyCard(TowerSlot slot) throws InvalidCardType {
       currentPlayer.getPersonalBoard().addCard(slot.getCardStored());
    }
}
